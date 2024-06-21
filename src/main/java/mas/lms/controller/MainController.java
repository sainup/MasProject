package mas.lms.controller;

// Existing imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private void showAddBook(ActionEvent event) throws IOException {
        loadUI("AddBook");
    }

    @FXML
    private void showUpdateBook(ActionEvent event) throws IOException {
        loadUI("UpdateBook");
    }

    @FXML
    private void showDeleteBook(ActionEvent event) throws IOException {
        loadUI("DeleteBook");
    }

    @FXML
    private void showViewBooks(ActionEvent event) throws IOException {
        loadUI("ViewBooks");
    }

    @FXML
    private void showAddMember(ActionEvent event) throws IOException {
        loadUI("AddMember");
    }

    @FXML
    private void showUpdateMember(ActionEvent event) throws IOException {
        loadUI("UpdateMember");
    }

    @FXML
    private void showDeleteMember(ActionEvent event) throws IOException {
        loadUI("DeleteMember");
    }

    @FXML
    private void showViewMembers(ActionEvent event) throws IOException {
        loadUI("ViewMembers");
    }

    @FXML
    private void showRecordBorrowing(ActionEvent event) throws IOException {
        loadUI("RecordBorrowing");
    }

    @FXML
    private void showReturnBook(ActionEvent event) throws IOException {
        loadUI("ReturnBook");
    }

    @FXML
    private void showFinePayment(ActionEvent event) throws IOException {
        loadUI("FinePayment");
    }

    @FXML
    private void showMembershipPayment(ActionEvent event) throws IOException {
        loadUI("MembershipPayment");
    }

    @FXML
    private void showBorrowedBooksReport(ActionEvent event) throws IOException {
        loadUI("BorrowedBooksReport");
    }

    @FXML
    private void showOverdueBooksReport(ActionEvent event) throws IOException {
        loadUI("OverdueBooksReport");
    }

    @FXML
    private void showMemberActivityReport(ActionEvent event) throws IOException {
        loadUI("MemberActivityReport");
    }

    @FXML
    private void showReserveBook(ActionEvent event) throws IOException {
        loadUI("ReserveBook");
    }

    @FXML
    private void showSearchBooks(ActionEvent event) throws IOException {
        loadUI("SearchBooks");
    }

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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
