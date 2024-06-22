package mas.lms.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
        primaryStage.setTitle("Library Management System");
        primaryStage.setMinWidth(600); // Set minimum width
        primaryStage.setMinHeight(400); // Set minimum height
        primaryStage.show();
    }

    private void addSampleData() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Create library
            Library library = new Library("Central Library");

            // Create addresses
            Address address1 = new Address("123 Main St", "Anytown", "12345");
            Address address2 = new Address("456 Maple Ave", "Othertown", "67890");

            // Create members with addresses
            Member member1 = new Member("Anup Karki", LocalDate.of(1990, 1, 1), address1);
            Member member2 = new Member("Kasia Karki", LocalDate.of(1985, 5, 23), address2);

            // Create categories
            Category category1 = new Category("Programming");
            Category category2 = new Category("Software Development");

            // Create books with categories
            Book book1 = new Book("Effective Java", "Joshua Bloch", "1234567891234", category1);
            Book book2 = new Book("Clean Code", "Robert C. Martin", "9780132350884", category2);

            // Add members and books to library
            library.addMember(member1);
            library.addMember(member2);
            library.addBook(book1);
            library.addBook(book2);

            // Create borrowing records
            Borrow borrow1 = new Borrow(member1, book1, LocalDate.now().minusDays(5), null);
            Borrow borrow2 = new Borrow(member2, book2, LocalDate.now().minusDays(10), null);
            book1.setStatus("borrowed");
            book2.setStatus("borrowed");

            // Create payment methods
            PaymentMethod cardPayment = new Card("1111222233334444", "Anup Karki", "12/23", "123", BigDecimal.valueOf(50.0), LocalDate.now());
            PaymentMethod cashPayment = new Cash("R123456", BigDecimal.valueOf(30.0), LocalDate.now());

            // Create payments
            Payment membershipPayment1 = new Payment(member1, 50.0, "membership", cardPayment);
            Payment finePayment1 = new Payment(member2, 30.0, "fine", cashPayment);

            // Create fines
            Fine fine1 = new Fine(BigDecimal.valueOf(10.0), LocalDate.now().minusDays(3));
            member1.addFine(fine1);

            // Save all entities to the database
            session.save(library);
            session.save(category1);
            session.save(category2);
            session.save(borrow1);
            session.save(borrow2);
            session.save(cardPayment);
            session.save(cashPayment);
            session.save(membershipPayment1);
            session.save(finePayment1);
            session.save(fine1);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
