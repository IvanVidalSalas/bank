package cat.uvic.teknos.bank.domain.jdbc.repositories;

import cat.uvic.teknos.bank.domain.jdbc.models.Customer;
import cat.uvic.teknos.bank.domain.jdbc.models.Loan;
import com.fcardara.dbtestutils.junit.CreateSchemaExtension;
import com.fcardara.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})
class JdbcLoanRepositoryTest {

    private final Connection connection;

    public JdbcLoanRepositoryTest(Connection connection) {
        this.connection = connection;
    }

    @Test
    @DisplayName("Given a new loan, when save, then a new record is added to the LOAN table")
    void insertNewLoanTest() {

        Customer customer = new Customer();
        customer.setId(3);

        Loan loan = new Loan();
        loan.setCustomer(customer);
        loan.setLoanDate(Date.valueOf(LocalDate.parse("2024-06-11")));
        loan.setReturnDate(Date.valueOf(LocalDate.parse("2024-06-18")));

        var repository = new JdbcLoanRepository(connection);
        repository.save(loan);
        assertFalse(loan.getId() > 0);
    }
    @Test
    @DisplayName("Given an existing Loan with modified fields")
    void shouldUpdateALoanTest() {

        Customer customer = new Customer();
        customer.setId(3);

        Loan loan = new Loan();
        loan.setCustomer(customer);
        loan.setLoanDate(Date.valueOf(LocalDate.parse("2024-07-11")));
        loan.setReturnDate(Date.valueOf(LocalDate.parse("2024-07-18")));

        var repository = new JdbcLoanRepository(connection);

        repository.save(loan);
        assertFalse(loan.getId() > 0);
    }

    @Test
    void delete() {

        Customer customer = new Customer();
        customer.setId(3);

        Loan loan = new Loan();
        loan.setCustomer(customer);

        var repository = new JdbcLoanRepository(connection);
        repository.delete(loan);
    }

    @Test
    void get() {
        int id = 1;
        var repository = new JdbcLoanRepository(connection);
        cat.uvic.teknos.bank.models.Loan loan = repository.get(id);
        SoutLoan(loan);
    }

    @Test
    void getAll() {
        var repository = new JdbcLoanRepository(connection);
        Set<cat.uvic.teknos.bank.models.Loan> loans = repository.getAll();

        for(var loan:loans){
            SoutLoan(loan);
        }
    }

    private void SoutLoan(cat.uvic.teknos.bank.models.Loan loan){
        System.out.println("Id: " + loan.getCustomer().getId());
        System.out.println("Loan Date: " + loan.getLoanDate());
        System.out.println("Return Date: " + loan.getReturnDate());

        System.out.println("\n");
    }
}
