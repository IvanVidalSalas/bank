package cat.uvic.teknos.bank.services.controllers;

import cat.uvic.teknos.bank.models.Account;
import cat.uvic.teknos.bank.models.Worker;
import cat.uvic.teknos.bank.repositories.WorkerRepository;
import cat.uvic.teknos.bank.repositories.RepositoryFactory;
import cat.uvic.teknos.bank.services.exception.ResourceNotFoundException;
import cat.uvic.teknos.bank.services.utils.Mappers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WorkerController implements Controller {
    //private final RepositoryFactory repositoryFactory;
    //private final ModelFactory modelFactory;
    private final ObjectMapper mapper = new ObjectMapper();
    private final WorkerRepository workerRepository;

    public WorkerController(WorkerRepository workerRepository) {
        //this.repositoryFactory = repositoryFactory;
        //this.modelFactory = modelFactory;
        this.workerRepository = workerRepository;
    }

    @Override
    public void delete(int id) {
        Worker worker = workerRepository.get(id);
        if (worker == null) {
            throw new ResourceNotFoundException("Worker " + id + " not found.");
        }
        workerRepository.delete(worker);
    }

    @Override
    public void put(int id, String json) {
        Worker worker = workerRepository.get(id);
        if (worker == null) {
            throw new ResourceNotFoundException("Worker " + id + " not found.");
        }
        try {
            // Deserialize JSON to Worker object
            var updatedWorker = Mappers.get().readValue(json, Worker.class);

            worker.setFirstName(updatedWorker.getFirstName());
            worker.setLastName(updatedWorker.getLastName());
            worker.setTransaction(updatedWorker.getTransaction());
            worker.setAccount(updatedWorker.getAccount());

            workerRepository.save(worker);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse worker JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public void post(String json) {
        try {
            // Deserialize JSON to Worker object
            var newWorker = Mappers.get().readValue(json, Worker.class);
            workerRepository.save(newWorker);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse worker JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public String get() {
        var workers = workerRepository.getAll();
        try {
            return mapper.writeValueAsString(workers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize workers to JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public String get(int id) {
        Worker worker = workerRepository.get(id);
        if (worker == null) {
            throw new ResourceNotFoundException("Worker " + id + " not found.");
        }
        try {
            return mapper.writeValueAsString(worker);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize worker to JSON: " + e.getMessage(), e);
        }
    }
}
