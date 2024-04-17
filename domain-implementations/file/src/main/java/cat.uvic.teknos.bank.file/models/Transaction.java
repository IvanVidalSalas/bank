package cat.uvic.teknos.bank.file.models;

import cat.uvic.teknos.bank.models.Customer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Transaction implements cat.uvic.teknos.bank.models.Transaction {
    private int id;
    private Set<Customer> customers;
    private String transactionType;
    private int amount;
    private LocalDate transactionDate;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
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
    public String gettransactionType() {
        return transactionType;
    }

    @Override
    public void settransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public int getacomunt() {
        return amount;
    }

    @Override
    public void setamount(int amount) {
        this.amount = amount;
    }

    @Override
    public String gettransactionDate() {
        return transactionDate.toString();
    }

    @Override
    public void settransactionDate(String transactionDate) {
        this.transactionDate = LocalDate.parse(transactionDate);
    }
}
