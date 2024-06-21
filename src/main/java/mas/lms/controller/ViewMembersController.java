package mas.lms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import mas.lms.util.HibernateUtil;
import mas.lms.model.Member;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

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
    private TableColumn<Member, Integer> memberIdColumn;

    @FXML
    private TableColumn<Member, Boolean> goldMemberColumn;

    @FXML
    private TableColumn<Member, Integer> daysBorrowedColumn;

    @FXML
    private Button updateMemberButton;

    @FXML
    private Button deleteMemberButton;

    private ObservableList<Member> memberList;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        birthdateColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        memberIdColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        goldMemberColumn.setCellValueFactory(new PropertyValueFactory<>("goldMember"));
        daysBorrowedColumn.setCellValueFactory(new PropertyValueFactory<>("daysBorrowed"));

        memberList = FXCollections.observableArrayList();
        loadMembers();
        membersTable.setItems(memberList);

        updateMemberButton.setOnAction(event -> updateMember());
        deleteMemberButton.setOnAction(event -> deleteMember());
    }

    private void loadMembers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Member> members = session.createQuery("from Member", Member.class).list();
            memberList.setAll(members);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateMember() {
        Member selectedMember = membersTable.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            // Open a new window or dialog to update member details
            // After updating, refresh the member list
            loadMembers();
        } else {
            showAlert("Selection Error", "Please select a member to update.");
        }
    }

    private void deleteMember() {
        Member selectedMember = membersTable.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                session.beginTransaction();
                session.delete(selectedMember);
                session.getTransaction().commit();
                memberList.remove(selectedMember);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Deletion Error", "An error occurred while deleting the member.");
            }
        } else {
            showAlert("Selection Error", "Please select a member to delete.");
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
