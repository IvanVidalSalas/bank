package cat.uvic.teknos.bank.services.controllers;

import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.models.Loan;
import cat.uvic.teknos.bank.repositories.LoanRepository;
import cat.uvic.teknos.bank.services.exception.ResourceNotFoundException;
import cat.uvic.teknos.bank.services.utils.Mappers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoanController implements Controller {

    private ObjectMapper mapper = new ObjectMapper();
    private LoanRepository loanRepository;

    public LoanController(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public void delete(int id) {
        Loan loan = loanRepository.get(id);
        if (loan == null) {
            throw new ResourceNotFoundException("Loan " + id + " not found");
        }
        loanRepository.delete(loan);
    }

    @Override
    public void put(int id, String json) {
        Loan loan = loanRepository.get(id);
        if (loan == null) {
            throw new ResourceNotFoundException("Loan " + id + " not found");
        }
        try {
            // Deserialize JSON to Loan object
            var updatedLoan = Mappers.get().readValue(json, Loan.class);

            loan.setLoanDate(updatedLoan.getLoanDate());
            loan.setReturnDate(updatedLoan.getReturnDate());
            loan.setCustomer(updatedLoan.getCustomer());

            loanRepository.save(loan);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse loan JSON", e);
        }
    }

    @Override
    public void post(String json) {
        try {
            // Deserialize JSON to Loan object
            var newLoan = Mappers.get().readValue(json, Loan.class);
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
        Loan loan = loanRepository.get(id);
        if (loan == null) {
            throw new ResourceNotFoundException("Loan " + id + " not found");
        }
        try {
            return mapper.writeValueAsString(loan);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize loan to JSON", e);
        }
    }
}
