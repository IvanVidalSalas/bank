package cat.uvic.teknos.bank.domain.jdbc.models;
import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.models.Worker;

import java.util.Set;

public class Account implements cat.uvic.teknos.bank.models.Account {

    private int id;
    private int customerid;
    private Set<Customer> customer;
    private int workerid;
    private Set<Worker> worker;
    private String accountType;
    private int balance;


    @Override
    public int getId() { return id; }
    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public int getCustomerId() { return customerid; }
    @Override
    public void setCustomerId(int customerid) { this.customerid = customerid; }

    @Override
    public Set<Customer> getCustomer() { return customer; }
    @Override
    public void setCustomer(Customer Customer) {

    }

    @Override
    public void setCustomer(Set<Customer> customer) { this.customer = customer; }

    @Override
    public int getWorkerId() {
        return 0;
    }


    @Override
    public Set<Worker> getWorker() { return worker; }

    @Override
    public void setWorker(Worker Worker) {

    }

    @Override
    public String getaccountType() { return accountType; }
    @Override
    public void setaccountType(String accountType) { this.accountType = accountType; }

    @Override
    public int getBalance() { return balance; }
    @Override
    public void setbalance(int balance) {this.balance = balance; }

}