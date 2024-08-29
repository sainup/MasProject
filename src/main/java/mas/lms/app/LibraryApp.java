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

            // Additional Publishers
            Publisher publisher3 = new Publisher("Pragmatic Bookshelf", address1);
            Publisher publisher4 = new Publisher("O'Reilly Media", address2);

            // Categories
            Category category1 = new Category("Programming");
            Category category2 = new Category("Software Engineering");
            Category category3 = new Category("Design Patterns");
            Category category4 = new Category("Databases");
            Category category5 = new Category("Web Development");

            Member member1 = new Member("Anup Karki", LocalDate.of(1990, 1, 1), address1);
            Member member2 = new Member("Kasia Karki", LocalDate.of(1985, 5, 23), address2);

            // Save the library, publishers, and categories first
            session.save(library);
            session.save(publisher1);
            session.save(publisher2);
            session.save(publisher3);
            session.save(publisher4);
            session.save(category1);
            session.save(category2);
            session.save(category3);
            session.save(category4);
            session.save(category5);

            // Books
            Book book1 = new Book("Effective Java", "Joshua Bloch", category1, "1234567891234");
            book1.setPublisher(publisher1);
            Book book2 = new Book("Clean Code", "Robert C. Martin", category2, "9780132350884");
            book2.setPublisher(publisher2);
            Book book3 = new Book("The Pragmatic Programmer", "Andrew Hunt, David Thomas", category3, "9780201616224");
            book3.setPublisher(publisher3);
            Book book4 = new Book("Head First Design Patterns", "Eric Freeman, Bert Bates", category3, "9780596007126");
            book4.setPublisher(publisher1);
            Book book5 = new Book("Refactoring", "Martin Fowler", category2, "9780201485677");
            book5.setPublisher(publisher2);
            Book book6 = new Book("Java Concurrency in Practice", "Brian Goetz", category1, "9780321349606");
            book6.setPublisher(publisher4);
            Book book7 = new Book("You Don't Know JS: Scope & Closures", "Kyle Simpson", category5, "9781449335588");
            book7.setPublisher(publisher4);
            Book book8 = new Book("Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides", category3, "9780201633610");
            book8.setPublisher(publisher1);
            Book book9 = new Book("Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein", category1, "9780262033848");
            book9.setPublisher(publisher3);
            Book book10 = new Book("Database System Concepts", "Abraham Silberschatz, Henry F. Korth, S. Sudarshan", category4, "9780078022159");
            book10.setPublisher(publisher1);

            library.addMember(member1);
            library.addMember(member2);
            library.addBook(book1);
            library.addBook(book2);
            library.addBook(book3);
            library.addBook(book4);
            library.addBook(book5);
            library.addBook(book6);
            library.addBook(book7);
            library.addBook(book8);
            library.addBook(book9);
            library.addBook(book10);

            // Sample Borrowing Data
            Borrow borrow1 = new Borrow(member1, book1, LocalDate.now().minusDays(5), null);
            Borrow borrow2 = new Borrow(member2, book2, LocalDate.now().minusDays(10), null);

            book1.setStatus("borrowed");
            book2.setStatus("borrowed");

            // Sample Reservation Data
            Reservation reservation1 = new Reservation(LocalDate.now().minusDays(2));
            reservation1.setMember(member1);
            reservation1.setBook(book2);

            // Sample Payment Data
            PaymentMethod cardPayment = new Card("1234567890123456", "Anup Karki", "12/25", "123", new BigDecimal("50.00"), LocalDate.now().minusDays(3));
            Payment payment1 = new Payment(member1, 50.00, "membership", cardPayment);

            PaymentMethod cashPayment = new Cash("R12345", new BigDecimal("20.00"), LocalDate.now().minusDays(2));
            Payment payment2 = new Payment(member2, 20.00, "fine", cashPayment);

            session.save(member1);
            session.save(member2);
            session.save(book1);
            session.save(book2);
            session.save(book3);
            session.save(book4);
            session.save(book5);
            session.save(book6);
            session.save(book7);
            session.save(book8);
            session.save(book9);
            session.save(book10);
            session.save(borrow1);
            session.save(borrow2);
            session.save(reservation1);
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
