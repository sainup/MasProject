package mas.lms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mas.lms.model.Borrow;
import mas.lms.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller class for generating and displaying the Member Activity Report.
 */
public class MemberActivityReportController {

    @FXML
    private TableView<Borrow> memberActivityTable;

    @FXML
    private TableColumn<Borrow, Long> bookIdColumn;

    @FXML
    private TableColumn<Borrow, String> titleColumn;

    @FXML
    private TableColumn<Borrow, String> authorColumn;

    @FXML
    private TableColumn<Borrow, String> borrowedByColumn;

    @FXML
    private TableColumn<Borrow, LocalDate> borrowDateColumn;

    @FXML
    private TableColumn<Borrow, LocalDate> returnDateColumn;

    @FXML
    private TableColumn<Borrow, String> statusColumn;

    private ObservableList<Borrow> memberActivityList;

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        bookIdColumn.setCellValueFactory(cellData -> cellData.getValue().getBook().idProperty().asObject());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getBook().titleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().getBook().authorProperty());
        borrowedByColumn.setCellValueFactory(cellData -> cellData.getValue().getMember().nameProperty());
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().getBook().statusProperty());

        memberActivityList = FXCollections.observableArrayList();
        loadMemberActivity();
        memberActivityTable.setItems(memberActivityList);
    }

    /**
     * Loads the member activity data from the database.
     */
    private void loadMemberActivity() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Borrow> borrows = session.createQuery("from Borrow", Borrow.class).list();
            memberActivityList.setAll(borrows);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
