package mas.lms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import mas.lms.model.Book;
import mas.lms.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Controller class for deleting a book from the library.
 */
public class DeleteBookController {

    @FXML
    private TextField bookIdField;

    /**
     * Handles the action event when the "Delete Book" button is clicked.
     * Validates the input field and deletes the book from the database.
     *
     * @param event The action event triggered by clicking the "Delete Book" button.
     */
    @FXML
    private void deleteBook(ActionEvent event) {
        String bookIdStr = bookIdField.getText();

        // Validate input field
        if (bookIdStr.isEmpty()) {
            showAlert("Validation Error", "Please enter Book ID.");
            return;
        }

        // Delete the book from the database
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            long bookId = Long.parseLong(bookIdStr);
            Transaction transaction = session.beginTransaction();
            Book book = session.get(Book.class, bookId);
            if (book == null) {
                showAlert("Error", "Book not found.");
                return;
            }

            session.delete(book);
            transaction.commit();
            showAlert("Success", "Book deleted successfully!");
            bookIdField.clear();
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Invalid Book ID.");
        } catch (Exception e) {
            showAlert("Error", "An error occurred while deleting the book.");
            e.printStackTrace();
        }
    }

    /**
     * Displays an alert dialog with the specified title and message.
     *
     * @param title The title of the alert dialog.
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
