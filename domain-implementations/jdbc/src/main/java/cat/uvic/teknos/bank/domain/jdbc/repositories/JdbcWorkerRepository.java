package cat.uvic.teknos.bank.domain.jdbc.repositories;

import cat.uvic.teknos.bank.models.Worker;
import cat.uvic.teknos.bank.repositories.WorkerRepository;

import java.util.Set;

public class JdbcWorkerRepository implements WorkerRepository {
    @Override
    public void save(Worker model) {

    }

    @Override
    public void delete(Worker model) {

    }

    @Override
    public Worker get(Integer id) {
        return null;
    }

    @Override
    public Set<Worker> getAll() {
        return Set.of();
    }
}
