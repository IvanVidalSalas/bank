package cat.uvic.teknos.bank.domain.jdbc.repositories;

import cat.uvic.teknos.bank.domain.jdbc.models.Loan;
import com.fcardara.dbtestutils.junit.CreateSchemaExtension;
import com.fcardara.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.SQLException;
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
    void insertNewLoanTest() throws SQLException {
        Loan loan = new Loan();
        loan.setId(1);
        loan.setCustomer(loan.getCustomer());
        loan.setLoanDate(LocalDate.now());
        loan.setReturnDate(LocalDate.now().plusMonths(1)); //

        var repository = new JdbcLoanRepository(connection);

        repository.save(loan);
        assertTrue(loan.getId() > 0);


    }
    @Test
    @DisplayName("Given an existing Loan with modified fields")
    void shouldUpdateALoanTest() throws SQLException {
        Loan loan = new Loan();
        loan.setId(2);
        loan.setCustomer(loan.getCustomer());
        loan.setLoanDate(LocalDate.now());
        loan.setReturnDate(LocalDate.now().plusMonths(2));

        var repository = new JdbcCustomerRepository(connection);

        repository.save(loan);
        assertTrue(loan.getId() > 0);
    }

    @Test
    void delete() {
        Loan loan = new Loan();
        loan.setId(1);

        var repository = new JdbcLoanRepository(connection);
        repository.delete(loan);

        assertNull(repository.get(1));
    }

    @Test
    void get() {
        var repository = new JdbcLoanRepository(connection);
        assertNotNull(repository.get(1));
    }

    @Test
    void getAll() {
        var repository = new JdbcLoanRepository(connection);

        Set<cat.uvic.teknos.bank.models.Loan> loans = repository.getAll();

        assertFalse(loans.isEmpty(), "Should not be empty");
    }
}
