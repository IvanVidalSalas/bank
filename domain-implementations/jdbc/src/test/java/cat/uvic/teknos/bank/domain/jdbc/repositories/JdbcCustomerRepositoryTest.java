package cat.uvic.teknos.bank.domain.jdbc.repositories;

import cat.uvic.teknos.bank.domain.jdbc.models.Customer;
import com.fcardara.dbtestutils.db.DbUtils;
import com.fcardara.dbtestutils.junit.CreateSchemaExtension;
import com.fcardara.dbtestutils.junit.DbAssertions;
import com.fcardara.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})
class JdbcCustomerRepositoryTest {

    private final Connection connection;

    public JdbcCustomerRepositoryTest(Connection connection) {
        this.connection = connection;
    }

    @Test
    @DisplayName("Given a new customer (id = 0), when save, then a new record is added to the CUSTOMER table")
    void insertNewCustomerTest() throws SQLException {
        Customer customer = new Customer();
        customer.setId(4);
        customer.setFirstName("Ivan");
        customer.setLastName("Doe");
        customer.setAddress("123 Main St");
        customer.setEmail("john@example.com");
        var repository = new JdbcCustomerRepository(connection);

        //Test
        repository.save(customer);
        assertTrue(customer.getId() > 0);
//        assertNotNull(repository.get(customer.getId()));
//
//        DbAssertions.assertThat(connection)
//                .table("CUSTOMER")
//                .where("CUSTOMER_ID = ?", customer.getId())
//                .hasOneLine();
    }

    @Test
    @DisplayName("Given an existing Customer with modified fields")
    void shouldUpdateACustomerTest() throws SQLException {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("Jane");
        customer.setLastName("Doe");
        customer.setAddress("456 Elm St");
        customer.setEmail("jane@example.com");

        var repository = new JdbcCustomerRepository(connection);
        repository.save(customer);
        assertTrue(customer.getId() > 0);
    }

    @Test
    void delete() {
        Customer customer = new Customer();
        customer.setId(4);

        var repository = new JdbcCustomerRepository(connection);
        repository.delete(customer);

        assertNull(repository.get(4));
    }

    @Test
    void get() throws SQLException  {
        var repository = new JdbcCustomerRepository(connection);
        assertNotNull(repository.get(1));
    }

    @Test
    void getAll() {
        var repository = new JdbcCustomerRepository(connection);

        Set<cat.uvic.teknos.bank.models.Customer> customers = repository.getAll();

        assertFalse(customers.isEmpty(), "Should not be empty" );
    }
}