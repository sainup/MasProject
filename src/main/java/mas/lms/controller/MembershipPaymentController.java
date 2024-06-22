package mas.lms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import mas.lms.model.*;
import mas.lms.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Controller class for processing membership payments.
 */
public class MembershipPaymentController {

    @FXML
    private TextField memberIdField;

    @FXML
    private TextField amountField;

    @FXML
    private ComboBox<String> paymentMethodComboBox;

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField cardHolderField;

    @FXML
    private TextField expiryDateField;

    @FXML
    private TextField cvvField;

    @FXML
    private TextField receiptNumberField;

    private Member member;


    @FXML
    public void initialize() {
        paymentMethodComboBox.getItems().addAll("Card", "Cash");
        paymentMethodComboBox.setOnAction(e -> togglePaymentFields());
        togglePaymentFields();
    }

    private void togglePaymentFields() {
        boolean isCard = "Card".equals(paymentMethodComboBox.getValue());
        cardNumberField.setVisible(isCard);
        cardHolderField.setVisible(isCard);
        expiryDateField.setVisible(isCard);
        cvvField.setVisible(isCard);
        receiptNumberField.setVisible(!isCard);
    }

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
     * Processes the payment for the member.
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

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            double amount = Double.parseDouble(amountStr);
            PaymentMethod paymentMethod;
            if ("Card".equals(paymentMethodComboBox.getValue())) {
                paymentMethod = new Card(cardNumberField.getText(), cardHolderField.getText(),
                        expiryDateField.getText(), cvvField.getText(), BigDecimal.valueOf(amount), LocalDate.now());
            } else {
                paymentMethod = new Cash(receiptNumberField.getText(), BigDecimal.valueOf(amount), LocalDate.now());
            }

            Payment payment = new Payment(member, amount, "membership", paymentMethod);

            Transaction transaction = session.beginTransaction();
            session.save(paymentMethod);
            session.save(payment);
            transaction.commit();

            showAlert("Success", "Payment processed successfully!");
            clearFields();
            member = null;
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Invalid amount.");
        } catch (Exception e) {
            showAlert("Error", "An error occurred while processing the payment.");
            e.printStackTrace();
        }
    }

    private void clearFields() {
        memberIdField.clear();
        amountField.clear();
        cardNumberField.clear();
        cardHolderField.clear();
        expiryDateField.clear();
        cvvField.clear();
        receiptNumberField.clear();
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
