package mas.lms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import mas.lms.util.HibernateUtil;
import mas.lms.model.Member;
import mas.lms.model.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Controller class for processing fine payments.
 */
public class FinePaymentController {

    @FXML
    private TextField memberIdField;

    @FXML
    private TextField amountField;

    private Member member;

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
     * Handles the action event when the "Process Payment" button is clicked.
     * Validates the input fields and processes the payment for the member.
     *
     * @param event The action event triggered by clicking the "Process Payment" button.
     */
    @FXML
    private void processPayment(ActionEvent event) {
        if (member == null) {
            showAlert("Validation Error", "Please search and select a member first.");
            return;
        }
        String amountStr = amountField.getText();
        if (amountStr.isEmpty()) {
            showAlert("Validation Error", "Amount is required.");
            return;
        }

        // Process the payment
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            double amount = Double.parseDouble(amountStr);
            Payment payment = new Payment(member, amount, "fine");

            Transaction transaction = session.beginTransaction();
            session.save(payment);
            transaction.commit();

            showAlert("Success", "Payment processed successfully!");
            memberIdField.clear();
            amountField.clear();
            member = null;
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Invalid amount.");
        } catch (Exception e) {
            showAlert("Error", "An error occurred while processing the payment.");
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
