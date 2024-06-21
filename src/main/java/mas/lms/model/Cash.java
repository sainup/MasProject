package mas.lms.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Class representing a cash payment method.
 */
@Entity
@DiscriminatorValue("CASH")
public class Cash extends PaymentMethod {

    private String receiptNumber;

    /**
     * Constructor for creating a Cash payment method.
     * @param receiptNumber the receipt number
     * @param amount the amount of the payment
     * @param date the date of the payment
     */
    public Cash(String receiptNumber, BigDecimal amount, LocalDate date) {
        super(amount, date);
        this.receiptNumber = receiptNumber;
    }

    /**
     * Default constructor for Cash.
     */
    public Cash() {}

    /**
     * Gets the receipt number.
     * @return the receipt number
     */
    public String getReceiptNumber() {
        return receiptNumber;
    }

    /**
     * Sets the receipt number.
     * @param receiptNumber the receipt number to set
     */
    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }
}
