package cat.uvic.teknos.bank.models;

public interface ModelFactory {
    Customer createCustomer();
    Account createAccount();
    Loan createLoan();
    Transaction createTransaction();
    Worker createWorker();
}
