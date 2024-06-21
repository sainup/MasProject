package mas.lms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import mas.lms.util.HibernateUtil;
import mas.lms.model.Member;
import org.hibernate.Session;

/**
 * Controller class for deleting a member from the library.
 */
public class DeleteMemberController {

    @FXML
    private TextField memberIdField;

    /**
     * Handles the action event when the "Delete Member" button is clicked.
     * Validates the input field and deletes the member from the database.
     *
     * @param event The action event triggered by clicking the "Delete Member" button.
     */
    @FXML
    private void deleteMember(ActionEvent event) {
        String memberIdStr = memberIdField.getText();

        // Validate input field
        if (memberIdStr.isEmpty()) {
            showAlert("Validation Error", "Member ID is required.");
            return;
        }

        // Delete the member from the database
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            long memberId = Long.parseLong(memberIdStr);
            Member member = session.get(Member.class, memberId);

            if (member == null) {
                showAlert("Error", "Member not found.");
                return;
            }

            session.beginTransaction();
            session.delete(member);
            session.getTransaction().commit();

            showAlert("Success", "Member deleted successfully!");
            memberIdField.clear();
        } catch (Exception e) {
            showAlert("Error", "An error occurred while deleting the member.");
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
