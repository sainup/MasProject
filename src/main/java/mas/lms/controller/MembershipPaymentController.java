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

public class MembershipPaymentController {

    @FXML
    private TextField memberIdField;

    @FXML
    private TextField amountField;

    private Member member;

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
            Payment payment = new Payment(member, amount, "membership");

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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
