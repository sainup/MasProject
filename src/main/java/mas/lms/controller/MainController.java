package mas.lms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Controller class for managing the main application window.
 */
public class MainController {

    @FXML
    private BorderPane mainPane;

    /**
     * Handles the action event when the "Add Book" menu item is clicked.
     * Loads the AddBook.fxml UI.
     *
     * @param event The action event triggered by clicking the "Add Book" menu item.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void showAddBook(ActionEvent event) throws IOException {
        loadUI("AddBook");
    }

    /**
     * Handles the action event when the "Update Book" menu item is clicked.
     * Loads the UpdateBook.fxml UI.
     *
     * @param event The action event triggered by clicking the "Update Book" menu item.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void showUpdateBook(ActionEvent event) throws IOException {
        loadUI("UpdateBook");
    }

    /**
     * Handles the action event when the "Delete Book" menu item is clicked.
     * Loads the DeleteBook.fxml UI.
     *
     * @param event The action event triggered by clicking the "Delete Book" menu item.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void showDeleteBook(ActionEvent event) throws IOException {
        loadUI("DeleteBook");
    }

    /**
     * Handles the action event when the "View Books" menu item is clicked.
     * Loads the ViewBooks.fxml UI.
     *
     * @param event The action event triggered by clicking the "View Books" menu item.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void showViewBooks(ActionEvent event) throws IOException {
        loadUI("ViewBooks");
    }

    /**
     * Handles the action event when the "Add Member" menu item is clicked.
     * Loads the AddMember.fxml UI.
     *
     * @param event The action event triggered by clicking the "Add Member" menu item.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void showAddMember(ActionEvent event) throws IOException {
        loadUI("AddMember");
    }

    /**
     * Handles the action event when the "Update Member" menu item is clicked.
     * Loads the UpdateMember.fxml UI.
     *
     * @param event The action event triggered by clicking the "Update Member" menu item.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void showUpdateMember(ActionEvent event) throws IOException {
        loadUI("UpdateMember");
    }

    /**
     * Handles the action event when the "Delete Member" menu item is clicked.
     * Loads the DeleteMember.fxml UI.
     *
     * @param event The action event triggered by clicking the "Delete Member" menu item.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void showDeleteMember(ActionEvent event) throws IOException {
        loadUI("DeleteMember");
    }

    /**
     * Handles the action event when the "View Members" menu item is clicked.
     * Loads the ViewMembers.fxml UI.
     *
     * @param event The action event triggered by clicking the "View Members" menu item.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void showViewMembers(ActionEvent event) throws IOException {
        loadUI("ViewMembers");
    }

    /**
     * Handles the action event when the "Record Borrowing" menu item is clicked.
     * Loads the RecordBorrowing.fxml UI.
     *
     * @param event The action event triggered by clicking the "Record Borrowing" menu item.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void showRecordBorrowing(ActionEvent event) throws IOException {
        loadUI("RecordBorrowing");
    }

    /**
     * Handles the action event when the "Return Book" menu item is clicked.
     * Loads the ReturnBook.fxml UI.
     *
     * @param event The action event triggered by clicking the "Return Book" menu item.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void showReturnBook(ActionEvent event) throws IOException {
        loadUI("ReturnBook");
    }

    /**
     * Handles the action event when the "Fine Payment" menu item is clicked.
     * Loads the FinePayment.fxml UI.
     *
     * @param event The action event triggered by clicking the "Fine Payment" menu item.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void showFinePayment(ActionEvent event) throws IOException {
        loadUI("FinePayment");
    }

    /**
     * Handles the action event when the "Membership Payment" menu item is clicked.
     * Loads the MembershipPayment.fxml UI.
     *
     * @param event The action event triggered by clicking the "Membership Payment" menu item.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void showMembershipPayment(ActionEvent event) throws IOException {
        loadUI("MembershipPayment");
    }

    /**
     * Handles the action event when the "Borrowed Books Report" menu item is clicked.
     * Loads the BorrowedBooksReport.fxml UI.
     *
     * @param event The action event triggered by clicking the "Borrowed Books Report" menu item.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void showBorrowedBooksReport(ActionEvent event) throws IOException {
        loadUI("BorrowedBooksReport");
    }

    /**
     * Handles the action event when the "Overdue Books Report" menu item is clicked.
     * Loads the OverdueBooksReport.fxml UI.
     *
     * @param event The action event triggered by clicking the "Overdue Books Report" menu item.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void showOverdueBooksReport(ActionEvent event) throws IOException {
        loadUI("OverdueBooksReport");
    }

    /**
     * Handles the action event when the "Member Activity Report" menu item is clicked.
     * Loads the MemberActivityReport.fxml UI.
     *
     * @param event The action event triggered by clicking the "Member Activity Report" menu item.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void showMemberActivityReport(ActionEvent event) throws IOException {
        loadUI("MemberActivityReport");
    }

    /**
     * Handles the action event when the "Reserve Book" menu item is clicked.
     * Loads the ReserveBook.fxml UI.
     *
     * @param event The action event triggered by clicking the "Reserve Book" menu item.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void showReserveBook(ActionEvent event) throws IOException {
        loadUI("ReserveBook");
    }

    /**
     * Handles the action event when the "Search Books" menu item is clicked.
     * Loads the SearchBooks.fxml UI.
     *
     * @param event The action event triggered by clicking the "Search Books" menu item.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    private void showSearchBooks(ActionEvent event) throws IOException {
        loadUI("SearchBooks");
    }

    @FXML
    private void showViewPayments(ActionEvent event) throws IOException {
        loadUI("ViewPayments");
    }

    /**
     * Loads the specified UI file into the center of the main pane.
     *
     * @param ui The name of the FXML file to load (without the .fxml extension).
     * @throws IOException if an I/O error occurs during loading.
     */
    private void loadUI(String ui) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + ui + ".fxml"));
            Parent root = loader.load();
            mainPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Loading Error", "Unable to load the requested UI: " + ui);
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
