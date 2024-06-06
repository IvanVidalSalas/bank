package cat.uvic.teknos.bank.domain.jpa;

import cat.uvic.teknos.bank.domain.jpa.models.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class JpaCustomerRepositoryTest {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;


    @BeforeAll
    static void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("bank-mysql");
    }

    @AfterAll
    static void tearDown() {
        entityManagerFactory.close();
    }


    @Test
    void insertTest(){
        //EntityManager: manages transactions, entities, cache
        var entityManager = entityManagerFactory.createEntityManager();

        try {
        entityManager.getTransaction().begin();

        var customer = new Customer();
        customer.setFirstName("Jack");
        customer.setLastName("Sparrow");
        customer.setAddress("Swimminginthesea 74");
        customer.setEmail("jack.sparrow@gmail.com");
        entityManager.persist(customer);

        assertTrue(customer.getId() > 0);

        entityManager.getTransaction().commit();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
        }

    }
}