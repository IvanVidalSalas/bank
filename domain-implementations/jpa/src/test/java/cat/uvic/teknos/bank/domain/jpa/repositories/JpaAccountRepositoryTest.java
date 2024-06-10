package cat.uvic.teknos.bank.domain.jpa.repositories;

import cat.uvic.teknos.bank.domain.jpa.models.Account;
import cat.uvic.teknos.bank.domain.jpa.models.Customer;
import cat.uvic.teknos.bank.domain.jpa.repositories.JpaAccountRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JpaAccountRepositoryTest {
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
    void insertAccountTest() {

        var accountRepository = new JpaAccountRepository(entityManager);

        Account account = new Account();
        account.setAccountType("Savings");
        account.setBalance(1000);

        Customer customer = entityManager.find(Customer.class, 1);
        account.setCustomer(customer);

        entityManager.getTransaction().begin();
        accountRepository.save(account);
        entityManager.getTransaction().commit();

        assertNotNull(account.getId(), "Account ID should not be null after insertion");
    }

    @Test
    void updateAccountTest() {

        var accountRepository = new JpaAccountRepository(entityManager);

        Account account = new Account();
        account.setAccountType("Checking");
        account.setBalance(2000);

        Customer customer = entityManager.find(Customer.class, 1);
        account.setCustomer(customer);

        entityManager.getTransaction().begin();
        accountRepository.save(account);
        entityManager.getTransaction().commit();

        account.setBalance(2500);

        entityManager.getTransaction().begin();
        accountRepository.save(account);
        entityManager.getTransaction().commit();

        Account updatedAccount = entityManager.find(Account.class, account.getId());
        assertEquals(account.getBalance(), updatedAccount.getBalance(), "Balance should be updated");
    }

    @Test
    void deleteAccountTest() {
        var accountRepository = new JpaAccountRepository(entityManager);

        Account account = new Account();
        account.setAccountType("Investment");
        account.setBalance(5000);

        Customer customer = entityManager.find(Customer.class, 1);
        account.setCustomer(customer);

        entityManager.getTransaction().begin();
        accountRepository.save(account);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        accountRepository.delete(account);
        entityManager.getTransaction().commit();

        assertNull(entityManager.find(Account.class, account.getId()), "Account should be deleted");
    }

    @Test
    void getAccountTest() {
        var accountRepository = new JpaAccountRepository(entityManager);

        Account account = new Account();
        account.setAccountType("Credit");
        account.setBalance(3000);

        Customer customer = entityManager.find(Customer.class, 1);
        account.setCustomer(customer);

        entityManager.getTransaction().begin();
        accountRepository.save(account);
        entityManager.getTransaction().commit();

        Account retrievedAccount = (Account) accountRepository.get(account.getId());
        assertNotNull(retrievedAccount, "Account should be retrieved");
        assertEquals(account.getId(), retrievedAccount.getId(), "Retrieved account ID should match");
    }

    @Test
    void getAllAccountsTest() {
        var accountRepository = new JpaAccountRepository(entityManager);

        Account account1 = new Account();
        account1.setAccountType("Savings");
        account1.setBalance(4000);

        Customer customer1 = entityManager.find(Customer.class, 1);
        account1.setCustomer(customer1);

        Account account2 = new Account();
        account2.setAccountType("Checking");
        account2.setBalance(6000);

        Customer customer2 = entityManager.find(Customer.class, 2);
        account2.setCustomer(customer2);

        entityManager.getTransaction().begin();
        accountRepository.save(account1);
        accountRepository.save(account2);
        entityManager.getTransaction().commit();

        Set<cat.uvic.teknos.bank.models.Account> accounts = accountRepository.getAll();
        assertFalse(accounts.isEmpty(), "The list of accounts should not be empty");
    }
}
