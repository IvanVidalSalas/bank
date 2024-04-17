package cat.uvic.teknos.bank.file.repositories;

import cat.uvic.teknos.bank.models.Loan;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LoanRepository {
    private final Map<Integer, Loan> loans = new HashMap<>();
    private final String dataPath;

    public LoanRepository(String dataPath) {
        this.dataPath = dataPath;
        load();
    }

    protected void load() {
        try {
            Path path = Path.of(dataPath);
            if (Files.exists(path) && Files.readAllBytes(path).length > 0) {
                try (var inputStream = new ObjectInputStream(new FileInputStream(dataPath))) {
                    loans.putAll((Map<Integer, Loan>) inputStream.readObject());
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
            outputStream.writeObject(loans);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Loan loan) {
        if (loan.getId() <= 0) {
            int newId = loans.keySet().stream().mapToInt(Integer::intValue).max().orElse(0) + 1;
            loan.setId(newId);
        }
        loans.put(loan.getId(), loan);
        write();
    }

    public void delete(Loan loan) {
        loans.remove(loan.getId());
        write();
    }

    public Loan get(Integer id) {
        return loans.get(id);
    }

    public Set<Loan> getAll() {
        return Set.copyOf(loans.values());
    }
}
