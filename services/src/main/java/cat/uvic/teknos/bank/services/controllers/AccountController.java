package cat.uvic.teknos.bank.services.controllers;

import cat.uvic.teknos.bank.models.Account;
import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.repositories.AccountRepository;
import cat.uvic.teknos.bank.repositories.RepositoryFactory;
import cat.uvic.teknos.bank.services.exception.ResourceNotFoundException;
import cat.uvic.teknos.bank.services.utils.Mappers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountController implements Controller {
    //private final RepositoryFactory repositoryFactory;
    //private final ModelFactory modelFactory;
    private final ObjectMapper mapper = new ObjectMapper();
    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        //this.repositoryFactory = repositoryFactory;
        //this.modelFactory = modelFactory;
        this.accountRepository = accountRepository;
    }

    @Override
    public void delete(int id) {
        Account account = accountRepository.get(id);
        if (account == null) {
            throw new ResourceNotFoundException("Account " + id + " not found.");
        }
        accountRepository.delete(account);
    }

    @Override
    public void put(int id, String json) {
        Account account = accountRepository.get(id);
        if (account == null) {
            throw new ResourceNotFoundException("Account " + id + " not found.");
        }
        try {
            // Deserialize JSON to Account object
            var updatedAccount = Mappers.get().readValue(json, Account.class);

            account.setAccountType(updatedAccount.getAccountType());
            account.setBalance(updatedAccount.getBalance());
            account.setCustomer(updatedAccount.getCustomer());
            account.setWorker(updatedAccount.getWorker());

            accountRepository.save(account);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse account JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public void post(String json) {
        try {
            // Deserialize JSON to Account object
            var newAccount = Mappers.get().readValue(json, Account.class);
            accountRepository.save(newAccount);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse account JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public String get() {
        var accounts = accountRepository.getAll();
        try {
            return mapper.writeValueAsString(accounts);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize accounts to JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public String get(int id) {
        Account account = accountRepository.get(id);
        if (account == null) {
            throw new ResourceNotFoundException("Account " + id + " not found.");
        }
        try {
            return mapper.writeValueAsString(account);
        } catch (JsonProcessingException e) {
            System.err.println("Failed to serialize account to JSON: " + e.getMessage());
            throw new RuntimeException("Failed to serialize account to JSON", e);
        }
    }
}
