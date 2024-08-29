package mas.lms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mas.lms.model.Book;

import java.util.List;

/**
 * Controller class for viewing books borrowed by a selected member.
 */
public class ViewBorrowedBooksController {

    @FXML
    private TableView<Book> borrowedBooksTable; // TableView for displaying borrowed books

    @FXML
    private TableColumn<Book, Long> bookIdColumn; // Column for book ID

    @FXML
    private TableColumn<Book, String> bookTitleColumn; // Column for book title

    private ObservableList<Book> borrowedBookList; // List of borrowed books

    private Stage stage; // The stage for this controller

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        // Set up the table columns with property values
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        // Initialize the borrowed book list and set it to the table
        borrowedBookList = FXCollections.observableArrayList();
        borrowedBooksTable.setItems(borrowedBookList);
    }

    /**
     * Sets the borrowed books to be displayed in the table.
     *
     * @param books List of books borrowed by the selected member.
     */
    public void setBorrowedBooks(List<Book> books) {
        borrowedBookList.setAll(books);
    }

    /**
     * Sets the stage for this controller.
     *
     * @param stage The stage to set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Closes the window.
     */
    @FXML
    private void closeWindow() {
        if (stage != null) {
            stage.close();
        }
    }
}
