package cat.uvic.teknos.bank.domain.jdbc.repositories;

import cat.uvic.teknos.bank.domain.jdbc.models.Transaction;
import cat.uvic.teknos.bank.domain.jdbc.models.Worker;
import com.fcardara.dbtestutils.junit.CreateSchemaExtension;
import com.fcardara.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})
class JdbcWorkerRepositoryTest {

    private final Connection connection;

    public JdbcWorkerRepositoryTest(Connection connection) {
        this.connection = connection;
    }

    @Test
    @DisplayName("Given a new worker, when save, then a new record is added to the WORKER table")
    void insertNewWorkerTest() {

        Worker worker = new Worker();
        worker.setFirstName("Steve");
        worker.setLastName("Terry");

        var repository = new JdbcWorkerRepository(connection);

        repository.save(worker);
        assertTrue(worker.getId() > 0);
    }

    @Test
    @DisplayName("Given an existing worker with modified fields")
    void shouldUpdateAWorkerTest() {

        Worker worker = new Worker();
        worker.setId(7);
        worker.setFirstName("Xevi");
        worker.setLastName("Jove");

        var repository = new JdbcWorkerRepository(connection);
        repository.save(worker);
        assertTrue(worker.getId() > 0);

    }

    @Test
    @DisplayName("Given a worker, when delete, then the record is removed from the WORKER table")
    void delete() {
        Worker worker = new Worker();
        worker.setId(7);

        var repository = new JdbcWorkerRepository(connection);
        repository.delete(worker);

        assertNull(repository.get(1));
    }

    @Test
    void get() {
        int id = 6 ;
        var repository = new JdbcWorkerRepository(connection);

        cat.uvic.teknos.bank.models.Worker worker = repository.get(id);
        SoutWorker(worker);
    }

    @Test
    void getAll() {
        var repository = new JdbcWorkerRepository(connection);
        Set<cat.uvic.teknos.bank.models.Worker> workers = repository.getAll();

        for(var worker:workers){
            SoutWorker(worker);
        }
    }

    private void SoutWorker(cat.uvic.teknos.bank.models.Worker worker){
        System.out.println("Worker Id: " + worker.getId());
        System.out.println("Transaction Id: " + worker.getTransaction().getClass());
        System.out.println("First Name: " + worker.getFirstName());
        System.out.println("Last Name: " + worker.getLastName());

        System.out.println("\n");
    }
}
