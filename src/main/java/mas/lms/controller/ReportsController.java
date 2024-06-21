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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReportsController {

    @FXML
    private TableView<Borrow> borrowedBooksTable;

    @FXML
    private TableColumn<Borrow, Number> bookIdColumn;

    @FXML
    private TableColumn<Borrow, String> titleColumn;

    @FXML
    private TableColumn<Borrow, String> authorColumn;

    @FXML
    private TableColumn<Borrow, String> borrowedByColumn;

    @FXML
    private TableColumn<Borrow, String> borrowDateColumn;

    @FXML
    private TableColumn<Borrow, String> returnDateColumn;

    @FXML
    private TableColumn<Borrow, String> statusColumn;

    private ObservableList<Borrow> borrowList;

    @FXML
    public void initialize() {
        bookIdColumn.setCellValueFactory(cellData -> cellData.getValue().getBook().idProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getBook().titleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().getBook().authorProperty());
        borrowedByColumn.setCellValueFactory(cellData -> cellData.getValue().getMember().nameProperty());
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().getBook().statusProperty());

        borrowList = FXCollections.observableArrayList();
        loadBorrowedBooks();
        borrowedBooksTable.setItems(borrowList);
    }

    private void loadBorrowedBooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Borrow> borrows = session.createQuery("from Borrow", Borrow.class).list();

            // Use a Set to ensure no duplicate entries
            Set<Borrow> uniqueBorrows = new HashSet<>(borrows);

            borrowList.setAll(uniqueBorrows);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
