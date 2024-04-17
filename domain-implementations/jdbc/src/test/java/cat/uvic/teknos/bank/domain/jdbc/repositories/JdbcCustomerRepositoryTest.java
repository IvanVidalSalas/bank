package cat.uvic.teknos.bank.domain.jdbc.repositories;

import cat.uvic.teknos.bank.domain.jdbc.models.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JdbcCustomerRepositoryTest {

    @Test
    @DisplayName("Given... when... then")
    void insertNewCustomerTest() throws SQLException {
        try(var connection = DriverManager.getConnection("jdbc:mysql://localhost/bank", "root", null)){

        Customer customer = new Customer();
        customer.setId("");
        var repository = new JdbcCustomerRepository();
        repository.save();
        assertTrue(customer.getId()) > 0;
        }
    }

    @Test
    @DisplayName("Given an existing Customer with modified fields")
    void shouldUpdateACustomerTest() throws SQLException {
        try(var connection = DriverManager.getConnection("jdbc:mysql://localhost/bank", "root", null)){

            Customer customer = new Customer();
            customer.setId("");
            var repository = new JdbcCustomerRepository();
            repository.save();
            assertTrue(customer.getId()) > 0;
        }
    }

    @Test
    void delete() {
    }

    @Test
    void get() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getByName() {
    }
}