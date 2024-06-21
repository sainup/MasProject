package mas.lms.model;

import jakarta.persistence.Embeddable;

/**
 * Class representing an address.
 */
@Embeddable
public class Address {

    private String street;
    private String city;
    private String zipCode;

    /**
     * Constructor for creating an Address.
     * @param street the street address
     * @param city the city
     * @param zipCode the zip code
     */
    public Address(String street, String city, String zipCode) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    /**
     * Default constructor for Address.
     */
    public Address() {}

    /**
     * Gets the street address.
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street address.
     * @param street the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets the city.
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the zip code.
     * @return the zip code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets the zip code.
     * @param zipCode the zip code to set
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
