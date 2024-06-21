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

    public Person(String name, LocalDate birthdate) {
        this.name = name;
        this.birthdate = birthdate;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
}
