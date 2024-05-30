package cat.uvic.teknos.bank.domain.jdbc.repositories;

import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.models.Loan;
import cat.uvic.teknos.bank.repositories.CustomerRepository;
import cat.uvic.teknos.bank.repositories.LoanRepository;


import java.sql.*;
import java.util.HashSet;
import java.util.Set;


public class JdbcLoanRepository implements LoanRepository {

    private final Connection connection;

    public JdbcLoanRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Loan model) {
        if (model.getId() <= 0) {
            insert(model);
        } else {
            update(model);
        }
    }

    private void update(Loan model) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE LOAN SET CUSTOMER_ID = ?, LOAN_DATE = ?, RETURN_DATE = ? WHERE LOAN_ID = ?", Statement.RETURN_GENERATED_KEYS)){
            statement.setInt(1, model.getCustomer().getId());
            statement.setDate(2, Date.valueOf(model.getLoanDate()));
            statement.setDate(3, Date.valueOf(model.getReturnDate()));
            statement.setInt(4, model.getId());

            statement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void insert(Loan model) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO LOAN(LOAN_ID, CUSTOMER_ID, LOAN_DATE, RETURN_DATE) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS)){
            statement.setInt(1, model.getId());
            statement.setInt(2, model.getCustomer().getId());
            statement.setDate(3, Date.valueOf(model.getLoanDate()));
            statement.setDate(4, Date.valueOf(model.getReturnDate()));
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if(keys.next()){
                model.setId(keys.getInt(1));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Loan model) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM LOAN WHERE LOAN_ID = (?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, model.getId());
            try (PreparedStatement statement2 = connection.prepareStatement("DELETE FROM LOAN WHERE LOAN_ID = (?)")){
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Loan get(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM LOAN WHERE LOAN_ID =  (?)", Statement.RETURN_GENERATED_KEYS)) {
            Loan loan = null;
            statement.setInt(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                loan = new cat.uvic.teknos.bank.domain.jdbc.models.Loan();
                loan.setId(resultSet.getInt("LOAN_ID"));
                Customer customer = CustomerRepository.get(resultSet.getInt("CUSTOMER_ID"));
                loan.setCustomer(customer);
                loan.setLoanDate(resultSet.getDate("LOAN_DATE").toLocalDate());
                loan.setLoanDate(resultSet.getDate("RETURN_DATE").toLocalDate());

            }
            return loan;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Loan> getAll() {
        try (PreparedStatement statement = connection.prepareStatement( "SELECT * FROM CUSTOMER", Statement.RETURN_GENERATED_KEYS)) {
            var loans = new HashSet<Loan>();
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var loan = new cat.uvic.teknos.bank.domain.jdbc.models.Loan();
                loan.setId(resultSet.getInt("LOAN_ID"));
                Customer customer = CustomerRepository.get(resultSet.getInt("CUSTOMER_ID"));
                loan.setCustomer(customer);
                loan.setLoanDate(resultSet.getDate("LOAN_DATE").toLocalDate());
                loan.setLoanDate(resultSet.getDate("RETURN_DATE").toLocalDate());
                loans.add(loan);
            }
            return loans;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
