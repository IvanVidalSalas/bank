package cat.uvic.teknos.bank.file.repositories;

import cat.uvic.teknos.bank.models.Customer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CustomerRepository {
    private final Map<Integer, Customer> customers = new HashMap<>();
    private final String dataPath;

    public CustomerRepository(String dataPath) {
        this.dataPath = dataPath;
        load();
    }

    protected void load() {
        try {
            Path path = Path.of(dataPath);
            if (Files.exists(path) && Files.readAllBytes(path).length > 0) {
                try (var inputStream = new ObjectInputStream(new FileInputStream(dataPath))) {
                    customers.putAll((Map<Integer, Customer>) inputStream.readObject());
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
            outputStream.writeObject(customers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Customer customer) {
        if (customer.getId() <= 0) {
            int newId = customers.keySet().stream().mapToInt(Integer::intValue).max().orElse(0) + 1;
            customer.setId(newId);
        }
        customers.put(customer.getId(), customer);
        write();
    }

    public void delete(Customer customer) {
        customers.remove(customer.getId());
        write();
    }

    public Customer get(Integer id) {
        return customers.get(id);
    }

    public Set<Customer> getAll() {
        return Set.copyOf(customers.values());
    }
}
