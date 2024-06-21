package mas.lms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mas.lms.util.HibernateUtil;
import mas.lms.model.Payment;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

public class ViewPaymentsController {

    @FXML
    private TableView<Payment> paymentsTable;

    @FXML
    private TableColumn<Payment, Long> idColumn;

    @FXML
    private TableColumn<Payment, String> memberNameColumn;

    @FXML
    private TableColumn<Payment, Double> amountColumn;

    @FXML
    private TableColumn<Payment, String> typeColumn;

    @FXML
    private TableColumn<Payment, LocalDate> dateColumn;

    private ObservableList<Payment> paymentList;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        memberNameColumn.setCellValueFactory(cellData -> cellData.getValue().getMember().nameProperty());
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        paymentList = FXCollections.observableArrayList();
        loadPayments();
        paymentsTable.setItems(paymentList);
    }

    private void loadPayments() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Payment> payments = session.createQuery("from Payment", Payment.class).list();
            paymentList.setAll(payments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
