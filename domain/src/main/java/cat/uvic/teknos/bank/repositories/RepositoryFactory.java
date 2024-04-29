package cat.uvic.teknos.bank.repositories;

public interface RepositoryFactory {
    CustomerRepository getCustomerRepository();
    AccountRepository getAccountRepository();
    LoanRepository getLoanRepository();
    TransactionRepository getTransactionRepository();
    WorkerRepository getWorkerRepository();
}
