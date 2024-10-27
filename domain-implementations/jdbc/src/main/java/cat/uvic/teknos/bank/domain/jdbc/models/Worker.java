package cat.uvic.teknos.bank.domain.jdbc.models;

import cat.uvic.teknos.bank.models.Account;
import cat.uvic.teknos.bank.models.Transaction;

import java.util.Set;

public class Worker implements cat.uvic.teknos.bank.models.Worker{
    private int id;
    private String firstName;
    private String lastName;
    private Set<Transaction> transactions;
    private Set<Account> accounts;

    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }
    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }
    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Set<Transaction> getTransaction() { return transactions; }
    @Override
    public void setTransaction(Set <Transaction> transaction) { this.transactions = transaction; }

    @Override
    public Set<Account> getAccount() {
        return accounts;
    }
    @Override
    public void setAccount(Set <Account> account) { this.accounts = account; }
}
