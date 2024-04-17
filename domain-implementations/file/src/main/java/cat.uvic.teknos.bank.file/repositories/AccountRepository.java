package cat.uvic.teknos.bank.file.repositories;

import cat.uvic.teknos.bank.models.Account;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AccountRepository {
    private final Map<Integer, Account> accounts = new HashMap<>();
    private final String dataPath;

    public AccountRepository(String dataPath) {
        this.dataPath = dataPath;
        load();
    }

    protected void load() {
        try {
            Path path = Path.of(dataPath);
            if (Files.exists(path) && Files.readAllBytes(path).length > 0) {
                try (var inputStream = new ObjectInputStream(new FileInputStream(dataPath))) {
                    accounts.putAll((Map<Integer, Account>) inputStream.readObject());
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
            outputStream.writeObject(accounts);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Account account) {
        if (account.getId() <= 0) {
            int newId = accounts.keySet().stream().mapToInt(Integer::intValue).max().orElse(0) + 1;
            account.setId(newId);
        }
        accounts.put(account.getId(), account);
        write();
    }

    public void delete(Account account) {
        accounts.remove(account.getId());
        write();
    }

    public Account get(Integer id) {
        return accounts.get(id);
    }

    public Set<Account> getAll() {
        return Set.copyOf(accounts.values());
    }}
