package mas.lms.model;

import jakarta.persistence.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class representing a book category.
 */
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    /**
     * Constructor for creating a Category.
     *
     * @param name the name of the category
     */
    public Category(String name) {
        this.name = name;
    }

    /**
     * Default constructor for Category.
     */
    public Category() {
    }

    /**
     * Gets the ID of the category.
     *
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the category.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the category.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the category.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name property for JavaFX.
     *
     * @return the name property
     */
    public StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }
}
