package cat.uvic.teknos.bank.domain.jpa.repositories;

import cat.uvic.teknos.bank.repositories.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaRepositoryFactory implements RepositoryFactory {

    private final EntityManager entityManager;

    public JpaRepositoryFactory() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bank-mysql");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public CustomerRepository getCustomerRepository() {
        return new JpaCustomerRepository(entityManager);
    }

    @Override
    public AccountRepository getAccountRepository() {
        return new JpaAccountRepository(entityManager);
    }

    @Override
    public LoanRepository getLoanRepository() { return new JpaLoanRepository(entityManager); }

    @Override
    public TransactionRepository getTransactionRepository() { return new JpaTransactionRepository(entityManager); }

    @Override
    public WorkerRepository getWorkerRepository() { return new JpaWorkerRepository(entityManager); }
}
