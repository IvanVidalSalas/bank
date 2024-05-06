package cat.uvic.teknos.bank.domain.jpa.models;

import cat.uvic.teknos.bank.models.Account;
import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.models.Loan;
import cat.uvic.teknos.bank.models.Transaction;
import cat.uvic.teknos.bank.models.Worker;
import cat.uvic.teknos.bank.models.*;

public class JpaModelFactory implements ModelFactory {

    @Override
    public Customer createCustomer() {
        return new cat.uvic.teknos.bank.domain.jpa.models.Customer();
    }

    @Override
    public Account createAccount() {
        return new cat.uvic.teknos.bank.domain.jpa.models.Account();
    }

    @Override
    public Loan createLoan() {
        return new cat.uvic.teknos.bank.domain.jpa.models.Loan();
    }

    @Override
    public Transaction createTransaction() {
        return new cat.uvic.teknos.bank.domain.jpa.models.Transaction();
    }

    @Override
    public Worker createWorker() {
        return new cat.uvic.teknos.bank.domain.jpa.models.Worker();
    }
}
