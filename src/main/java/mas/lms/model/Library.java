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
    private Set<Book> books = new HashSet<>();

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL)
    private Set<Member> members = new HashSet<>();

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL)
    private Set<Borrow> borrows = new HashSet<>();

    public Library() {}

    public Library(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.setLibrary(this);
    }

    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }

    public void addMember(Member member) {
        this.members.add(member);
        member.setLibrary(this);
    }

    public Set<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(Set<Borrow> borrows) {
        this.borrows = borrows;
    }

    public void addBorrow(Borrow borrow) {
        this.borrows.add(borrow);
        borrow.setLibrary(this);
    }
}
