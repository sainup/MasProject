package mas.lms.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Class representing a card payment method.
 */
@Entity
@DiscriminatorValue("CARD")
public class Card extends PaymentMethod {

    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;

    /**
     * Constructor for creating a Card payment method.
     * @param cardNumber the card number
     * @param cardHolderName the card holder's name
     * @param expiryDate the expiry date of the card
     * @param cvv the CVV of the card
     * @param amount the amount of the payment
     * @param date the date of the payment
     */
    public Card(String cardNumber, String cardHolderName, String expiryDate, String cvv, BigDecimal amount, LocalDate date) {
        super(amount, date);
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    /**
     * Default constructor for Card.
     */
    public Card() {}

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Override
    public String getPaymentDetails() {
        return "Card: " + cardHolderName + " (" + cardNumber + ")";
    }
}
