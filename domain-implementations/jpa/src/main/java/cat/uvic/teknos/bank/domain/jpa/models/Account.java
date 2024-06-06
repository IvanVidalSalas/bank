package cat.uvic.teknos.bank.domain.jpa.models;
import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.models.Worker;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "ACCOUNT")
public class Account implements cat.uvic.teknos.bank.models.Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private int id;
    @Column(name = "ACCOUNT_TYPE")
    private String accountType;
    @Column(name = "BALANCE")
    private int balance;
    @ManyToOne (targetEntity = cat.uvic.teknos.bank.domain.jpa.models.Customer.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;
    @ManyToMany (
            mappedBy = "accounts",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = cat.uvic.teknos.bank.domain.jpa.models.Worker.class)
    private Set<Worker> worker;

    @Override
    public int getId() { return id; }
    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public String getAccountType() { return accountType; }
    @Override
    public void setAccountType(String accountType) { this.accountType = accountType; }

    @Override
    public int getBalance() { return balance; }
    @Override
    public void setBalance(int balance) {this.balance = balance; }

    @Override
    public Customer getCustomer() { return customer; }
    @Override
    public void setCustomer(Customer customer) { this.customer = customer; }

    @Override
    public Set<Worker> getWorker() { return worker; }
    @Override
    public void setWorker(Set <Worker> worker) { this.worker = worker; }

}