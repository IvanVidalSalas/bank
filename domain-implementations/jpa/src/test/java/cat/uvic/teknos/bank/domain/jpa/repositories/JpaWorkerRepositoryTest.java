package cat.uvic.teknos.bank.domain.jpa.repositories;

import cat.uvic.teknos.bank.domain.jpa.models.Worker;
import cat.uvic.teknos.bank.domain.jpa.repositories.JpaWorkerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JpaWorkerRepositoryTest {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    @BeforeAll
    static void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("bank-mysql");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll
    static void tearDown() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void insertWorkerTest() {
        var workerRepository = new JpaWorkerRepository(entityManager);

        Worker worker = new Worker();
        worker.setFirstName("John");
        worker.setLastName("Doe");

        entityManager.getTransaction().begin();
        workerRepository.save(worker);
        entityManager.getTransaction().commit();

        assertNotNull(worker.getId(), "Worker ID should not be null after insertion");
    }

    @Test
    void updateWorkerTest() {
        var workerRepository = new JpaWorkerRepository(entityManager);

        Worker worker = new Worker();
        worker.setFirstName("Jane");
        worker.setLastName("Smith");

        entityManager.getTransaction().begin();
        workerRepository.save(worker);
        entityManager.getTransaction().commit();

        worker.setLastName("Johnson");

        entityManager.getTransaction().begin();
        workerRepository.save(worker);
        entityManager.getTransaction().commit();

        Worker updatedWorker = entityManager.find(Worker.class, worker.getId());
        assertEquals(worker.getLastName(), updatedWorker.getLastName(), "Last name should be updated");
    }

    @Test
    void deleteWorkerTest() {
        var workerRepository = new JpaWorkerRepository(entityManager);

        Worker worker = new Worker();
        worker.setFirstName("Delete");
        worker.setLastName("Me");

        entityManager.getTransaction().begin();
        workerRepository.save(worker);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        workerRepository.delete(worker);
        entityManager.getTransaction().commit();

        assertNull(entityManager.find(Worker.class, worker.getId()), "Worker should be deleted");
    }

    @Test
    void getWorkerTest() {
        var workerRepository = new JpaWorkerRepository(entityManager);

        Worker worker = new Worker();
        worker.setFirstName("Alice");
        worker.setLastName("Example");

        entityManager.getTransaction().begin();
        workerRepository.save(worker);
        entityManager.getTransaction().commit();

        Worker retrievedWorker = (Worker) workerRepository.get(worker.getId());
        assertNotNull(retrievedWorker, "Worker should be retrieved");
        assertEquals(worker.getId(), retrievedWorker.getId(), "Retrieved worker ID should match");
    }

    @Test
    void getAllWorkersTest() {
        var workerRepository = new JpaWorkerRepository(entityManager);

        Worker worker1 = new Worker();
        worker1.setFirstName("Bob");
        worker1.setLastName("Example");

        Worker worker2 = new Worker();
        worker2.setFirstName("Charlie");
        worker2.setLastName("Example");

        entityManager.getTransaction().begin();
        workerRepository.save(worker1);
        workerRepository.save(worker2);
        entityManager.getTransaction().commit();

        Set<cat.uvic.teknos.bank.models.Worker> workers = workerRepository.getAll();
        assertFalse(workers.isEmpty(), "The list of workers should not be empty");
    }
}
