package mas.lms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import mas.lms.util.HibernateUtil;
import mas.lms.model.Member;
import org.hibernate.Session;

import java.time.LocalDate;

public class UpdateMemberController {

    @FXML
    private TextField memberIdField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField birthdateField;

    @FXML
    private void updateMember(ActionEvent event) {
        String memberIdStr = memberIdField.getText();
        String name = nameField.getText();
        String birthdate = birthdateField.getText();

        if (memberIdStr.isEmpty() || name.isEmpty() || birthdate.isEmpty()) {
            showAlert("Validation Error", "Member ID, Name, and Birthdate are required.");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            long memberId = Long.parseLong(memberIdStr);
            Member member = session.get(Member.class, memberId);

            if (member == null) {
                showAlert("Error", "Member not found.");
                return;
            }

            session.beginTransaction();
            member.setName(name);
            member.setBirthdate(LocalDate.parse(birthdate));
            session.update(member);
            session.getTransaction().commit();

            showAlert("Success", "Member updated successfully!");
            memberIdField.clear();
            nameField.clear();
            birthdateField.clear();
        } catch (Exception e) {
            showAlert("Error", "An error occurred while updating the member.");
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
