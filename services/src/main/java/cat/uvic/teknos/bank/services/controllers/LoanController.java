package cat.uvic.teknos.bank.services.controllers;

import cat.uvic.teknos.bank.models.Loan;
import cat.uvic.teknos.bank.models.ModelFactory;
import cat.uvic.teknos.bank.repositories.LoanRepository; // Import LoanRepository
import cat.uvic.teknos.bank.repositories.RepositoryFactory;
import cat.uvic.teknos.bank.services.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class LoanController implements Controller {

    private RepositoryFactory repositoryFactory;
    private ModelFactory modelFactory;
    private ObjectMapper mapper = new ObjectMapper();
    private LoanRepository loanRepository; // Use LoanRepository

    public LoanController(RepositoryFactory repositoryFactory, ModelFactory modelFactory) {
        this.repositoryFactory = repositoryFactory;
        this.modelFactory = modelFactory;
        this.loanRepository = repositoryFactory.getLoanRepository(); // Initialize LoanRepository
    }

    @Override
    public void delete(int id) {
        Optional<Loan> optionalLoan = Optional.ofNullable(loanRepository.get(id));
        if (optionalLoan.isEmpty()) {
            throw new ResourceNotFoundException("Loan with id " + id + " not found");
        }
        loanRepository.delete(optionalLoan.get());
    }

    @Override
    public void put(int id, String json) {
        Optional<Loan> optionalLoan = Optional.ofNullable(loanRepository.get(id));
        if (optionalLoan.isEmpty()) {
            throw new ResourceNotFoundException("Loan with id " + id + " not found");
        }

        try {
            // Deserialize JSON to Loan object
            Loan updatedLoan = mapper.readValue(json, Loan.class);
            Loan loan = optionalLoan.get();


            loan.setLoanDate(updatedLoan.getLoanDate());
            loan.setReturnDate(updatedLoan.getReturnDate());
            loan.setCustomer(updatedLoan.getCustomer());

            loanRepository.save(loan); // Save the updated loan
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse loan JSON", e);
        }
    }

    @Override
    public void post(String json) {
        try {
            // Deserialize JSON to Loan object
            Loan newLoan = mapper.readValue(json, Loan.class);
            loanRepository.save(newLoan); // Save the new loan
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse loan JSON", e);
        }
    }

    @Override
    public String get() {
        var loans = loanRepository.getAll();
        try {
            return mapper.writeValueAsString(loans);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize loans to JSON", e);
        }
    }

    @Override
    public String get(int id) {
        Optional<Loan> optionalLoan = Optional.ofNullable(loanRepository.get(id));
        if (optionalLoan.isEmpty()) {
            throw new ResourceNotFoundException("Loan with id " + id + " not found");
        }
        try {
            return mapper.writeValueAsString(optionalLoan.get());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize loan to JSON", e);
        }
    }
}
