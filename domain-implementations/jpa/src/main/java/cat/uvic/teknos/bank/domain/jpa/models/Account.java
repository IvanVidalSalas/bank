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
    private int id;
    private Set<Customer> customer;

    private Set<Worker> worker;
    private String accountType;
    private int balance;

    @Override
    public int getId() { return id; }

    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public Set<Customer> getCustomer() { return customer; }

    @Override
    public void setCustomer(Set<Customer> customer) { this.customer = customer; }

    @ManyToMany
    @Override
    public Set<Worker> getWorker() { return worker; }

    @Override
    public void setWorker(Set <Worker> worker) { this.worker = worker; }

    @Override
    public String getAccountType() { return accountType; }

    @Override
    public void setAccountType(String accountType) { this.accountType = accountType; }

    @Override
    public int getBalance() { return balance; }

    @Override
    public void setBalance(int balance) {this.balance = balance; }

}