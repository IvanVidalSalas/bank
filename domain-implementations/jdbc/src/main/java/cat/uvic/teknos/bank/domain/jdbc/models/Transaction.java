package cat.uvic.teknos.bank.domain.jdbc.models;

import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.models.Worker;


import java.sql.Date;
import java.util.Set;

public class Transaction implements cat.uvic.teknos.bank.models.Transaction {
    private int id;
    private String transactionType;
    private int amount;
    private Date transactionDate;
    private Customer customer;
    private Worker worker;

    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTransactionType() {
        return transactionType;
    }
    @Override
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public int getAmount() {
        return amount;
    }
    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public Date getTransactionDate() {
        return transactionDate;
    }
    @Override
    public void setTransactionDate(Date transactionDate) { this.transactionDate = transactionDate; }

    @Override
    public Customer getCustomer() {
        return customer;
    }
    @Override
    public void setCustomer(Customer customer) { this.customer = customer; }

    @Override
    public Worker getWorker() { return worker; }
    @Override
    public void setWorker(Worker worker) { this.worker = worker; }
}
