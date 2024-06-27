package mas.lms.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import mas.lms.model.*;
import mas.lms.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class LibraryApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        addSampleData();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        Scene scene = new Scene(loader.load(), 800, 600); // Set initial size
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/styles.css")).toExternalForm()); // Add CSS
        primaryStage.setScene(scene);
        primaryStage.setTitle("S25600 Library Management System");
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icon.png")))); // Set the icon
        primaryStage.setMinWidth(600); // Set minimum width
        primaryStage.setMinHeight(400); // Set minimum height
        primaryStage.show();
    }

    private void addSampleData() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Library library = new Library("S25600 Library Management System");

            Address address1 = new Address("123 Test St", "Test", "12345");
            Address address2 = new Address("456 Dummy St", "Dummy", "67890");

            Publisher publisher1 = new Publisher("Tech Books Publishing", address1);
            Publisher publisher2 = new Publisher("Clean Code Press", address2);

            Category category1 = new Category("Programming");
            Category category2 = new Category("Software Engineering");

            Member member1 = new Member("Anup Karki", LocalDate.of(1990, 1, 1), address1);
            Member member2 = new Member("Kasia Karki", LocalDate.of(1985, 5, 23), address2);

            // Save the library, publishers, and categories first
            session.save(library);
            session.save(publisher1);
            session.save(publisher2);
            session.save(category1);
            session.save(category2);

            Book book1 = new Book("Effective Java", "Joshua Bloch", category1, "1234567891234");
            book1.setPublisher(publisher1);
            Book book2 = new Book("Clean Code", "Robert C. Martin", category2, "9780132350884");
            book2.setPublisher(publisher2);

            library.addMember(member1);
            library.addMember(member2);
            library.addBook(book1);
            library.addBook(book2);

            // Sample Borrowing Data
            Borrow borrow1 = new Borrow(member1, book1, LocalDate.now().minusDays(5), null);
            Borrow borrow2 = new Borrow(member2, book2, LocalDate.now().minusDays(10), null);

            book1.setStatus("borrowed");
            book2.setStatus("borrowed");

            // Sample Reservation Data
            Reservation reservation1 = new Reservation(LocalDate.now().minusDays(2));
            reservation1.setMember(member1);
            reservation1.setBook(book2);

            Reservation reservation2 = new Reservation(LocalDate.now().minusDays(1));
            reservation2.setMember(member2);
            reservation2.setBook(book1);

            // Sample Payment Data
            PaymentMethod cardPayment = new Card("1234567890123456", "Anup Karki", "12/25", "123", new BigDecimal("50.00"), LocalDate.now().minusDays(3));
            Payment payment1 = new Payment(member1, 50.00, "membership", cardPayment);

            PaymentMethod cashPayment = new Cash("R12345", new BigDecimal("20.00"), LocalDate.now().minusDays(2));
            Payment payment2 = new Payment(member2, 20.00, "fine", cashPayment);

            session.save(member1);
            session.save(member2);
            session.save(book1);
            session.save(book2);
            session.save(borrow1);
            session.save(borrow2);
            session.save(reservation1);
            session.save(reservation2);
            session.save(cardPayment);
            session.save(payment1);
            session.save(cashPayment);
            session.save(payment2);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
