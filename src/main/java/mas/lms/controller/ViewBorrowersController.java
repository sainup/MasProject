package mas.lms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mas.lms.model.Member;

import java.util.List;

/**
 * Controller class for viewing borrowers of a selected book.
 */
public class ViewBorrowersController {

    @FXML
    private TableView<Member> borrowersTable; // TableView for displaying borrowers

    @FXML
    private TableColumn<Member, Long> borrowerIdColumn; // Column for borrower ID

    @FXML
    private TableColumn<Member, String> borrowerNameColumn; // Column for borrower name

    private ObservableList<Member> borrowerList; // List of borrowers

    private Stage stage; // The stage for this controller

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        // Set up the table columns with property values
        borrowerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        borrowerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Initialize the borrower list and set it to the table
        borrowerList = FXCollections.observableArrayList();
        borrowersTable.setItems(borrowerList);
    }

    /**
     * Sets the borrowers to be displayed in the table.
     *
     * @param borrowers List of members who borrowed the selected book.
     */
    public void setBorrowers(List<Member> borrowers) {
        borrowerList.setAll(borrowers);
    }

    /**
     * Sets the stage for this controller.
     *
     * @param stage The stage to set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Closes the window.
     */
    @FXML
    private void closeWindow() {
        if (stage != null) {
            stage.close();
        }
    }
}
