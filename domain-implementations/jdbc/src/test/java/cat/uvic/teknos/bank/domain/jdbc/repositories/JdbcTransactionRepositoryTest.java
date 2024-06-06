package cat.uvic.teknos.bank.domain.jdbc.repositories;

import cat.uvic.teknos.bank.domain.jdbc.models.Transaction;
import cat.uvic.teknos.bank.domain.jdbc.models.Customer;
import cat.uvic.teknos.bank.domain.jdbc.models.Worker;
import com.fcardara.dbtestutils.junit.CreateSchemaExtension;
import com.fcardara.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})
class JdbcTransactionRepositoryTest {

    private final Connection connection;

    public JdbcTransactionRepositoryTest(Connection connection) {
        this.connection = connection;
    }

    @Test
    @DisplayName("Given a new transaction, when save, then a new record is added to the TRANSACTION table")
    void insertNewTransactionTest() {
        Transaction transaction = new Transaction();
        Customer customer = new Customer();
        customer.setId(1);
        transaction.setCustomer(customer);
        transaction.setTransactionType("Deposit");
        transaction.setAmount(100);
        transaction.setTransactionDate(new Date(System.currentTimeMillis()));

        var repository = new JdbcTransactionRepository(connection);

        // Test
        repository.save(transaction);
        assertTrue(transaction.getId() > 0);
    }

    @Test
    @DisplayName("Given an existing transaction with modified fields")
    void shouldUpdateATransactionTest() {
        Transaction transaction = new Transaction();
        transaction.setTransactionType("Withdraw");
        transaction.setAmount(200);
        transaction.setTransactionDate(new Date(System.currentTimeMillis()));

        Customer customer = new Customer();
        customer.setId(1);
        transaction.setCustomer(customer);

        Worker worker = new Worker();
        worker.setId(1);
        transaction.setWorker(worker);

        var repository = new JdbcTransactionRepository(connection);
        repository.save(transaction);
        assertTrue(transaction.getId() > 0);

        // Update transaction
        transaction.setAmount(200);
        repository.save(transaction);

    }

    @Test
    @DisplayName("Given a transaction, when delete, then the record is removed from the TRANSACTION table")
    void delete() {
        Transaction transaction = new Transaction();
        transaction.setId(1);

        var repository = new JdbcTransactionRepository(connection);
        repository.delete(transaction);

        assertNull(repository.get(1));
    }



    @Test
    void get() {
        int id = 9;
        var repository = new JdbcTransactionRepository(connection);

        cat.uvic.teknos.bank.models.Transaction transaction = repository.get(id);
        SoutTransaction(transaction);
    }

    @Test
    void getAll() {
        var repository = new JdbcTransactionRepository(connection);
        Set<cat.uvic.teknos.bank.models.Transaction> transactions = repository.getAll();

        for(var transaction:transactions){
            SoutTransaction(transaction);
        }
    }


    private void SoutTransaction(cat.uvic.teknos.bank.models.Transaction transaction){
        System.out.println("Transaction Id: " + transaction.getId());
        System.out.println("Customer Id: " + transaction.getCustomer().getId());
        System.out.println("Transaction type: " + transaction.getTransactionType());
        System.out.println("Transaction amount: " + transaction.getAmount());
        System.out.println("Transaction Date: " + transaction.getTransactionDate());

        System.out.println("\n");
    }
}
