package cat.uvic.teknos.bank.domain.jpa.models;

import cat.uvic.teknos.bank.models.Loan;
import cat.uvic.teknos.bank.models.Transaction;
import cat.uvic.teknos.bank.models.Account;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "CUSTOMER")

public class Customer implements cat.uvic.teknos.bank.models.Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private int id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "EMAIL")
    private String email;
    @OneToOne(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            targetEntity = Loan.class)
    @PrimaryKeyJoinColumn
    private Loan loan;
    @OneToOne(
            mappedBy = "account",
            cascade = CascadeType.ALL,
            targetEntity = Loan.class)
    @PrimaryKeyJoinColumn
    private Account account;
    @OneToMany(
            mappedBy = "artist",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            targetEntity = cat.uvic.teknos.bank.domain.jpa.models.Transaction.class)
    private Set<Transaction> transactions = new HashSet<>();



    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Loan getLoan() { return loan; }

    @Override
    public void setLoan(Loan loan) {
        this.loan = loan;
        this.loan.setCustomer(this);}

    @Override
    public Account getAccount() { return account; }

    @Override
    public void setAccount(Account account) { this.account = account; }

    @Override
    public Set<Transaction> getTransaction() { return transactions; }

    @Override
    public void setTransaction(Set<Transaction> transaction) { this.transactions = transaction; }
}

