package mas.lms.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mas.lms.model.Book;
import mas.lms.model.Borrow;
import mas.lms.util.HibernateUtil;
import mas.lms.model.Library;
import mas.lms.model.Member;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

            Library library = new Library("Central Library");

            Member member1 = new Member("Anup Karki", LocalDate.of(1990, 1, 1));
            Member member2 = new Member("Kasia Karki", LocalDate.of(1985, 5, 23));

            Book book1 = new Book("Effective Java", "Joshua Bloch", "1234567891234");
            Book book2 = new Book("Clean Code", "Robert C. Martin", "9780132350884");

            library.addMember(member1);
            library.addMember(member2);
            library.addBook(book1);
            library.addBook(book2);

            // Sample Borrowing Data
            Borrow borrow1 = new Borrow(member1, book1, LocalDate.now().minusDays(5), null);
            Borrow borrow2 = new Borrow(member2, book2, LocalDate.now().minusDays(10), null);

            book1.setStatus("borrowed");
            book2.setStatus("borrowed");

            session.save(library);
            session.save(borrow1);
            session.save(borrow2);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
