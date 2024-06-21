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
 * Controller class for adding a new book to the library.
 */
public class AddBookController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField isbnField;

    @FXML
    private TextField statusField;

    /**
     * Handles the action event when the "Add Book" button is clicked.
     * Validates the input fields and adds the book to the database.
     *
     * @param event The action event triggered by clicking the "Add Book" button.
     */
    @FXML
    private void addBook(ActionEvent event) {
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();
        String status = statusField.getText();

        // Validate input fields
        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty() || status.isEmpty()) {
            showAlert("Validation Error", "All fields are required.");
            return;
        }

        // Add the book to the database
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            book.setIsbn(isbn);
            book.setStatus(status);
            session.save(book);
            transaction.commit();
            showAlert("Success", "Book added successfully!");
            clearFields();
        } catch (Exception e) {
            showAlert("Error", "An error occurred while adding the book.");
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

    /**
     * Clears all input fields.
     */
    private void clearFields() {
        titleField.clear();
        authorField.clear();
        isbnField.clear();
        statusField.clear();
    }
}
