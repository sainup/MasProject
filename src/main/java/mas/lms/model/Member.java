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

    public Member(String name, LocalDate birthdate) {
        super(name, birthdate);
    }

    public Member() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public boolean isGoldMember() {
        return goldMember;
    }

    public void setGoldMember(boolean goldMember) {
        this.goldMember = goldMember;
    }

    public int getDaysBorrowed() {
        return daysBorrowed;
    }

    public void setDaysBorrowed(int daysBorrowed) {
        this.daysBorrowed = daysBorrowed;
    }

    public void updateDaysBorrowed(int days) {
        this.daysBorrowed += days;
        if (this.daysBorrowed > 100) {
            this.goldMember = true;
        }
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public Set<Fine> getFines() {
        return fines;
    }

    public void setFines(Set<Fine> fines) {
        this.fines = fines;
    }

    public void addFine(Fine fine) {
        this.fines.add(fine);
        fine.setMember(this);
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setMember(this);
    }

    public Set<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(Set<Borrow> borrows) {
        this.borrows = borrows;
    }

    // Property method for JavaFX
    public StringProperty nameProperty() {
        return new SimpleStringProperty(getName());
    }
}
