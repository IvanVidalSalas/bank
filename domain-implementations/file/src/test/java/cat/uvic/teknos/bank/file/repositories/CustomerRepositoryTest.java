package cat.uvic.teknos.bank.file.repositories;
import cat.uvic.teknos.bank.file.models.Customer;
import org.junit.jupiter.api.Test;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRepositoryTest {

    private final String testDataPath = "test-customers.txt";


    @Test
    void save() {
        var repository = new CustomerRepository(testDataPath);
        var customer = new Customer();
        customer.setId(1);
        customer.setFirstName("Xevi");
        customer.setLastName("Fernandez");
        customer.setAddress("Carrer Major");
        customer.setEmail("xevifernandez@example.com");
        repository.save(customer);
        assertTrue(customer.getId() > 0);
    }

    @Test
    void update() {
        var repository = new CustomerRepository(testDataPath);
        var customer = new Customer();
        customer.setId(1);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setAddress("123 Main St");
        customer.setEmail("johndoe@example.com");
        repository.save(customer);
        var updatedCustomer = repository.get(customer.getId());
        updatedCustomer.setFirstName("Updated Name");
        repository.save(updatedCustomer);
        var retrievedCustomer = repository.get(customer.getId());
        assertEquals("Updated Name", retrievedCustomer.getFirstName());
    }

    @Test
    void delete() {
        var repository = new CustomerRepository(testDataPath);
        var customer = new Customer();
        customer.setId(1);
        customer.setFirstName("Jane");
        customer.setLastName("Kennedy");
        customer.setAddress("456 Elm St");
        customer.setEmail("janekennedy@example.com");
        repository.save(customer);
        repository.delete(customer);
        assertNull(repository.get(customer.getId()));
    }

    @Test
    void get() {
        var repository = new CustomerRepository(testDataPath);
        var customer = new Customer();
        customer.setId(1);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setAddress("123 Main St");
        customer.setEmail("john@example.com");
        repository.save(customer);
        var retrievedCustomer = repository.get(customer.getId());
        assertEquals(customer, retrievedCustomer);
    }

    @Test
    void getAll() {
        var repository = new CustomerRepository(testDataPath);
        var customer1 = new Customer();
        customer1.setId(1);
        customer1.setFirstName("John");
        customer1.setLastName("Doe");
        customer1.setAddress("123 Main St");
        customer1.setEmail("john@example.com");

        var customer2 = new Customer();
        customer2.setId(2);
        customer2.setFirstName("Jane");
        customer2.setLastName("Doe");
        customer2.setAddress("456 Elm St");
        customer2.setEmail("jane@example.com");

        repository.save(customer1);
        repository.save(customer2);

    }
}