package cat.uvic.teknos.bank.file.repositories;

import cat.uvic.teknos.bank.file.models.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryTest {

    private final String testDataPath = "test-account.txt";

    @Test
    void save() {
        var repository = new AccountRepository(testDataPath);
        var account = new Account(1);
        account.setId(1);
        account.setCustomerId(1);
        account.setaccountType("Savings");
        account.setbalance(1000);
        repository.save(account);
        assertNotNull(account.getId());
    }

    @Test
    void update() {
        var repository = new AccountRepository(testDataPath);
        var account = new Account(1);
        account.setCustomerId(1);
        account.setaccountType("Savings");
        account.setbalance(1000);
        repository.save(account);

        account.setaccountType("Checking");
        account.setbalance(1500);
        repository.save(account);

        var updatedAccount = repository.get(account.getId());
        assertEquals("Checking", updatedAccount.getaccountType());
        assertEquals(1500, updatedAccount.getBalance());
    }

    @Test
    void delete() {
        var repository = new AccountRepository(testDataPath);
        var account = new Account(1);
        account.setCustomerId(1);
        account.setaccountType("Savings");
        account.setbalance(1000);
        repository.save(account);
        repository.delete(account);
        assertNull(repository.get(account.getId()));
    }

    @Test
    void get() {
        var repository = new AccountRepository(testDataPath);
        var account = new Account(1);
        account.setCustomerId(1);
        account.setaccountType("Savings");
        account.setbalance(1000);
        repository.save(account);
        var retrievedAccount = repository.get(account.getId());
        assertEquals(account, retrievedAccount);
    }

    @Test
    void getAll() {
        var repository = new AccountRepository(testDataPath);
        var account1 = new Account(1);
        account1.setCustomerId(1);
        account1.setaccountType("Savings");
        account1.setbalance(1000);
        var account2 = new Account(2);
        account2.setCustomerId(2);
        account2.setaccountType("Checking");
        account2.setbalance(500);
        repository.save(account1);
        repository.save(account2);
        assertEquals(2, repository.getAll().size());
    }
}
