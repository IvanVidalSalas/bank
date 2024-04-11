package cat.uvic.teknos.bank.repositories;


import cat.uvic.teknos.bank.models.Worker;

import java.util.Set;

public interface WorkerRepository extends Repository<Integer, Worker> {
    @Override
    void save(Worker model);

    @Override
    void delete(Worker model);

    @Override
    Worker get(Integer id);

    @Override
    Set<Worker> getAll();
}
