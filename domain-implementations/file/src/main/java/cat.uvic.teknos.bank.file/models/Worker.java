package cat.uvic.teknos.bank.file.models;

import cat.uvic.teknos.bank.models.Account;
import cat.uvic.teknos.bank.models.Transaction;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Worker implements cat.uvic.teknos.bank.models.Worker, Serializable {
    private int id;
    private Set<Transaction> transactions;
    private Set<Account> accounts;
    private String firstName;
    private String lastName;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Set<Transaction> getTransaction() {
        return transactions;
    }

    @Override
    public void setTransaction(Transaction transaction) {
        if (transactions == null) {
            transactions = new HashSet<>();
        }
        transactions.add(transaction);
    }

    @Override
    public Set<Account> getAccount() {
        return accounts;
    }

    @Override
    public void setAccount(Account account) {
        if (accounts == null) {
            accounts = new HashSet<>();
        }
        accounts.add(account);
    }

    @Override
    public String getfirstName() {
        return firstName;
    }

    @Override
    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getlastName() {
        return lastName;
    }

    @Override
    public void setlastName(String lastName) {
        this.lastName = lastName;
    }
}
