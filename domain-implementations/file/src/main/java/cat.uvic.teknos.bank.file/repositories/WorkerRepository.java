package cat.uvic.teknos.bank.file.repositories;

import cat.uvic.teknos.bank.models.Worker;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WorkerRepository {
    private final Map<Integer, Worker> workers = new HashMap<>();
    private final String dataPath;

    public WorkerRepository(String dataPath) {
        this.dataPath = dataPath;
        load();
    }

    protected void load() {
        try {
            Path path = Path.of(dataPath);
            if (Files.exists(path) && Files.readAllBytes(path).length > 0) {
                try (var inputStream = new ObjectInputStream(new FileInputStream(dataPath))) {
                    workers.putAll((Map<Integer, Worker>) inputStream.readObject());
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void write() {
        try (var outputStream = new ObjectOutputStream(new FileOutputStream(dataPath))) {
            outputStream.writeObject(workers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Worker worker) {
        if (worker.getId() <= 0) {
            int newId = workers.keySet().stream().mapToInt(Integer::intValue).max().orElse(0) + 1;
            worker.setId(newId);
        }
        workers.put(worker.getId(), worker);
        write();
    }

    public void delete(Worker worker) {
        workers.remove(worker.getId());
        write();
    }

    public Worker get(Integer id) {
        return workers.get(id);
    }

    public Set<Worker> getAll() {
        return Set.copyOf(workers.values());
    }
}
