package cat.uvic.teknos.bank.domain.jdbc.models;

import cat.uvic.teknos.bank.models.Account;
import cat.uvic.teknos.bank.models.Transaction;

import java.util.Set;

public class Worker implements cat.uvic.teknos.bank.models.Worker{
    private int id;
    private int transactionid;
    private Set<cat.uvic.teknos.bank.models.Transaction> transaction;
    private int accountid;
    private Set<cat.uvic.teknos.bank.models.Account> account;
    private String firstname;
    private String lastname;

    @Override
    public int getId() { return id; }
    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public int getTransactionId() { return transactionid; }
    @Override
    public void setTransactionId(int transactionidid) { this.transactionid = transactionid; }
    @Override
    public Set<cat.uvic.teknos.bank.models.Transaction> getTransaction() { return transaction; }
    @Override
    public void setTransaction(Set<Transaction> transaction) { this.transaction = transaction; }

    @Override
    public int getAccountid() { return accountid; }
    @Override
    public void setAccountid(int accountid) { this.accountid = accountid; }
    @Override
    public Set<cat.uvic.teknos.bank.models.Account> getAccount() { return account; }
    @Override
    public void setAccount(Set<Account> account) { this.account = account; }

    @Override
    public String getFirstname() { return firstname; }
    @Override
    public void setFirstname(String firstname) { this.firstname = firstname; }

    @Override
    public String getLastname() { return lastname; }
    @Override
    public void setLastname(String lastname) { this.lastname = lastname; }
}
