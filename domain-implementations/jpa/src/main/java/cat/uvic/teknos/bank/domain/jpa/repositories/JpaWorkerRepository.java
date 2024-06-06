package cat.uvic.teknos.bank.domain.jpa.repositories;

import cat.uvic.teknos.bank.models.Worker;
import cat.uvic.teknos.bank.repositories.WorkerRepository;
import jakarta.persistence.EntityManager;

import java.util.HashSet;
import java.util.Set;

public class JpaWorkerRepository implements WorkerRepository {
    private EntityManager entityManager;

    public JpaWorkerRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void save(Worker model) {
        try {
            entityManager.getTransaction().begin();
            if (model.getId() <= 0) {
                // insert
                entityManager.persist(model);
            } else if (!entityManager.contains(model)) {
                // update
                Worker worker = entityManager.find(Worker.class, model.getId());

                if (model.getFirstName() == null || model.getFirstName().isEmpty()) {
                    model.setFirstName(worker.getFirstName());
                }
                if (model.getLastName() == null || model.getLastName().isEmpty()) {
                    model.setLastName(worker.getLastName());
                }
                if (model.getTransaction() == null || model.getTransaction().isEmpty()) {
                    model.setTransaction(worker.getTransaction());
                }
                if (model.getAccount() == null || model.getAccount().isEmpty()) {
                    model.setAccount(worker.getAccount());
                }
                entityManager.merge(model);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println("ERROR SAVING WORKER");
        }
    }

    @Override
    public void delete(Worker model) {
        try{
            entityManager.getTransaction().begin();
            var worker = entityManager.find(Worker.class, model.getId());
            entityManager.remove(worker);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("ERROR DELETING WORKER");
        }
    }

    @Override
    public Worker get(Integer id) {
        return entityManager.find(Worker.class, id);
    }

    @Override
    public Set<Worker> getAll() {
        return new HashSet<>(entityManager.createQuery("SELECT w FROM Worker w", Worker.class).getResultList());
    }
}
