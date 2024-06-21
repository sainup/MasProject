package mas.lms.model;

import jakarta.persistence.*;
import javafx.beans.property.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Class representing a book in the library.
 */
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String status;

    @ManyToOne
    private Library library;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<Borrow> borrows = new HashSet<>();

    /**
     * Constructor for creating a Book.
     * @param title the title of the book
     * @param author the author of the book
     * @param isbn the ISBN of the book
     */
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.status = "available";
    }

    /**
     * Default constructor for Book.
     */
    public Book() {}

    /**
     * Gets the ID of the book.
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the book.
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the title of the book.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the title property for JavaFX.
     * @return the title property
     */
    public StringProperty titleProperty() {
        return new SimpleStringProperty(title);
    }

    /**
     * Gets the author of the book.
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the author property for JavaFX.
     * @return the author property
     */
    public StringProperty authorProperty() {
        return new SimpleStringProperty(author);
    }

    /**
     * Gets the ISBN of the book.
     * @return the ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of the book.
     * @param isbn the ISBN to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets the status of the book.
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the book.
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the status property for JavaFX.
     * @return the status property
     */
    public StringProperty statusProperty() {
        return new SimpleStringProperty(status);
    }

    /**
     * Gets the ID property for JavaFX.
     * @return the ID property
     */
    public LongProperty idProperty() {
        return new SimpleLongProperty(id);
    }

    /**
     * Gets the borrows associated with the book.
     * @return the borrows
     */
    public Set<Borrow> getBorrows() {
        return borrows;
    }

    /**
     * Sets the borrows associated with the book.
     * @param borrows the borrows to set
     */
    public void setBorrows(Set<Borrow> borrows) {
        this.borrows = borrows;
    }

    /**
     * Adds a borrow to the book.
     * @param borrow the borrow to add
     */
    public void addBorrow(Borrow borrow) {
        this.borrows.add(borrow);
        borrow.setBook(this);
    }

    /**
     * Gets the library associated with the book.
     * @return the library
     */
    public Library getLibrary() {
        return library;
    }

    /**
     * Sets the library associated with the book.
     * @param library the library to set
     */
    public void setLibrary(Library library) {
        this.library = library;
    }

    /**
     * Overriding toString method to return book details.
     * @return String representation of the book
     */
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", status='" + status + '\'' +
                ", library=" + library +
                '}';
    }
}
