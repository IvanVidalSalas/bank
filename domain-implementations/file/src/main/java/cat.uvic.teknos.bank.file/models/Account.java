package cat.uvic.teknos.bank.file.models;

import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.models.Worker;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Account implements cat.uvic.teknos.bank.models.Account, Serializable {
    private int id;
    private int customerId;
    private Set<Customer> customers;
    private int workerId;
    private Set<Worker> workers;
    private String accountType;
    private int balance;

    public Account(int workerId) {
        this.workerId = workerId;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getCustomerId() {
        return customerId;
    }

    @Override
    public void setCustomerId(int id) {
        this.customerId = id;
    }

    @Override
    public Set<Customer> getCustomer() {
        return customers;
    }

    @Override
    public void setCustomer(Customer customer) {
        if (customers == null) {
            customers = new HashSet<>();
        }
        customers.add(customer);
    }

    @Override
    public void setCustomer(Set<Customer> customer) {
        this.customers = customer;
    }

    @Override
    public int getWorkerId() {
        return workerId;
    }

    @Override
    public Set<Worker> getWorker() {
        return workers;
    }

    @Override
    public void setWorker(Worker worker) {
        if (workers == null) {
            workers = new HashSet<>();
        }
        workers.add(worker);
    }

    @Override
    public String getaccountType() {
        return accountType;
    }

    @Override
    public void setaccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void setbalance(int balance) {
        this.balance = balance;
    }
}
