package cat.uvic.teknos.bank.domain.jdbc.repositories;

import cat.uvic.teknos.bank.domain.jdbc.models.Account;
import cat.uvic.teknos.bank.domain.jdbc.models.Customer;
import com.fcardara.dbtestutils.junit.CreateSchemaExtension;
import com.fcardara.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})
class JdbcAccountRepositoryTest {

    private final Connection connection;

    public JdbcAccountRepositoryTest(Connection connection){
        this.connection = connection;
    }

    @Test
    @DisplayName("Given a new Account, when save, then a new record is added to the CUSTOMER table")
    void insertNewAccountTest() {

        Customer customer = new Customer();
        customer.setId(1);

        Account account = new Account();
        account.setCustomer(customer);
        account.setAccountType("Savings");
        account.setBalance(500);


        var repository = new JdbcAccountRepository(connection);
        //Test
        repository.save(account);
        assertTrue(account.getId() > 0);
    }

    @Test
    @DisplayName("Given an existing Account with modified fields")
    void shouldUpdateAnAccountTest() {

        Customer customer = new Customer();
        customer.setId(1);

        Account account = new Account();
        account.setId(5);
        account.setCustomer(customer);
        account.setAccountType("Checking");
        account.setBalance(250);

        var repository = new JdbcAccountRepository(connection);
        repository.save(account);
        assertTrue(account.getId() > 0);
    }


    @Test
    void delete() {

        Account account = new Account();
        account.setId(5);

        var repository = new JdbcAccountRepository(connection);
        repository.delete(account);

        assertNull(repository.get(5));
    }

    @Test
    void get() {

        int id = 4;
        var repository = new JdbcAccountRepository(connection);

        cat.uvic.teknos.bank.models.Account account = repository.get(id);
        SoutAccount(account);
    }

    @Test
    void getAll() {
        var repository = new JdbcAccountRepository(connection);
        Set<cat.uvic.teknos.bank.models.Account> accounts = repository.getAll();
        for(var account:accounts){
            SoutAccount(account);
        }
    }

    private void SoutAccount(cat.uvic.teknos.bank.models.Account account){
        System.out.println("Account Id: " + account.getId());
        System.out.println("Customer Id: " + account.getCustomer().getId());
        System.out.println("Account type: " + account.getAccountType());
        System.out.println("Balance: " + account.getBalance());

        System.out.println("\n");
    }
}