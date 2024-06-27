package mas.lms.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mas.lms.model.Address;
import mas.lms.model.Member;
import mas.lms.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;

/**
 * Controller class for updating member information.
 */
public class UpdateMemberController {

    @FXML
    private TextField memberIdField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField birthdateField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField zipCodeField;

    private Member member;
    private Stage stage;

    /**
     * Sets the member to be updated.
     *
     * @param member The member to be updated.
     */
    public void setMember(Member member) {
        this.member = member;
        memberIdField.setText(String.valueOf(member.getId()));
        nameField.setText(member.getName());
        birthdateField.setText(member.getBirthdate().toString());
        if (member.getAddress() != null) {
            streetField.setText(member.getAddress().getStreet());
            cityField.setText(member.getAddress().getCity());
            zipCodeField.setText(member.getAddress().getZipCode());
        }
    }

    /**
     * Sets the stage for the update member dialog.
     *
     * @param stage The stage for the update member dialog.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Handles the action event when the "Update Member" button is clicked.
     * Updates the member information in the database.
     */
    @FXML
    private void updateMember() {
        String memberIdStr = memberIdField.getText();
        String name = nameField.getText();
        String birthdate = birthdateField.getText();
        String street = streetField.getText();
        String city = cityField.getText();
        String zipCode = zipCodeField.getText();

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
            if (!street.isEmpty() && !city.isEmpty() && !zipCode.isEmpty()) {
                member.setAddress(new Address(street, city, zipCode));
            } else {
                member.setAddress(null);
            }
            session.update(member);
            session.getTransaction().commit();

            showAlert("Success", "Member updated successfully!");
            clearFields();
            if (stage != null) {
                stage.close(); // Close the dialog after successful update
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred while updating the member.");
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
     * Clears the input fields in the form.
     */
    private void clearFields() {
        memberIdField.clear();
        nameField.clear();
        birthdateField.clear();
        streetField.clear();
        cityField.clear();
        zipCodeField.clear();
    }
}
