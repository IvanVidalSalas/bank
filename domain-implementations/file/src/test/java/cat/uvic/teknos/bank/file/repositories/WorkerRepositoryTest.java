package cat.uvic.teknos.bank.file.repositories;

import cat.uvic.teknos.bank.file.models.Worker;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WorkerRepositoryTest {

    private final String testDataPath = "test-workers.txt";

    @Test
    void save() {
        var repository = new WorkerRepository(testDataPath);
        var worker = new Worker();
        worker.setId(1);
        worker.setFirstName("John");
        worker.setLastName("Doe");
        repository.save(worker);
        assertTrue(worker.getId() > 0);
    }

    @Test
    void update() {
        var repository = new WorkerRepository(testDataPath);
        var worker = new Worker();
        worker.setId(1);
        worker.setFirstName("John");
        worker.setLastName("Doe");
        repository.save(worker);
        var updatedWorker = repository.get(worker.getId());
        updatedWorker.setFirstName("Updated Name");
        repository.save(updatedWorker);
        var retrievedWorker = repository.get(worker.getId());
        assertEquals("Updated Name", retrievedWorker.getFirstName());
    }

    @Test
    void delete() {
        var repository = new WorkerRepository(testDataPath);
        var worker = new Worker();
        worker.setId(1);
        worker.setFirstName("John");
        worker.setLastName("Doe");
        repository.save(worker);
        repository.delete(worker);
        assertNull(repository.get(worker.getId()));
    }

    @Test
    void get() {
        var repository = new WorkerRepository(testDataPath);
        var worker = new Worker();
        worker.setId(1);
        worker.setFirstName("John");
        worker.setLastName("Doe");
        repository.save(worker);
        var retrievedWorker = repository.get(worker.getId());
        assertEquals(worker, retrievedWorker);
    }

    @Test
    void getAll() {
        var repository = new WorkerRepository(testDataPath);
        var worker1 = new Worker();
        worker1.setId(1);
        worker1.setFirstName("John");
        worker1.setLastName("Doe");

        var worker2 = new Worker();
        worker2.setId(2);
        worker2.setFirstName("Jane");
        worker2.setLastName("Doe");

        repository.save(worker1);
        repository.save(worker2);

        Set<cat.uvic.teknos.bank.models.Worker> allWorkers = repository.getAll();

        assertEquals(2, allWorkers.size());
        assertTrue(allWorkers.contains(worker1));
        assertTrue(allWorkers.contains(worker2));
    }
}
