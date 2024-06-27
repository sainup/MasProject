package mas.lms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mas.lms.model.*;
import mas.lms.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Controller class for updating book information.
 */
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
    private TextField categoryField;

    @FXML
    private TextField publisherField;

    @FXML
    private TextField publisherAddressField;

    private Stage stage;
    private Book book;

    /**
     * Sets the stage for the update book dialog.
     *
     * @param stage The stage for the update book dialog.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the book to be updated and populates the input fields.
     *
     * @param book The book to be updated.
     */
    public void setBook(Book book) {
        this.book = book;
        bookIdField.setText(String.valueOf(book.getId()));
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        isbnField.setText(book.getIsbn());
        categoryField.setText(book.getCategory() != null ? book.getCategory().getName() : "");
        publisherField.setText(book.getPublisher() != null ? book.getPublisher().getName() : "");
        publisherAddressField.setText(book.getPublisher() != null && book.getPublisher().getAddress() != null ? book.getPublisher().getAddress().getStreet() : "");
    }

    /**
     * Handles the action event when the "Update Book" button is clicked.
     * Validates the input fields and updates the book in the database.
     *
     * @param event The action event triggered by clicking the "Update Book" button.
     */
    @FXML
    private void updateBook(ActionEvent event) {
        String bookIdStr = bookIdField.getText();
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();
        String categoryName = categoryField.getText();
        String publisherName = publisherField.getText();
        String publisherAddress = publisherAddressField.getText();

        if (bookIdStr.isEmpty() || title.isEmpty() || author.isEmpty() || categoryName.isEmpty()) {
            showAlert("Validation Error", "Book ID, Title, Author, and Category are required.");
            return;
        }

        if (!isbn.isEmpty() && (isbn.length() != 13 || !isbn.matches("\\d+"))) {
            showAlert("Validation Error", "ISBN must be exactly 13 digits long.");
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
            book.setIsbn(isbn.isEmpty() ? null : isbn);

            Category category = session.createQuery("from Category where name = :name", Category.class)
                    .setParameter("name", categoryName)
                    .uniqueResultOptional()
                    .orElse(new Category(categoryName));
            book.setCategory(category);

            Publisher publisher = null;
            if (!publisherName.isEmpty() && !publisherAddress.isEmpty()) {
                Address address = new Address(publisherAddress, "", ""); // Assuming only street address is required for simplicity
                publisher = session.createQuery("from Publisher where name = :name", Publisher.class)
                        .setParameter("name", publisherName)
                        .uniqueResultOptional()
                        .orElse(new Publisher(publisherName, address));
                book.setPublisher(publisher);
            } else {
                book.setPublisher(null);
            }

            session.saveOrUpdate(category);
            if (publisher != null) {
                session.saveOrUpdate(publisher);
            }
            session.update(book);
            transaction.commit();

            showAlert("Success", "Book updated successfully!");
            clearFields();
            if (stage != null) {
                stage.close(); // Close the dialog after successful update
            }
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Invalid Book ID.");
        } catch (Exception e) {
            showAlert("Error", "An error occurred while updating the book.");
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
        bookIdField.clear();
        titleField.clear();
        authorField.clear();
        isbnField.clear();
        categoryField.clear();
        publisherField.clear();
        publisherAddressField.clear();
    }
}
