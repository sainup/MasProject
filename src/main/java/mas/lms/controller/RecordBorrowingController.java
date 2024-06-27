package mas.lms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import mas.lms.model.Book;
import mas.lms.model.Borrow;
import mas.lms.util.HibernateUtil;
import mas.lms.model.Member;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for recording book borrowings.
 */
public class RecordBorrowingController {

    @FXML
    private TextField memberIdField;

    @FXML
    private TextField bookIdField;

    @FXML
    private ListView<String> borrowedBooksListView;

    private Member member;
    private Book book;

    /**
     * Handles the action event when the "Search Member" button is clicked.
     * Searches for the member in the database and displays their borrowed books.
     *
     * @param event The action event triggered by clicking the "Search Member" button.
     */
    @FXML
    private void searchMember(ActionEvent event) {
        String memberIdStr = memberIdField.getText();
        if (memberIdStr.isEmpty()) {
            showAlert("Validation Error", "Member ID is required.");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            long memberId = Long.parseLong(memberIdStr);
            member = session.get(Member.class, memberId);
            if (member == null) {
                showAlert("Error", "Member not found.");
            } else {
                Hibernate.initialize(member.getBorrows());
                updateBorrowedBooksList(new ArrayList<>(member.getBorrows()));
                showAlert("Success", "Member found: " + member.getName());
            }
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Invalid Member ID.");
        } catch (Exception e) {
            showAlert("Error", "An error occurred while searching for the member.");
            e.printStackTrace();
        }
    }

    /**
     * Updates the list view with the borrowed books of the member.
     *
     * @param borrows The list of borrows to display.
     */
    private void updateBorrowedBooksList(List<Borrow> borrows) {
        ObservableList<String> borrowedBooks = FXCollections.observableArrayList();
        for (Borrow borrow : borrows) {
            borrowedBooks.add("Book ID: " + borrow.getBookId() + ", Title: " + borrow.getTitle() +
                    ", Author: " + borrow.getAuthor() + ", Status: " + borrow.getStatus());
        }
        borrowedBooksListView.setItems(borrowedBooks);
    }

    /**
     * Handles the action event when the "Search Book" button is clicked.
     * Searches for the book in the database.
     *
     * @param event The action event triggered by clicking the "Search Book" button.
     */
    @FXML
    private void searchBook(ActionEvent event) {
        String bookIdStr = bookIdField.getText();
        if (bookIdStr.isEmpty()) {
            showAlert("Validation Error", "Book ID is required.");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            long bookId = Long.parseLong(bookIdStr);
            book = session.get(Book.class, bookId);
            if (book == null) {
                showAlert("Error", "Book not found.");
            } else {
                showAlert("Success", "Book found: " + book.getTitle());
            }
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Invalid Book ID.");
        } catch (Exception e) {
            showAlert("Error", "An error occurred while searching for the book.");
            e.printStackTrace();
        }
    }

    /**
     * Handles the action event when the "Record Borrowing" button is clicked.
     * Records the borrowing transaction for the selected member and book.
     *
     * @param event The action event triggered by clicking the "Record Borrowing" button.
     */
    @FXML
    private void recordBorrowing(ActionEvent event) {
        if (member == null) {
            showAlert("Validation Error", "Please search and select a member first.");
            return;
        }
        if (book == null) {
            showAlert("Validation Error", "Please search and select a book first.");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            // Refresh the book entity to ensure it's up-to-date
            book = session.get(Book.class, book.getId());

            if (!book.getStatus().equals("available")) {
                showAlert("Error", "Book is currently borrowed or reserved.");
                session.getTransaction().rollback();
                return;
            }

            // Create a new borrow record with the current date and null return date
            Borrow borrow = new Borrow(member, book, LocalDate.now(), null);
            book.setStatus("borrowed");

            // Save the borrow transaction
            session.save(borrow);

            // Update the book status
            session.update(book);

            session.getTransaction().commit();
            showAlert("Success", "Borrowing recorded successfully!");
            memberIdField.clear();
            bookIdField.clear();
            member = null;
            book = null;
            borrowedBooksListView.getItems().clear();
        } catch (Exception e) {
            showAlert("Error", "An error occurred while recording the borrowing.");
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
}
