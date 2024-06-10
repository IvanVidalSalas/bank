package cat.uvic.teknos.bank.domain.jpa.repositories;

import cat.uvic.teknos.bank.domain.jpa.models.Customer;
import cat.uvic.teknos.bank.domain.jpa.repositories.JpaCustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JpaCustomerRepositoryTest {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    @BeforeAll
    static void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("bank-mysql");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll
    static void tearDown() {
        entityManagerFactory.close();
        entityManager.close();
    }

    @Test
    void insertCustomerTest(){

        var customerRepository = new JpaCustomerRepository(entityManager);

        Customer customer = new Customer();
        customer.setFirstName("Jack");
        customer.setLastName("Sparrow");
        customer.setAddress("Swimminginthesea 74");
        customer.setEmail("jack.sparrow@gmail.com");

        entityManager.getTransaction().begin();
        customerRepository.save(customer);
        entityManager.getTransaction().commit();

    }

    @Test
    void updateCustomerTest() {

        var customerRepository = new JpaCustomerRepository(entityManager);

        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setAddress("Doe street");
        customer.setEmail("John@example.com");

        entityManager.getTransaction().begin();
        customerRepository.save(customer);
        entityManager.getTransaction().commit();

        customer.setLastName("UpdatedDoe");

        entityManager.getTransaction().begin();
        customerRepository.save(customer);
        entityManager.getTransaction().commit();

    }

    @Test
    void deleteCustomerTest() {

        var customerRepository = new JpaCustomerRepository(entityManager);
        Customer customer = new Customer();
        customer.setFirstName("Delete");
        customer.setLastName("Me");
        customer.setAddress("To be deleted");
        customer.setEmail("delete.me@example.com");

        entityManager.getTransaction().begin();
        customerRepository.save(customer);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        customerRepository.delete(customer);
        entityManager.getTransaction().commit();

    }

    @Test
    void getCustomerTest() {

        var customerRepository = new JpaCustomerRepository(entityManager);

        Customer customer = new Customer();
        customer.setFirstName("Alice");
        customer.setLastName("Example");
        customer.setAddress("Example address");
        customer.setEmail("alice@example.com");

        entityManager.getTransaction().begin();
        customerRepository.save(customer);
        entityManager.getTransaction().commit();
    }

    @Test
    void getAllCustomersTest() {

        var customerRepository = new JpaCustomerRepository(entityManager);

        Customer customer1 = new Customer();
        customer1.setFirstName("Bob");
        customer1.setLastName("Example");
        customer1.setAddress("Example address");
        customer1.setEmail("bob@example.com");

        Customer customer2 = new Customer();
        customer2.setFirstName("Charlie");
        customer2.setLastName("Example");
        customer2.setAddress("Example address");
        customer2.setEmail("charlie@example.com");

        entityManager.getTransaction().begin();
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        entityManager.getTransaction().commit();

        Set<cat.uvic.teknos.bank.models.Customer> customers = customerRepository.getAll();
        assertFalse(customers.isEmpty(), "The list of customers should not be empty");
    }
}