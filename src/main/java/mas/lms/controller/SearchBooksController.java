package mas.lms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import mas.lms.model.Book;
import mas.lms.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class SearchBooksController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField isbnField;

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, Long> bookIdColumn;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> categoryColumn;

    @FXML
    private TableColumn<Book, String> isbnColumn;

    @FXML
    private TableColumn<Book, String> statusColumn;

    private ObservableList<Book> bookList;

    @FXML
    public void initialize() {
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        bookList = FXCollections.observableArrayList();
        booksTable.setItems(bookList);
    }

    @FXML
    private void searchBooks(ActionEvent event) {
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Book b WHERE 1=1";
            if (!title.isEmpty()) {
                hql += " AND b.title LIKE :title";
            }
            if (!author.isEmpty()) {
                hql += " AND b.author LIKE :author";
            }
            if (!isbn.isEmpty()) {
                hql += " AND b.isbn LIKE :isbn";
            }

            Query<Book> query = session.createQuery(hql, Book.class);

            if (!title.isEmpty()) {
                query.setParameter("title", "%" + title + "%");
            }
            if (!author.isEmpty()) {
                query.setParameter("author", "%" + author + "%");
            }
            if (!isbn.isEmpty()) {
                query.setParameter("isbn", "%" + isbn + "%");
            }

            List<Book> books = query.list();
            bookList.setAll(books);
        } catch (Exception e) {
            showAlert("Error", "An error occurred while searching for books.");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
