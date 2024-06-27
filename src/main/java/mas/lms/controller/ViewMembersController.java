package mas.lms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mas.lms.model.Member;
import mas.lms.util.DeletionUtil;
import mas.lms.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller class for viewing and managing members.
 */
public class ViewMembersController {

    @FXML
    private TableView<Member> membersTable;

    @FXML
    private TableColumn<Member, Long> idColumn;

    @FXML
    private TableColumn<Member, String> nameColumn;

    @FXML
    private TableColumn<Member, LocalDate> birthdateColumn;

    @FXML
    private TableColumn<Member, Boolean> goldMemberColumn;

    @FXML
    private TableColumn<Member, Integer> daysBorrowedColumn;

    @FXML
    private Button updateMemberButton;

    @FXML
    private Button deleteMemberButton;

    private ObservableList<Member> memberList;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        birthdateColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        goldMemberColumn.setCellValueFactory(new PropertyValueFactory<>("goldMember"));
        daysBorrowedColumn.setCellValueFactory(new PropertyValueFactory<>("daysBorrowed"));

        memberList = FXCollections.observableArrayList();
        loadMembers();
        membersTable.setItems(memberList);

        updateMemberButton.setOnAction(event -> updateMember());
        deleteMemberButton.setOnAction(event -> deleteMember());
    }

    /**
     * Loads the members from the database and populates the table.
     */
    private void loadMembers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Member> members = session.createQuery("from Member", Member.class).list();
            memberList.setAll(members);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action event when the "Update Member" button is clicked.
     * Opens the update member dialog.
     */
    private void updateMember() {
        Member selectedMember = membersTable.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UpdateMember.fxml"));
                Parent root = loader.load();

                UpdateMemberController controller = loader.getController();
                controller.setMember(selectedMember);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Update Member");
                stage.setScene(new Scene(root));
                controller.setStage(stage); // Set the stage in the controller
                stage.showAndWait();

                loadMembers(); // Refresh the member list after the update
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Loading Error", "Unable to load the update member dialog.");
            }
        } else {
            showAlert("Selection Error", "Please select a member to update.");
        }
    }

    /**
     * Handles the action event when the "Delete Member" button is clicked.
     * Deletes the selected member.
     */
    private void deleteMember() {
        Member selectedMember = membersTable.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            boolean confirmed = DeletionUtil.showConfirmation("Delete Member",
                    "Are you sure you want to delete this member? This action cannot be undone.");

            if (confirmed) {
                try {
                    DeletionUtil.deleteEntity(Member.class, selectedMember.getId(), "Member");
                    loadMembers();
                } catch (ConstraintViolationException e) {
                    handleDeleteException(selectedMember.getId());
                }
            }
        } else {
            DeletionUtil.showAlert("Selection Error", "Please select a member to delete.");
        }
    }

    /**
     * Handles the deletion exception if the member has associated records.
     *
     * @param memberId The ID of the member that failed to be deleted.
     */
    private void handleDeleteException(Long memberId) {
        boolean confirmed = DeletionUtil.showConfirmation("Referential Integrity Constraint Violation",
                "Member has associated records. Do you still wish to continue?");

        if (confirmed) {
            DeletionUtil.forceDeleteEntity(Member.class, memberId, "Member");
            loadMembers();
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
