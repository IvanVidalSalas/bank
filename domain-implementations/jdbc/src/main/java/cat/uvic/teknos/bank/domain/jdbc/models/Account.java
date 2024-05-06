package cat.uvic.teknos.bank.domain.jdbc.models;
import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.models.Worker;

import java.util.Set;

public class Account implements cat.uvic.teknos.bank.models.Account {

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
    public void setCustomer(Set<Customer> customer) { this.customer = customer;  }
    @Override
    public Set<Worker> getWorker() { return worker; }
    @Override
    public void setWorker(Set<Worker> Worker) { this.worker = worker;  }
    @Override
    public String getAccountType() { return accountType; }
    @Override
    public void setAccountType(String accountType) { this.accountType = accountType; }
    @Override
    public int getBalance() { return balance; }
    @Override
    public void setBalance(int balance) {this.balance = balance; }
}