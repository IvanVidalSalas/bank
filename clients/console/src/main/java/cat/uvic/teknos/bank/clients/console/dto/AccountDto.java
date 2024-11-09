package cat.uvic.teknos.bank.clients.console.dto;
import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.models.Worker;

import java.util.Set;

public class AccountDto implements cat.uvic.teknos.bank.models.Account {

    private int id;
    private String accountType;
    private int balance;
    private Customer customer;
    private Set<Worker> workers;

    @Override
    public int getId() { return id; }
    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public String getAccountType() { return accountType; }
    @Override
    public void setAccountType(String accountType) { this.accountType = accountType; }

    @Override
    public int getBalance() { return balance;}
    @Override
    public void setBalance(int balance) { this.balance = balance; }

    @Override
    public Customer getCustomer() { return customer; }
    @Override
    public void setCustomer(Customer customer) { this.customer = customer; }

    @Override
    public Set<Worker> getWorker() { return workers; }
    @Override
    public void setWorker(Set<Worker> worker) { this.workers = worker; }
}