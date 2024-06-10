package cat.uvic.teknos.bank.domain.jpa.models;

import cat.uvic.teknos.bank.models.Account;
import cat.uvic.teknos.bank.models.Transaction;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "WORKER")

public class Worker implements cat.uvic.teknos.bank.models.Worker{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORKER_ID")
    private int id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @OneToMany(mappedBy="worker",cascade = CascadeType.ALL,orphanRemoval = true, targetEntity = Transaction.class)
    private Set<Transaction> transaction;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Account.class)
    @JoinTable (name = "WORKER_ACCOUNT",
            joinColumns = {@JoinColumn (name = "WORKER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ACCOUNT_ID")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"WORKER_ID", "ACCOUNT_ID"})})
    private Set<Account> account;

    @Override
    public int getId() { return id; }
    @Override
    public void setId(int id) { this.id = id; }
    @Override
    public String getFirstName() { return firstName; }
    @Override
    public void setFirstName(String firstName) { this.firstName = firstName; }

    @Override
    public String getLastName() { return lastName; }
    @Override
    public void setLastName(String lastName) { this.lastName = lastName; }

    @Override
    public Set<Transaction> getTransaction() { return transaction; }
    @Override
    public void setTransaction(Set<Transaction> transaction) { this.transaction = transaction; }

    @Override
    public Set<Account> getAccount() { return account; }
    @Override
    public void setAccount(Set<Account> account) { this.account = account; }
}
