package mas.lms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import mas.lms.model.Book;
import mas.lms.model.Borrow;
import mas.lms.util.HibernateUtil;
import mas.lms.model.Member;
import mas.lms.model.Reservation;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for recording a book return.
 */
public class ReturnBookController {

    @FXML
    private TextField memberIdField;

    @FXML
    private TextField bookIdField;

    private Member member;
    private Book book;

    /**
     * Handles the action event when the "Search Member" button is clicked.
     * Searches for the member in the database.
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
                showAlert("Success", "Member found: " + member.getName());
                System.out.println("Member ID: " + member.getId());
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
                System.out.println("Book ID: " + book.getId());
            }
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Invalid Book ID.");
        } catch (Exception e) {
            showAlert("Error", "An error occurred while searching for the book.");
            e.printStackTrace();
        }
    }

    /**
     * Records the return of a borrowed book.
     * @param event the action event
     */
    @FXML
    private void recordReturning(ActionEvent event) {
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

            System.out.println("Searching for active borrow transaction with Book ID: " + book.getId() + " and Member ID: " + member.getId());

            // Find the borrow transaction
            List<Borrow> borrows = session.createQuery("from Borrow where book.id = :bookId and member.id = :memberId and returnDate is null", Borrow.class)
                    .setParameter("bookId", book.getId())
                    .setParameter("memberId", member.getId())
                    .list();

            System.out.println("Number of active borrows found: " + borrows.size());

            if (borrows.isEmpty()) {
                showAlert("Error", "No active borrow transaction found for this book and member.");
                session.getTransaction().rollback();
                return;
            }

            Borrow borrow = borrows.get(0);

            // Update the book status and borrow transaction
            borrow.setReturnDate(LocalDate.now());
            session.update(borrow);

            // Check for pending reservations
            List<Reservation> reservations = session.createQuery("from Reservation where book.id = :bookId and member.id != :memberId", Reservation.class)
                    .setParameter("bookId", book.getId())
                    .setParameter("memberId", member.getId())
                    .list();

            if (!reservations.isEmpty()) {
                book.setStatus("reserved");
            } else {
                book.setStatus("available");
            }

            session.update(book);
            session.getTransaction().commit();

            showAlert("Success", "Book returned successfully!");
            memberIdField.clear();
            bookIdField.clear();
            member = null;
            book = null;
        } catch (Exception e) {
            showAlert("Error", "An error occurred while recording the return.");
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
