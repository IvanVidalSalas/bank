package cat.uvic.teknos.bank.domain.jdbc.models;

import cat.uvic.teknos.bank.models.Account;
import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.models.Loan;
import cat.uvic.teknos.bank.models.ModelFactory;
import cat.uvic.teknos.bank.models.Transaction;
import cat.uvic.teknos.bank.models.Worker;

public class JdbcModelFactory implements ModelFactory {

    @Override
    public Customer createCustomer() {
        return new cat.uvic.teknos.bank.domain.jdbc.models.Customer();
    }

    @Override
    public Account createAccount() {
        return new cat.uvic.teknos.bank.domain.jdbc.models.Account();
    }

    @Override
    public Loan createLoan() {
        return new cat.uvic.teknos.bank.domain.jdbc.models.Loan();
    }

    @Override
    public Transaction createTransaction() {
        return new cat.uvic.teknos.bank.domain.jdbc.models.Transaction();
    }

    @Override
    public Worker createWorker() {
        return new cat.uvic.teknos.bank.domain.jdbc.models.Worker();
    }
}
