package mas.lms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import mas.lms.model.Address;
import mas.lms.model.Member;
import mas.lms.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;

/**
 * Controller class for adding a new member to the library.
 */
public class AddMemberController {

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

    /**
     * Handles the action event when the "Add Member" button is clicked.
     * Validates the input fields and saves the new member to the database.
     *
     * @param event The action event triggered by clicking the "Add Member" button.
     */
    @FXML
    private void addMember(ActionEvent event) {
        String name = nameField.getText();
        String birthdate = birthdateField.getText();
        String street = streetField.getText();
        String city = cityField.getText();
        String zipCode = zipCodeField.getText();

        // Validate input fields
        if (name.isEmpty() || birthdate.isEmpty() || street.isEmpty() || city.isEmpty() || zipCode.isEmpty()) {
            showAlert("Validation Error", "Name, Birthdate, and Address fields are required.");
            return;
        }

        // Parse birthdate and save the new member to the database
        try {
            LocalDate birthDateParsed = LocalDate.parse(birthdate);
            Address address = new Address(street, city, zipCode);
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Transaction transaction = session.beginTransaction();
                Member member = new Member(name, birthDateParsed, address);
                session.save(member);
                transaction.commit();
            }
            showAlert("Success", "Member added successfully!");
            clearFields();
        } catch (Exception e) {
            showAlert("Error", "An error occurred while adding the member.");
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

    /**
     * Clears the input fields in the form.
     */
    private void clearFields() {
        nameField.clear();
        birthdateField.clear();
        streetField.clear();
        cityField.clear();
        zipCodeField.clear();
    }
}
