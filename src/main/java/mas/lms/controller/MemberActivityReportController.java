package mas.lms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mas.lms.model.Borrow;
import mas.lms.model.Payment;
import mas.lms.model.Reservation;
import mas.lms.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for generating and displaying the Member Activity Report.
 */
public class MemberActivityReportController {

    @FXML
    private TableView<MemberActivity> memberActivityTable;

    @FXML
    private TableColumn<MemberActivity, Long> bookIdColumn;

    @FXML
    private TableColumn<MemberActivity, String> titleColumn;

    @FXML
    private TableColumn<MemberActivity, String> authorColumn;

    @FXML
    private TableColumn<MemberActivity, String> borrowedByColumn;

    @FXML
    private TableColumn<MemberActivity, LocalDate> borrowDateColumn;

    @FXML
    private TableColumn<MemberActivity, LocalDate> returnDateColumn;

    @FXML
    private TableColumn<MemberActivity, String> statusColumn;

    @FXML
    private TableColumn<MemberActivity, String> activityTypeColumn;

    @FXML
    private TableColumn<MemberActivity, Double> amountColumn;

    private ObservableList<MemberActivity> memberActivityList;

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        borrowedByColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedBy"));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        activityTypeColumn.setCellValueFactory(new PropertyValueFactory<>("activityType"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        memberActivityList = FXCollections.observableArrayList();
        loadMemberActivity();
        memberActivityTable.setItems(memberActivityList);
    }

    /**
     * Loads the member activity data from the database.
     */
    private void loadMemberActivity() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<MemberActivity> activities = new ArrayList<>();

            // Load borrow data
            List<Borrow> borrows = session.createQuery("from Borrow", Borrow.class).list();
            for (Borrow borrow : borrows) {
                activities.add(new MemberActivity(borrow));
            }

            // Load payment data
            List<Payment> payments = session.createQuery("from Payment", Payment.class).list();
            for (Payment payment : payments) {
                activities.add(new MemberActivity(payment));
            }

            // Load reservation data
            List<Reservation> reservations = session.createQuery("from Reservation", Reservation.class).list();
            for (Reservation reservation : reservations) {
                activities.add(new MemberActivity(reservation));
            }

            memberActivityList.setAll(activities);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class MemberActivity {
        private Long bookId;
        private String title;
        private String author;
        private String borrowedBy;
        private LocalDate borrowDate;
        private LocalDate returnDate;
        private String status;
        private String activityType;
        private Double amount;

        public MemberActivity(Borrow borrow) {
            this.bookId = borrow.getBook().getId();
            this.title = borrow.getBook().getTitle();
            this.author = borrow.getBook().getAuthor();
            this.borrowedBy = borrow.getMember().getName();
            this.borrowDate = borrow.getBorrowDate();
            this.returnDate = borrow.getReturnDate();
            this.status = borrow.getBook().getStatus();
            this.activityType = "Borrow";
            this.amount = null;
        }

        public MemberActivity(Payment payment) {
            this.bookId = null;
            this.title = null;
            this.author = null;
            this.borrowedBy = payment.getMember().getName();
            this.borrowDate = null;
            this.returnDate = null;
            this.status = null;
            this.activityType = "Payment";
            this.amount = payment.getAmount();
        }

        public MemberActivity(Reservation reservation) {
            this.bookId = reservation.getBook().getId();
            this.title = reservation.getBook().getTitle();
            this.author = reservation.getBook().getAuthor();
            this.borrowedBy = reservation.getMember().getName();
            this.borrowDate = reservation.getReservationDate();
            this.returnDate = null;
            this.status = reservation.getBook().getStatus();
            this.activityType = "Reservation";
            this.amount = null;
        }

        // Getters and setters for the properties

        public Long getBookId() {
            return bookId;
        }

        public void setBookId(Long bookId) {
            this.bookId = bookId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getBorrowedBy() {
            return borrowedBy;
        }

        public void setBorrowedBy(String borrowedBy) {
            this.borrowedBy = borrowedBy;
        }

        public LocalDate getBorrowDate() {
            return borrowDate;
        }

        public void setBorrowDate(LocalDate borrowDate) {
            this.borrowDate = borrowDate;
        }

        public LocalDate getReturnDate() {
            return returnDate;
        }

        public void setReturnDate(LocalDate returnDate) {
            this.returnDate = returnDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getActivityType() {
            return activityType;
        }

        public void setActivityType(String activityType) {
            this.activityType = activityType;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }
    }
}
