package mas.lms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import mas.lms.model.Address;
import mas.lms.model.Book;
import mas.lms.model.Category;
import mas.lms.model.Publisher;
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
    private TextField categoryField;

    @FXML
    private TextField publisherField;

    @FXML
    private TextField publisherAddressField;

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
        String categoryName = categoryField.getText();
        String publisherName = publisherField.getText();
        String publisherAddress = publisherAddressField.getText();

        if (title.isEmpty() || author.isEmpty() || categoryName.isEmpty()) {
            showAlert("Validation Error", "Title, Author, and Category are required.");
            return;
        }

        if (!isbn.isEmpty() && (isbn.length() != 13 || !isbn.matches("\\d+"))) {
            showAlert("Validation Error", "ISBN must be exactly 13 digits long.");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Category category = session.createQuery("from Category where name = :name", Category.class)
                    .setParameter("name", categoryName)
                    .uniqueResultOptional()
                    .orElse(new Category(categoryName));

            Publisher publisher = null;
            if (!publisherName.isEmpty() && !publisherAddress.isEmpty()) {
                Address address = new Address(publisherAddress, "", ""); // Assuming only street address is required for simplicity
                publisher = session.createQuery("from Publisher where name = :name", Publisher.class)
                        .setParameter("name", publisherName)
                        .uniqueResultOptional()
                        .orElse(new Publisher(publisherName, address));
            }

            Book book = new Book(title, author, category, isbn.isEmpty() ? null : isbn);
            if (publisher != null) {
                book.setPublisher(publisher);
            }

            session.save(category);
            if (publisher != null) {
                session.save(publisher);
            }
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

    /**
     * Clears all input fields.
     */
    private void clearFields() {
        titleField.clear();
        authorField.clear();
        isbnField.clear();
        categoryField.clear();
        publisherField.clear();
        publisherAddressField.clear();
    }
}
