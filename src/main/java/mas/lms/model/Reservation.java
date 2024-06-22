package mas.lms.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Class representing a reservation.
 */
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate reservationDate;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Book book;

    /**
     * Constructor for creating a Reservation.
     * @param reservationDate the date of the reservation
     */
    public Reservation(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    /**
     * Default constructor for Reservation.
     */
    public Reservation() {}

    /**
     * Gets the ID of the reservation.
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the reservation.
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the date of the reservation.
     * @return the reservation date
     */
    public LocalDate getReservationDate() {
        return reservationDate;
    }

    /**
     * Sets the date of the reservation.
     * @param reservationDate the reservation date to set
     */
    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    /**
     * Gets the member making the reservation.
     * @return the member
     */
    public Member getMember() {
        return member;
    }

    /**
     * Sets the member making the reservation.
     * @param member the member to set
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * Gets the book being reserved.
     * @return the book
     */
    public Book getBook() {
        return book;
    }

    /**
     * Sets the book being reserved.
     * @param book the book to set
     */
    public void setBook(Book book) {
        this.book = book;
    }
}
