package mas.lms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import mas.lms.model.Book;
import mas.lms.util.HibernateUtil;
import mas.lms.model.Member;
import mas.lms.model.Reservation;
import org.hibernate.Session;

import java.time.LocalDate;

/**
 * Controller class for reserving books.
 */
public class ReserveBooksController {

    @FXML
    private TextField memberIdField;

    @FXML
    private TextField bookIdField;

    private Member member;
    private Book book;

    /**
     * Handles the action event when the "Search Member" button is clicked.
     * Validates the input field and searches for the member in the database.
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

        // Search for the member in the database
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            long memberId = Long.parseLong(memberIdStr);
            member = session.get(Member.class, memberId);
            if (member == null) {
                showAlert("Error", "Member not found.");
            } else {
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
     * Handles the action event when the "Search Book" button is clicked.
     * Validates the input field and searches for the book in the database.
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

        // Search for the book in the database
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
     * Handles the action event when the "Reserve Book" button is clicked.
     * Validates the input fields and reserves the book for the member.
     *
     * @param event The action event triggered by clicking the "Reserve Book" button.
     */
    @FXML
    private void reserveBook(ActionEvent event) {
        if (member == null) {
            showAlert("Validation Error", "Please search and select a member first.");
            return;
        }
        if (book == null) {
            showAlert("Validation Error", "Please search and select a book first.");
            return;
        }

        // Reserve the book for the member
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            // Refresh the book entity to ensure it's up-to-date
            book = session.get(Book.class, book.getId());

            if (!book.getStatus().equals("available")) {
                showAlert("Error", "Book is currently borrowed or reserved.");
                session.getTransaction().rollback();
                return;
            }

            Reservation reservation = new Reservation(LocalDate.now());
            reservation.setMember(member);
            reservation.setBook(book);
            book.setStatus("reserved");

            session.save(reservation);
            session.update(book);

            session.getTransaction().commit();
            showAlert("Success", "Book reserved successfully!");
            memberIdField.clear();
            bookIdField.clear();
            member = null;
            book = null;
        } catch (Exception e) {
            showAlert("Error", "An error occurred while reserving the book.");
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
