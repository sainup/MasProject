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

public class OverdueBooksReportController {

    @FXML
    private TableView<Borrow> overdueBooksTable;

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

    private ObservableList<Borrow> overdueBooksList;

    @FXML
    public void initialize() {
        bookIdColumn.setCellValueFactory(cellData -> cellData.getValue().getBook().idProperty().asObject());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getBook().titleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().getBook().authorProperty());
        borrowedByColumn.setCellValueFactory(cellData -> cellData.getValue().getMember().nameProperty());
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().getBook().statusProperty());

        overdueBooksList = FXCollections.observableArrayList();
        loadOverdueBooks();
        overdueBooksTable.setItems(overdueBooksList);
    }

    private void loadOverdueBooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Borrow> borrows = session.createQuery("from Borrow where returnDate is null and borrowDate < :date", Borrow.class)
                    .setParameter("date", LocalDate.now().minusDays(14)) // Assuming books are overdue after 14 days
                    .list();
            overdueBooksList.setAll(borrows);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
