package cat.uvic.teknos.bank.domain.jdbc.repositories;

import cat.uvic.teknos.bank.domain.jdbc.models.Customer;
import com.fcardara.dbtestutils.junit.CreateSchemaExtension;
import com.fcardara.dbtestutils.junit.DbAssertions;
import com.fcardara.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})
class JdbcCustomerRepositoryTest {

    private final Connection connection;

    public JdbcCustomerRepositoryTest(Connection connection) {
        this.connection = connection;
    }

    @Test
    @DisplayName("Given a new cutomer (id = 0), when save, then a new record is added to the CUSTOMER table")
    void insertNewCustomerTest() throws SQLException {
        Customer customer = new Customer();
        customer.setId(1);
        var repository = new JdbcCustomerRepository(connection);

        //Test
        repository.save(customer);
        assertTrue(customer.getId() > 0);
        assertNotNull(repository.get(customer.getId()));

        DbAssertions.assertThat(connection)
                .table("CUSTOMER")
                .where("ID = ?", customer.getId())
                .hasOneLine();
    }

    @Test
    @DisplayName("Given an existing Customer with modified fields")
    void shouldUpdateACustomerTest() throws SQLException {
            Customer customer = new Customer();
            customer.setId(1);
            var repository = new JdbcCustomerRepository(connection);
            repository.save(customer);
            assertTrue(customer.getId() > 0);
    }

    @Test
    void delete() {
        Customer customer = new Customer();
        customer.setId(1);
        var repository = new JdbcCustomerRepository(connection);
        repository.delete(customer);

        //assertNull(repository.get(1));

        DbAssertions.assertThat(connection)
                .table("CUSTOMER")
                .where("ID = ?", customer.getId())
                .doesNotExist();
    }

    @Test
    void get() throws SQLException  {
        var repository = new JdbcCustomerRepository(connection);
        assertNotNull(repository.get(1));
    }

    @Test
    void getAll() {

    }

    @Test
    void getByName() {

    }
}