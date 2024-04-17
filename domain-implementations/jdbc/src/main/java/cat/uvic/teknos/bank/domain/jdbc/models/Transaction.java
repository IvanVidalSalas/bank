package cat.uvic.teknos.bank.domain.jdbc.models;

import java.time.LocalDate;
import java.util.Set;

public class Transaction implements cat.uvic.teknos.bank.models.Transaction{
    private int id;
    private int customerid;
    private Set<Customer> customer;
    private String transactionType;
    private int amount;
    private LocalDate transactionDate;

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
    public void setCustomer(Set<Customer> customer) { this.customer = customer; }

    @Override
    public String getTransactionType() { return transactionType; }
    @Override
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    @Override
    public int getAmount() { return amount; }
    @Override
    public void setAmount(int amount) { this.amount = amount; }

    @Override
    public LocalDate getTransactionDate() { return transactionDate; }
    @Override
    public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }
}
