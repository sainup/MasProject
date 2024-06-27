package mas.lms.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Class representing a fine.
 */
@Entity
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private LocalDate dateIssued;

    @ManyToOne
    private Borrow borrow;

    @ManyToOne
    private Member member;

    /**
     * Constructor for creating a Fine.
     *
     * @param amount     the amount of the fine
     * @param dateIssued the date the fine was issued
     */
    public Fine(BigDecimal amount, LocalDate dateIssued) {
        this.amount = amount;
        this.dateIssued = dateIssued;
    }

    /**
     * Default constructor for Fine.
     */
    public Fine() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(LocalDate dateIssued) {
        this.dateIssued = dateIssued;
    }

    public Borrow getBorrow() {
        return borrow;
    }

    public void setBorrow(Borrow borrow) {
        this.borrow = borrow;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
