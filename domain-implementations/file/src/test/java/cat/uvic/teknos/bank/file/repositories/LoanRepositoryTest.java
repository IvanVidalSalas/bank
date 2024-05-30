package cat.uvic.teknos.bank.file.repositories;

import cat.uvic.teknos.bank.file.models.Loan;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LoanRepositoryTest {

    private final String testDataPath = "test-loans.txt";

    @Test
    void save() {
        var repository = new LoanRepository(testDataPath);
        var loan = new Loan();
        loan.setId(1);
        loan.setLoanDate(Date.valueOf(""));
        loan.setReturnDate(Date.valueOf(""));
        repository.save(loan);
        assertNotNull(loan.getId());
    }

    @Test
    void update() {
        var repository = new LoanRepository(testDataPath);
        var loan = new Loan();
        loan.setId(1);
        loan.setLoanDate(Date.valueOf(""));
        loan.setReturnDate(Date.valueOf(""));
        repository.save(loan);
        loan.setReturnDate(Date.valueOf(""));
        repository.save(loan);

        var updatedLoan = repository.get(loan.getId());
        assertEquals(LocalDate.now().plusDays(30), updatedLoan.getReturnDate());
    }

    @Test
    void delete() {
        var repository = new LoanRepository(testDataPath);
        var loan = new Loan();
        loan.setId(1);
        loan.setLoanDate(Date.valueOf(""));
        loan.setReturnDate(Date.valueOf(""));
        repository.save(loan);
        repository.delete(loan);
        assertNull(repository.get(loan.getId()));
    }

    @Test
    void get() {
        var repository = new LoanRepository(testDataPath);
        var loan = new Loan();
        loan.setId(1);
        loan.setLoanDate(Date.valueOf(""));
        loan.setReturnDate(Date.valueOf(""));
        repository.save(loan);
        var retrievedLoan = repository.get(loan.getId());
        assertEquals(loan, retrievedLoan);
    }

    @Test
    void getAll() {
        var repository = new LoanRepository(testDataPath);
        var loan1 = new Loan();
        loan1.setId(1);
        loan1.setLoanDate(Date.valueOf(""));
        loan1.setReturnDate(Date.valueOf(""));
        var loan2 = new Loan();
        loan2.setLoanDate(Date.valueOf(""));
        loan2.setReturnDate(Date.valueOf(""));
        repository.save(loan1);
        repository.save(loan2);
        assertEquals(2, repository.getAll().size());
    }
}
