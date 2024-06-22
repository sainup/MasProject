package mas.lms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mas.lms.model.Book;
import mas.lms.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

/**
 * Controller class for viewing books in the library.
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

    private ObservableList<Book> bookList;

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        bookList = FXCollections.observableArrayList();
        loadBooks();
        booksTable.setItems(bookList);
    }

    /**
     * Loads books from the database and populates the table.
     */
    private void loadBooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Book> books = session.createQuery("from Book", Book.class).list();
            bookList.setAll(books);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
