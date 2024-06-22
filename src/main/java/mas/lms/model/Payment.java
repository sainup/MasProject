package mas.lms.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Class representing a payment.
 */
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    private double amount;
    private String type;
    private LocalDate date;
    @ManyToOne
    private PaymentMethod paymentMethod;

    /**
     * Default constructor for Payment.
     */
    public Payment() {}

    /**
     * Constructor for creating a Payment.
     * @param member the member making the payment
     * @param amount the amount of the payment
     * @param type the type of the payment (e.g., fine, membership)
     */
    public Payment(Member member, double amount, String type, PaymentMethod paymentMethod) {
        this.member = member;
        this.amount = amount;
        this.type = type;
        this.date = LocalDate.now();
        this.paymentMethod = paymentMethod;
    }


    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Gets the ID of the payment.
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the payment.
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the member making the payment.
     * @return the member
     */
    public Member getMember() {
        return member;
    }

    /**
     * Sets the member making the payment.
     * @param member the member to set
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * Gets the amount of the payment.
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the payment.
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the type of the payment.
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the payment.
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the date of the payment.
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the payment.
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }


}
