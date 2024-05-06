package cat.uvic.teknos.bank.domain.jpa.models;

import cat.uvic.teknos.bank.models.Account;
import cat.uvic.teknos.bank.models.Transaction;

import java.util.Set;

public class Worker implements cat.uvic.teknos.bank.models.Worker{
    private int id;
    private Set<Transaction> transaction;
    private Set<Account> account;
    private String firstName;
    private String lastName;

    @Override
    public int getId() { return id; }
    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public Set<Transaction> getTransaction() { return transaction; }
    @Override
    public void setTransaction(Set<Transaction> transaction) { this.transaction = transaction; }

    @Override
    public Set<Account> getAccount() { return account; }
    @Override
    public void setAccount(Set<Account> account) { this.account = account; }

    @Override
    public String getFirstName() { return firstName; }
    @Override
    public void setFirstName(String firstName) { this.firstName = firstName; }

    @Override
    public String getLastName() { return lastName; }
    @Override
    public void setLastName(String lastName) { this.lastName = lastName; }
}
