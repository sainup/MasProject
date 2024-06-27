package mas.lms.model;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Class representing a borrow transaction.
 */
@Entity
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Library library;

    private LocalDate borrowDate;
    private LocalDate returnDate;

    /**
     * Default constructor for Borrow.
     */
    public Borrow() {
    }

    /**
     * Constructor for creating a Borrow.
     *
     * @param member     the member borrowing the book
     * @param book       the book being borrowed
     * @param borrowDate the date of borrowing
     * @param returnDate the date of return
     */
    public Borrow(Member member, Book book, LocalDate borrowDate, LocalDate returnDate) {
        this.member = member;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    /**
     * Gets the ID of the borrow transaction.
     *
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the member borrowing the book.
     *
     * @return the member
     */
    public Member getMember() {
        return member;
    }

    /**
     * Sets the member borrowing the book.
     *
     * @param member the member to set
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * Gets the book being borrowed.
     *
     * @return the book
     */
    public Book getBook() {
        return book;
    }

    /**
     * Sets the book being borrowed.
     *
     * @param book the book to set
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Gets the date of borrowing.
     *
     * @return the borrow date
     */
    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    /**
     * Sets the date of borrowing.
     *
     * @param borrowDate the borrow date to set
     */
    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    /**
     * Gets the date of return.
     *
     * @return the return date
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the date of return.
     *
     * @param returnDate the return date to set
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Gets the library associated with the borrow transaction.
     *
     * @return the library
     */
    public Library getLibrary() {
        return library;
    }

    /**
     * Sets the library associated with the borrow transaction.
     *
     * @param library the library to set
     */
    public void setLibrary(Library library) {
        this.library = library;
    }

    /**
     * Gets the ID of the book being borrowed.
     *
     * @return the book ID
     */
    public Long getBookId() {
        return book != null ? book.getId() : null;
    }

    /**
     * Gets the title of the book being borrowed.
     *
     * @return the book title
     */
    public String getTitle() {
        return book != null ? book.getTitle() : null;
    }

    /**
     * Gets the author of the book being borrowed.
     *
     * @return the book author
     */
    public String getAuthor() {
        return book != null ? book.getAuthor() : null;
    }

    /**
     * Gets the status of the book being borrowed.
     *
     * @return the book status
     */
    public String getStatus() {
        return book != null ? book.getStatus() : null;
    }
}
