package mas.lms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import mas.lms.model.Book;
import mas.lms.util.DeletionUtil;

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
            DeletionUtil.showAlert("Validation Error", "Please enter Book ID.");
            return;
        }

        // Delete the book from the database
        try {
            long bookId = Long.parseLong(bookIdStr);
            DeletionUtil.deleteEntity(Book.class, bookId, "Book");
            bookIdField.clear();
        } catch (NumberFormatException e) {
            DeletionUtil.showAlert("Validation Error", "Invalid Book ID.");
        }
    }
}
