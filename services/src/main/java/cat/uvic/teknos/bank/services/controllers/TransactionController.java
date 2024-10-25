package cat.uvic.teknos.bank.services.controllers;

import cat.uvic.teknos.bank.models.Account;
import cat.uvic.teknos.bank.models.Transaction;
import cat.uvic.teknos.bank.repositories.TransactionRepository;
import cat.uvic.teknos.bank.repositories.RepositoryFactory;
import cat.uvic.teknos.bank.services.exception.ResourceNotFoundException;
import cat.uvic.teknos.bank.services.utils.Mappers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TransactionController implements Controller {
    //private final RepositoryFactory repositoryFactory;
    //private final ModelFactory modelFactory;
    private final ObjectMapper mapper = new ObjectMapper();
    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        //this.repositoryFactory = repositoryFactory;
        //this.modelFactory = modelFactory;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void delete(int id) {
        Transaction transaction = transactionRepository.get(id);
        if (transaction == null) {
            throw new ResourceNotFoundException("Transaction " + id + " not found.");
        }
        transactionRepository.delete(transaction);
    }

    @Override
    public void put(int id, String json) {
        Transaction transaction = transactionRepository.get(id);
        if (transaction == null) {
            throw new ResourceNotFoundException("Transaction " + id + " not found.");
        }
        try {
            // Deserialize JSON to Transaction object
            var updatedTransaction = Mappers.get().readValue(json, Transaction.class);

            transaction.setTransactionType(updatedTransaction.getTransactionType());
            transaction.setAmount(updatedTransaction.getAmount());
            transaction.setTransactionDate(updatedTransaction.getTransactionDate());
            transaction.setCustomer(updatedTransaction.getCustomer());
            transaction.setWorker(updatedTransaction.getWorker());

            transactionRepository.save(transaction);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse transaction JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public void post(String json) {
        try {
            // Deserialize JSON to Transaction object
            var newTransaction = Mappers.get().readValue(json, Transaction.class);
            transactionRepository.save(newTransaction);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse transaction JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public String get() {
        var transactions = transactionRepository.getAll();
        try {
            return mapper.writeValueAsString(transactions);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize transactions to JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public String get(int id) {
        Transaction transaction = transactionRepository.get(id);
        if (transaction == null) {
            throw new ResourceNotFoundException("Transaction " + id + " not found.");
        }
        try {
            return mapper.writeValueAsString(transaction);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize transaction to JSON: " + e.getMessage(), e);
        }
    }
}
