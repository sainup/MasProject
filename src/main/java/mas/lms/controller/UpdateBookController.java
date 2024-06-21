package mas.lms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import mas.lms.model.Book;
import mas.lms.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UpdateBookController {

    @FXML
    private TextField bookIdField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField isbnField;

    @FXML
    private void updateBook(ActionEvent event) {
        String bookIdStr = bookIdField.getText();
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();

        if (bookIdStr.isEmpty() || title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
            showAlert("Validation Error", "Please fill all fields.");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            long bookId = Long.parseLong(bookIdStr);
            Transaction transaction = session.beginTransaction();
            Book book = session.get(Book.class, bookId);
            if (book == null) {
                showAlert("Error", "Book not found.");
                return;
            }

            book.setTitle(title);
            book.setAuthor(author);
            book.setIsbn(isbn);
            session.update(book);
            transaction.commit();
            showAlert("Success", "Book updated successfully!");
            bookIdField.clear();
            titleField.clear();
            authorField.clear();
            isbnField.clear();
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Invalid Book ID.");
        } catch (Exception e) {
            showAlert("Error", "An error occurred while updating the book.");
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
