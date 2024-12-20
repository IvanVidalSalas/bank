package cat.uvic.teknos.bank.clients.console.dto;

import cat.uvic.teknos.bank.models.Account;
import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.models.Loan;
import cat.uvic.teknos.bank.models.Transaction;

import java.util.Set;

public class CustomerDto implements Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private Loan loan;
    private Account account;
    private Set<Transaction> transactions;


    @Override
    public int getId() { return id; }
    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public String getFirstName() { return firstName; }
    @Override
    public void setFirstName(String firstName) { this.firstName = firstName; }

    @Override
    public String getLastName() { return lastName; }
    @Override
    public void setLastName(String lastName) { this.lastName = lastName; }

    @Override
    public String getAddress() { return address; }
    @Override
    public void setAddress(String address) { this.address = address; }

    @Override
    public String getEmail() { return email; }
    @Override
    public void setEmail(String email) { this.email = email; }

    @Override
    public Loan getLoan() { return loan; }

    @Override
    public void setLoan(Loan loan) { this.loan = loan; }

    @Override
    public Account getAccount() { return account; }

    @Override
    public void setAccount(Account account) { this.account = account; }

    @Override
    public Set<Transaction> getTransaction() { return transactions; }

    @Override
    public void setTransaction(Set<Transaction> transaction) { this.transactions = transaction; }
}
