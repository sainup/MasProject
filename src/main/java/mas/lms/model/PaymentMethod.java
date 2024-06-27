package mas.lms.model;

import jakarta.persistence.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Abstract class representing a payment method.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "payment_type", discriminatorType = DiscriminatorType.STRING)
public abstract class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private LocalDate date;

    /**
     * Constructor for creating a PaymentMethod.
     *
     * @param amount the amount of the payment
     * @param date   the date of the payment
     */
    public PaymentMethod(BigDecimal amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }

    /**
     * Default constructor for PaymentMethod.
     */
    public PaymentMethod() {
    }

    /**
     * Gets the ID of the payment method.
     *
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the payment method.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the amount of the payment.
     *
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the payment.
     *
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Gets the date of the payment.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the payment.
     *
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public abstract String getPaymentDetails();

    public StringProperty toStringProperty() {
        return new SimpleStringProperty(getPaymentDetails());
    }
}
