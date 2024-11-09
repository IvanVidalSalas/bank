package cat.uvic.teknos.bank.clients.console.dto;

import cat.uvic.teknos.bank.models.*;

public class DtoModelFactory implements ModelFactory {

    @Override
    public Customer createCustomer() { return new CustomerDto(); }

    @Override
    public Account createAccount() { return new AccountDto(); }

    @Override
    public Loan createLoan() { return new LoanDto(); }

    @Override
    public Transaction createTransaction() { return new TransactionDto(); }

    @Override
    public Worker createWorker() { return new WorkerDto(); }
}
