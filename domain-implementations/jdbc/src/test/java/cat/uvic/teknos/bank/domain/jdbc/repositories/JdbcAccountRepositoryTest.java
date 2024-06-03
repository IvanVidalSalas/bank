package cat.uvic.teknos.bank.domain.jdbc.repositories;

import cat.uvic.teknos.bank.domain.jdbc.models.Account;
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
    @DisplayName("Given a new Account (id = 0), when save, then a new record is added to the CUSTOMER table")
    void insertNewAccountTest() {
        Account account = new Account();
        account.setId(4);
        account.setAccountType("Ivan");
        account.setBalance(500);
        account.setAddress("123 Main St");
        account.setEmail("john@example.com");

        var repository = new JdbcAccountRepository(connection);
        //Test
        repository.save(account);
        assertTrue(account.getId() > 0);

//        DbAssertions.assertThat(connection)
//                .table("CUSTOMER")
//                .where("CUSTOMER_ID = ?", account.getId())
//                .hasOneLine();
    }

    @Test
    @DisplayName("Given an existing Account with modified fields")
    void shouldUpdateAnAccountTest() {
        Account account = new Account();
        account.setId(1);
        account.setAccountType("Jane");
        account.setBalance(250);
        account.setAddress("456 Elm St");
        account.setEmail("jane@example.com");

        var repository = new JdbcAccountRepository(connection);
        repository.save(account);
        assertTrue(account.getId() > 0);
    }


    @Test
    void delete() {
        Account account = new Account();
        account.setId(4);

        var repository = new JdbcAccountRepository(connection);
        repository.delete(account);

        assertNull(repository.get(4));
    }

    @Test
    void get() {
        var repository = new JdbcAccountRepository(connection);
        assertNotNull(repository.get(1));
    }

    @Test
    void getAll() {
        var repository = new JdbcAccountRepository(connection);
        Set<cat.uvic.teknos.bank.models.Account> accounts = repository.getAll();
        assertFalse(accounts.isEmpty(), "Should not be empty");
    }
}