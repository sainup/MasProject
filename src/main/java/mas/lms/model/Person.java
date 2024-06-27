package mas.lms.model;

import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;

/**
 * Abstract class representing a person.
 */
@MappedSuperclass
public abstract class Person {

    protected String name;
    protected LocalDate birthdate;

    /**
     * Constructor for creating a Person.
     *
     * @param name      the name of the person
     * @param birthdate the birthdate of the person
     */
    public Person(String name, LocalDate birthdate) {
        this.name = name;
        this.birthdate = birthdate;
    }

    /**
     * Default constructor for Person.
     */
    public Person() {
    }

    /**
     * Gets the name of the person.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the person.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the birthdate of the person.
     *
     * @return the birthdate
     */
    public LocalDate getBirthdate() {
        return birthdate;
    }

    /**
     * Sets the birthdate of the person.
     *
     * @param birthdate the birthdate to set
     */
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
}
