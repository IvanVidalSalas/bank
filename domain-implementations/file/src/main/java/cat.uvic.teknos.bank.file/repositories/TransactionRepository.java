package cat.uvic.teknos.bank.file.repositories;

import cat.uvic.teknos.bank.models.Transaction;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TransactionRepository {
    private final Map<Integer, Transaction> transactions = new HashMap<>();
    private final String dataPath;

    public TransactionRepository(String dataPath) {
        this.dataPath = dataPath;
        load();
    }

    protected void load() {
        try {
            Path path = Path.of(dataPath);
            if (Files.exists(path) && Files.readAllBytes(path).length > 0) {
                try (var inputStream = new ObjectInputStream(new FileInputStream(dataPath))) {
                    transactions.putAll((Map<Integer, Transaction>) inputStream.readObject());
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
            outputStream.writeObject(transactions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Transaction transaction) {
        if (transaction.getId() <= 0) {
            int newId = transactions.keySet().stream().mapToInt(Integer::intValue).max().orElse(0) + 1;
            transaction.setId(newId);
        }
        transactions.put(transaction.getId(), transaction);
        write();
    }

    public void delete(Transaction transaction) {
        transactions.remove(transaction.getId());
        write();
    }

    public Transaction get(Integer id) {
        return transactions.get(id);
    }

    public Set<Transaction> getAll() {
        return Set.copyOf(transactions.values());
    }
}
