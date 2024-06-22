package mas.lms.model;

import jakarta.persistence.*;

/**
 * Class representing a book publisher.
 */
@Entity
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Address address;

    /**
     * Constructor for creating a Publisher.
     * @param name the name of the publisher
     * @param address the address of the publisher
     */
    public Publisher(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    /**
     * Default constructor for Publisher.
     */
    public Publisher() {}

    /**
     * Gets the ID of the publisher.
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the publisher.
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the publisher.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the publisher.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of the publisher.
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the address of the publisher.
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }
}
