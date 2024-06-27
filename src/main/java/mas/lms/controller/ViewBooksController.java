package mas.lms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mas.lms.model.Book;
import mas.lms.util.DeletionUtil;
import mas.lms.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.io.IOException;
import java.util.List;

/**
 * Controller class for viewing and managing books.
 */
public class ViewBooksController {

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, Long> idColumn;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> isbnColumn;

    @FXML
    private TableColumn<Book, String> statusColumn;

    @FXML
    private TableColumn<Book, String> categoryColumn;

    @FXML
    private TableColumn<Book, String> publisherColumn;

    @FXML
    private Button updateBookButton;

    @FXML
    private Button deleteBookButton;

    private ObservableList<Book> bookList;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    public void initialize() {
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

        bookList = FXCollections.observableArrayList();
        loadBooks();
        booksTable.setItems(bookList);

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
            boolean confirmed = DeletionUtil.showConfirmation("Delete Book",
                    "Are you sure you want to delete this book? This action cannot be undone.");

            if (confirmed) {
                try {
                    DeletionUtil.deleteEntity(Book.class, selectedBook.getId(), "Book");
                    loadBooks();
                } catch (ConstraintViolationException e) {
                    handleDeleteException(selectedBook.getId());
                }
            }
        } else {
            DeletionUtil.showAlert("Selection Error", "Please select a book to delete.");
        }
    }

    /**
     * Handles the deletion exception if the book has associated records.
     *
     * @param bookId The ID of the book that failed to be deleted.
     */
    private void handleDeleteException(Long bookId) {
        boolean confirmed = DeletionUtil.showConfirmation("Referential Integrity Constraint Violation",
                "Book has associated records. Do you still wish to continue?");

        if (confirmed) {
            DeletionUtil.forceDeleteEntity(Book.class, bookId, "Book");
            loadBooks();
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
