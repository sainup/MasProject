package mas.lms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mas.lms.model.Book;
import mas.lms.model.Borrow;
import mas.lms.model.Member;
import mas.lms.util.DeletionUtil;
import mas.lms.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for viewing and managing books.
 */
public class ViewBooksController {

    @FXML
    private TableView<Book> booksTable; // TableView for displaying books

    @FXML
    private TableColumn<Book, Long> idColumn; // Column for book ID

    @FXML
    private TableColumn<Book, String> titleColumn; // Column for book title

    @FXML
    private TableColumn<Book, String> authorColumn; // Column for book author

    @FXML
    private TableColumn<Book, String> isbnColumn; // Column for book ISBN

    @FXML
    private TableColumn<Book, String> statusColumn; // Column for book status

    @FXML
    private TableColumn<Book, String> categoryColumn; // Column for book category

    @FXML
    private TableColumn<Book, String> publisherColumn; // Column for book publisher

    @FXML
    private TableView<Member> borrowersTable;  // TableView for displaying members who borrowed the selected book

    @FXML
    private TableColumn<Member, Long> borrowerIdColumn; // Column for member ID

    @FXML
    private TableColumn<Member, String> borrowerNameColumn; // Column for member name

    @FXML
    private Button updateBookButton; // Button for updating book information

    @FXML
    private Button deleteBookButton; // Button for deleting a book

    private ObservableList<Book> bookList; // List of books for the TableView
    private ObservableList<Member> borrowerList; // List of borrowers for the secondary TableView

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        // Set up the table columns with property values
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().getCategory().nameProperty());
        publisherColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getPublisher() != null) {
                return cellData.getValue().getPublisher().nameProperty();
            }
            return null;
        });

        // Initialize the book list and populate it
        bookList = FXCollections.observableArrayList();
        loadBooks();
        booksTable.setItems(bookList);

        // Add a double-click listener to open a new window when a book is double-clicked
        booksTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
                if (selectedBook != null) {
                    showBorrowersForSelectedBook(selectedBook);
                }
            }
        });

        // Set actions for update and delete buttons
        updateBookButton.setOnAction(event -> updateBook());
        deleteBookButton.setOnAction(event -> deleteBook());
    }

    /**
     * Loads the books from the database and populates the table.
     */
    private void loadBooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Book> books = session.createQuery("from Book", Book.class).list();
            bookList.setAll(books);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action event when the "Update Book" button is clicked.
     * Opens the update book dialog.
     */
    private void updateBook() {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UpdateBook.fxml"));
                Parent root = loader.load();

                UpdateBookController controller = loader.getController();
                controller.setBook(selectedBook);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Update Book");
                stage.setScene(new Scene(root));
                controller.setStage(stage);
                stage.showAndWait();

                loadBooks(); // Refresh the book list after the update
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Loading Error", "Unable to load the update book dialog.");
            }
        } else {
            showAlert("Selection Error", "Please select a book to update.");
        }
    }

    /**
     * Handles the action event when the "Delete Book" button is clicked.
     * Deletes the selected book.
     */
    private void deleteBook() {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            boolean confirmed = DeletionUtil.showConfirmation("Delete Book", "Are you sure you want to delete this book? This action cannot be undone.");

            if (confirmed) {
                try {
                    DeletionUtil.deleteEntity(Book.class, selectedBook.getId(), "Book");
                    loadBooks(); // Refresh the book list after deletion
                } catch (ConstraintViolationException e) {
                    handleDeleteException(selectedBook.getId());
                }
            }
        } else {
            DeletionUtil.showAlert("Selection Error", "Please select a book to delete.");
        }
    }

    /**
     * Opens a new window to show the list of members who borrowed the selected book.
     *
     * @param selectedBook The book that was selected.
     */
    private void showBorrowersForSelectedBook(Book selectedBook) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ViewBorrowers.fxml"));
            Parent root = loader.load();

            ViewBorrowersController controller = loader.getController();
            List<Member> borrowers = getBorrowersForBook(selectedBook);
            controller.setBorrowers(borrowers);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Borrowers of Book: " + selectedBook.getTitle());
            stage.setScene(new Scene(root));
            controller.setStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Loading Error", "Unable to load the borrowers dialog.");
        }
    }

    /**
     * Retrieves the list of members who have borrowed the given book.
     *
     * @param book The book for which to retrieve borrowers.
     * @return A list of members who borrowed the book.
     */
    private List<Member> getBorrowersForBook(Book book) {
        // Using association from the model to get the list of borrowers
        return book.getBorrows().stream()
                .map(Borrow::getMember)
                .collect(Collectors.toList());
    }

    /**
     * Handles the deletion exception if the book has associated records.
     *
     * @param bookId The ID of the book that failed to be deleted.
     */
    private void handleDeleteException(Long bookId) {
        boolean confirmed = DeletionUtil.showConfirmation("Referential Integrity Constraint Violation", "Book has associated records. Do you still wish to continue?");

        if (confirmed) {
            DeletionUtil.forceDeleteEntity(Book.class, bookId, "Book");
            loadBooks(); // Refresh the book list after force deletion
        }
    }

    /**
     * Displays an alert dialog with the specified title and message.
     *
     * @param title   The title of the alert dialog.
     * @param message The message to be displayed in the alert dialog.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
