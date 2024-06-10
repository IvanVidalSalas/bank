package cat.uvic.teknos.bank.domain.jdbc.repositories;

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
class JdbcCustomerRepositoryTest {

    private final Connection connection;

    public JdbcCustomerRepositoryTest(Connection connection) {
        this.connection = connection;
    }

    @Test
    @DisplayName("Given a new customer, when save, then a new record is added to the CUSTOMER table")
    void insertNewCustomerTest() {

        Customer customer = new Customer();
        customer.setFirstName("Ivan");
        customer.setLastName("Vidal");
        customer.setAddress("123 Main St");
        customer.setEmail("ivan@example.com");

        var repository = new JdbcCustomerRepository(connection);
        repository.save(customer);
        assertTrue(customer.getId() > 0);
    }

    @Test
    @DisplayName("Given an existing Customer with modified fields")
    void shouldUpdateACustomerTest() {

        Customer customer = new Customer();
        customer.setId(5);
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
        repository.save(customer);
        repository.delete(customer);

        assertNull(repository.get(customer.getId()));
    }

    @Test
    void get() {

        int id = 1;
        var repository = new JdbcCustomerRepository(connection);

        cat.uvic.teknos.bank.models.Customer customer = repository.get(id);
        SoutCustomer(customer);
    }

    @Test
    void getAll() {
        var repository = new JdbcCustomerRepository(connection);
        Set<cat.uvic.teknos.bank.models.Customer> customers = repository.getAll();

        for(var customer:customers){
            SoutCustomer(customer);
        }
    }

    private void SoutCustomer(cat.uvic.teknos.bank.models.Customer customer){

        System.out.println("Customer Id: " + customer.getId());
        System.out.println("First Name: " + customer.getFirstName());
        System.out.println("Last Name: " + customer.getLastName());
        System.out.println("Address: " + customer.getAddress());
        System.out.println("Email: " + customer.getEmail());

        System.out.println("\n");
    }


}