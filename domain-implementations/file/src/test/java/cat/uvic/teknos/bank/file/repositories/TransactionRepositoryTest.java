package cat.uvic.teknos.bank.file.repositories;

import cat.uvic.teknos.bank.file.models.Transaction;
import cat.uvic.teknos.bank.file.models.Worker;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRepositoryTest {

    private final String testDataPath = "test-transactions.txt";

    @Test
    void save() {
        var repository = new TransactionRepository(testDataPath);
        var transaction = new Transaction();
        transaction.setId(1);
        transaction.setamount(1000);
        transaction.settransactionType("Test transaction");
        repository.save(transaction);
        assertNotNull(transaction.getId());
    }

    @Test
    void update() {
        var repository = new TransactionRepository(testDataPath);
        var transaction = new Transaction();
        transaction.setId(1);
        transaction.setamount(1000);
        transaction.settransactionType("Test transaction");
        repository.save(transaction);

        transaction.settransactionType("Updated description");
        repository.save(transaction);

        var updatedTransaction = repository.get(transaction.getId());
        assertEquals("Updated description", updatedTransaction.gettransactionType());
    }

    @Test
    void delete() {
        var repository = new TransactionRepository(testDataPath);
        var transaction = new Transaction();
        transaction.setId(1);
        transaction.setamount(1000);
        transaction.settransactionType("Test transaction");
        repository.save(transaction);
        repository.delete(transaction);
        assertNull(repository.get(transaction.getId()));
    }
    @Test
    void get() {
        var repository = new TransactionRepository(testDataPath);
        var transaction = new Transaction();
        transaction.setId(1);
        transaction.setamount(1000);
        transaction.settransactionType("Test transaction");
        repository.save(transaction);
        var retrievedTransaction = repository.get(transaction.getId());
        assertEquals(transaction, retrievedTransaction);

    }


    @Test
    void getAll() {
        var repository = new TransactionRepository(testDataPath);
        var transaction1 = new Transaction();
        transaction1.setId(1);
        transaction1.setamount(1000);
        transaction1.settransactionType("Transaction 1");
        var transaction2 = new Transaction();
        transaction2.setId(2);
        transaction2.setamount(2000);
        transaction2.settransactionType("Transaction 2");
        repository.save(transaction1);
        repository.save(transaction2);
        assertEquals(2, repository.getAll().size());
    }
}

