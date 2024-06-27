package mas.lms.model;

import jakarta.persistence.Entity;

import java.time.LocalDate;

/**
 * Class representing a librarian.
 */
@Entity
public class Librarian extends Person {

    private int employeeId;
    private LocalDate hireDate;

    /**
     * Constructor for creating a Librarian.
     *
     * @param name       the name of the librarian
     * @param birthdate  the birthdate of the librarian
     * @param employeeId the employee ID of the librarian
     * @param hireDate   the hire date of the librarian
     */
    public Librarian(String name, LocalDate birthdate, int employeeId, LocalDate hireDate) {
        super(name, birthdate);
        this.employeeId = employeeId;
        this.hireDate = hireDate;
    }

    /**
     * Default constructor for Librarian.
     */
    public Librarian() {
        super();
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
}
