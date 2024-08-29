package mas.lms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mas.lms.model.Book;
import mas.lms.model.Borrow;
import mas.lms.model.Member;
import mas.lms.util.DeletionUtil;
import mas.lms.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for viewing and managing members.
 */
public class ViewMembersController {

    @FXML
    private TableView<Member> membersTable; // TableView for displaying members

    @FXML
    private TableColumn<Member, Long> idColumn; // Column for member ID

    @FXML
    private TableColumn<Member, String> nameColumn; // Column for member name

    @FXML
    private TableColumn<Member, LocalDate> birthdateColumn; // Column for member birthdate

    @FXML
    private TableColumn<Member, Boolean> goldMemberColumn; // Column for gold member status

    @FXML
    private TableColumn<Member, Integer> daysBorrowedColumn; // Column for days borrowed

    @FXML
    private TableView<Book> borrowedBooksTable; // TableView for displaying books borrowed by the selected member

    @FXML
    private TableColumn<Book, Long> bookIdColumn; // Column for book ID

    @FXML
    private TableColumn<Book, String> bookTitleColumn; // Column for book title

    @FXML
    private Button updateMemberButton; // Button for updating member information

    @FXML
    private Button deleteMemberButton; // Button for deleting a member

    private ObservableList<Member> memberList; // List of members for the TableView
    private ObservableList<Book> borrowedBookList; // List of borrowed books for the secondary TableView

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        // Set up the table columns with property values
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        birthdateColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        goldMemberColumn.setCellValueFactory(new PropertyValueFactory<>("goldMember"));
        daysBorrowedColumn.setCellValueFactory(new PropertyValueFactory<>("daysBorrowed"));

        // Initialize the member list and populate it
        memberList = FXCollections.observableArrayList();
        loadMembers();
        membersTable.setItems(memberList);

        // Add a double-click listener to open a new window when a member is double-clicked
        membersTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Member selectedMember = membersTable.getSelectionModel().getSelectedItem();
                if (selectedMember != null) {
                    showBorrowedBooksForSelectedMember(selectedMember);
                }
            }
        });

        // Set actions for update and delete buttons
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
                    loadMembers(); // Refresh the member list after deletion
                } catch (ConstraintViolationException e) {
                    handleDeleteException(selectedMember.getId());
                }
            }
        } else {
            DeletionUtil.showAlert("Selection Error", "Please select a member to delete.");
        }
    }

    /**
     * Opens a new window to show the list of books borrowed by the selected member.
     *
     * @param selectedMember The member that was selected.
     */
    private void showBorrowedBooksForSelectedMember(Member selectedMember) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ViewBorrowedBooks.fxml"));
            Parent root = loader.load();

            ViewBorrowedBooksController controller = loader.getController();
            List<Book> borrowedBooks = getBorrowedBooksForMember(selectedMember);
            controller.setBorrowedBooks(borrowedBooks);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Books Borrowed by: " + selectedMember.getName());
            stage.setScene(new Scene(root));
            controller.setStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Loading Error", "Unable to load the borrowed books dialog.");
        }
    }

    /**
     * Retrieves the list of books borrowed by the given member.
     *
     * @param member The member for which to retrieve borrowed books.
     * @return A list of books borrowed by the member.
     */
    private List<Book> getBorrowedBooksForMember(Member member) {
        // Using association from the model to get the list of borrowed books
        return member.getBorrows().stream()
                .map(Borrow::getBook)
                .collect(Collectors.toList());
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
            loadMembers(); // Refresh the member list after force deletion
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
