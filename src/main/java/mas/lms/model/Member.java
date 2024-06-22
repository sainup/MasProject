package mas.lms.model;

import jakarta.persistence.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Class representing a library member.
 */
@Entity
public class Member extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int memberId;
    private boolean goldMember;
    private int daysBorrowed;

    @ManyToOne
    private Library library;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private Set<Fine> fines = new HashSet<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(mappedBy = "member")
    private Set<Borrow> borrows = new HashSet<>();

    @Embedded
    private Address address;

    /**
     * Constructor for creating a Member.
     * @param name the name of the member
     * @param birthdate the birthdate of the member
     */
    public Member(String name, LocalDate birthdate, Address address) {
        super(name, birthdate);
        this.address = address;
    }

    /**
     * Default constructor for Member.
     */
    public Member() {}

    /**
     * Gets the ID of the member.
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the member.
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the member ID.
     * @return the member ID
     */
    public int getMemberId() {
        return memberId;
    }

    /**
     * Sets the member ID.
     * @param memberId the member ID to set
     */
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    /**
     * Checks if the member is a gold member.
     * @return true if gold member, false otherwise
     */
    public boolean isGoldMember() {
        return goldMember;
    }

    /**
     * Sets the gold member status.
     * @param goldMember the gold member status to set
     */
    public void setGoldMember(boolean goldMember) {
        this.goldMember = goldMember;
    }

    /**
     * Gets the days borrowed.
     * @return the days borrowed
     */
    public int getDaysBorrowed() {
        return daysBorrowed;
    }

    /**
     * Sets the days borrowed.
     * @param daysBorrowed the days borrowed to set
     */
    public void setDaysBorrowed(int daysBorrowed) {
        this.daysBorrowed = daysBorrowed;
    }

    /**
     * Updates the days borrowed and checks for gold member eligibility.
     * @param days the days to add to the days borrowed
     */
    public void updateDaysBorrowed(int days) {
        this.daysBorrowed += days;
        if (this.daysBorrowed > 100) {
            this.goldMember = true;
        }
    }

    /**
     * Gets the library associated with the member.
     * @return the library
     */
    public Library getLibrary() {
        return library;
    }

    /**
     * Sets the library associated with the member.
     * @param library the library to set
     */
    public void setLibrary(Library library) {
        this.library = library;
    }

    /**
     * Gets the set of fines associated with the member.
     * @return the set of fines
     */
    public Set<Fine> getFines() {
        return fines;
    }

    /**
     * Sets the set of fines associated with the member.
     * @param fines the set of fines to set
     */
    public void setFines(Set<Fine> fines) {
        this.fines = fines;
    }

    // getters and setters for address
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Adds a fine to the member.
     * @param fine the fine to add
     */
    public void addFine(Fine fine) {
        this.fines.add(fine);
        fine.setMember(this);
    }

    /**
     * Gets the set of reservations associated with the member.
     * @return the set of reservations
     */
    public Set<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Sets the set of reservations associated with the member.
     * @param reservations the set of reservations to set
     */
    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Adds a reservation to the member.
     * @param reservation the reservation to add
     */
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setMember(this);
    }

    /**
     * Gets the set of borrows associated with the member.
     * @return the set of borrows
     */
    public Set<Borrow> getBorrows() {
        return borrows;
    }

    /**
     * Sets the set of borrows associated with the member.
     * @param borrows the set of borrows to set
     */
    public void setBorrows(Set<Borrow> borrows) {
        this.borrows = borrows;
    }

    /**
     * Gets the name property for JavaFX.
     * @return the name property
     */
    public StringProperty nameProperty() {
        return new SimpleStringProperty(getName());
    }
}
