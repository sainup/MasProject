package mas.lms.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Class representing a library.
 */
@Entity
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL)
    private Set<Book> books = new HashSet<>(); // The extent

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL)
    private Set<Member> members = new HashSet<>();

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL)
    private Set<Borrow> borrows = new HashSet<>(); // Multi-valued attribute

    private static final String openingHours = "9 AM - 5 PM"; // Class attribute

    /**
     * Default constructor for Library.
     */
    public Library() {}

    /**
     * Constructor for creating a Library with a name.
     * @param name the name of the library
     */
    public Library(String name) {
        this.name = name;
    }

    /**
     * Gets the ID of the library.
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the library.
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the library.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the library.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the set of books in the library.
     * @return the set of books
     */
    public Set<Book> getBooks() {
        return books;
    }

    /**
     * Sets the set of books in the library.
     * @param books the set of books to set
     */
    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    /**
     * Adds a book to the library.
     * @param book the book to add
     */
    public void addBook(Book book) {
        this.books.add(book);
        book.setLibrary(this);
    }

    /**
     * Gets the set of members in the library.
     * @return the set of members
     */
    public Set<Member> getMembers() {
        return members;
    }

    /**
     * Sets the set of members in the library.
     * @param members the set of members to set
     */
    public void setMembers(Set<Member> members) {
        this.members = members;
    }

    /**
     * Adds a member to the library.
     * @param member the member to add
     */
    public void addMember(Member member) {
        this.members.add(member);
        member.setLibrary(this);
    }

    /**
     * Gets the set of borrows in the library.
     * @return the set of borrows
     */
    public Set<Borrow> getBorrows() {
        return borrows;
    }

    /**
     * Sets the set of borrows in the library.
     * @param borrows the set of borrows to set
     */
    public void setBorrows(Set<Borrow> borrows) {
        this.borrows = borrows;
    }

    /**
     * Adds a borrow to the library.
     * @param borrow the borrow to add
     */
    public void addBorrow(Borrow borrow) {
        this.borrows.add(borrow);
        borrow.setLibrary(this);
    }

    /**
     * Gets the opening hours of the library.
     * @return the opening hours
     */
    public static String getOpeningHours() {
        return openingHours;
    }
}
