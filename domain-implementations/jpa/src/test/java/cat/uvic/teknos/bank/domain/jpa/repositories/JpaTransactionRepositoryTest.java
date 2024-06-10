package cat.uvic.teknos.bank.domain.jpa.repositories;

import cat.uvic.teknos.bank.domain.jpa.models.Customer;
import cat.uvic.teknos.bank.domain.jpa.models.Transaction;
import cat.uvic.teknos.bank.domain.jpa.repositories.JpaTransactionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JpaTransactionRepositoryTest {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    @BeforeAll
    static void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("bank-mysql");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll
    static void tearDown() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void insertTransactionTest() {
        var transactionRepository = new JpaTransactionRepository(entityManager);

        Transaction transaction = new Transaction();
        transaction.setTransactionType("Deposit");
        transaction.setAmount(100);
        transaction.setTransactionDate(new Date(System.currentTimeMillis()));

        Customer customer = entityManager.find(Customer.class, 1);
        transaction.setCustomer(customer);

        entityManager.getTransaction().begin();
        transactionRepository.save(transaction);
        entityManager.getTransaction().commit();

        assertNotNull(transaction.getId(), "Transaction ID should not be null after insertion");
    }

    @Test
    void updateTransactionTest() {
        var transactionRepository = new JpaTransactionRepository(entityManager);

        Transaction transaction = new Transaction();
        transaction.setTransactionType("Withdrawal");
        transaction.setAmount(50);
        transaction.setTransactionDate(new Date(System.currentTimeMillis()));

        Customer customer = entityManager.find(Customer.class, 1);
        transaction.setCustomer(customer);

        entityManager.getTransaction().begin();
        transactionRepository.save(transaction);
        entityManager.getTransaction().commit();

        Transaction updatedTransaction = entityManager.find(Transaction.class, transaction.getId());
        assertEquals(transaction.getAmount(), updatedTransaction.getAmount(), "Amount should be updated");
    }

    @Test
    void deleteTransactionTest() {
        var transactionRepository = new JpaTransactionRepository(entityManager);

        Transaction transaction = new Transaction();
        transaction.setTransactionType("Transfer");
        transaction.setAmount(200);
        transaction.setTransactionDate(new Date(System.currentTimeMillis()));

        Customer customer = entityManager.find(Customer.class, 1);
        transaction.setCustomer(customer);

        entityManager.getTransaction().begin();
        transactionRepository.save(transaction);
        entityManager.getTransaction().commit();

        assertNull(entityManager.find(Transaction.class, transaction.getId()), "Transaction should be deleted");
    }

    @Test
    void getTransactionTest() {
        var transactionRepository = new JpaTransactionRepository(entityManager);

        Transaction transaction = new Transaction();
        transaction.setTransactionType("Withdrawal");
        transaction.setAmount(100);
        transaction.setTransactionDate(new Date(System.currentTimeMillis()));

        Customer customer = entityManager.find(Customer.class, 1);
        transaction.setCustomer(customer);

        entityManager.getTransaction().begin();
        transactionRepository.save(transaction);
        entityManager.getTransaction().commit();

        Transaction retrievedTransaction = (Transaction) transactionRepository.get(transaction.getId());
        assertNotNull(retrievedTransaction, "Transaction should be retrieved");
        assertEquals(transaction.getId(), retrievedTransaction.getId(), "Retrieved transaction ID should match");
    }

    @Test
    void getAllTransactionsTest() {
        var transactionRepository = new JpaTransactionRepository(entityManager);

        Transaction transaction1 = new Transaction();
        transaction1.setTransactionType("Deposit");
        transaction1.setAmount(500);
        transaction1.setTransactionDate(new Date(System.currentTimeMillis()));

        Customer customer1 = entityManager.find(Customer.class, 1);
        transaction1.setCustomer(customer1);

        Transaction transaction2 = new Transaction();
        transaction2.setTransactionType("Withdrawal");
        transaction2.setAmount(300);
        transaction2.setTransactionDate(new Date(System.currentTimeMillis()));

        Customer customer2 = entityManager.find(Customer.class, 2);
        transaction2.setCustomer(customer2);

        entityManager.getTransaction().begin();
        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);
        entityManager.getTransaction().commit();

        Set<cat.uvic.teknos.bank.models.Transaction> transactions = transactionRepository.getAll();
        assertFalse(transactions.isEmpty(), "The list of transactions should not be empty");
    }
}
