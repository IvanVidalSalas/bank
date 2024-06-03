package cat.uvic.teknos.bank.domain.jdbc.repositories;

import cat.uvic.teknos.bank.models.Transaction;
import cat.uvic.teknos.bank.repositories.TransactionRepository;
import cat.uvic.teknos.bank.domain.jdbc.models.Customer;
import cat.uvic.teknos.bank.domain.jdbc.models.Worker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class JdbcTransactionRepository implements TransactionRepository {

    private final Connection connection;

    public JdbcTransactionRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Transaction model) {
        if (model.getId() <= 0) {
            insert(model);
        } else {
            update(model);
        }
    }

    private void update(Transaction model) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE TRANSACTION SET TRANSACTION_TYPE = ?, AMOUNT = ?, TRANSACTION_DATE = ?, CUSTOMER_ID = ? WHERE TRANSACTION_ID = ?",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, model.getTransactionType());
            statement.setInt(2, model.getAmount());
            statement.setDate(3, model.getTransactionDate());
            statement.setInt(4, model.getCustomer().getId());
            statement.setInt(5, model.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insert(Transaction model) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO TRANSACTION(TRANSACTION_TYPE, AMOUNT, TRANSACTION_DATE, CUSTOMER_ID) VALUES(?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, model.getTransactionType());
            statement.setInt(2, model.getAmount());
            statement.setDate(3, model.getTransactionDate());
            statement.setInt(4, model.getCustomer().getId());

            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                model.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Transaction model) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM TRANSACTION WHERE TRANSACTION_ID = ?", Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, model.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Transaction get(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM TRANSACTION WHERE TRANSACTION_ID = (?)", Statement.RETURN_GENERATED_KEYS)) {
            Transaction transaction = null;
            statement.setInt(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                transaction = new cat.uvic.teknos.bank.domain.jdbc.models.Transaction();
                transaction.setId(resultSet.getInt("TRANSACTION_ID"));
                var customer = new Customer();
                customer.setId(resultSet.getInt("CUSTOMER_ID"));
                transaction.setCustomer(customer);
                transaction.setTransactionType(resultSet.getString("TRANSACTION_TYPE"));
                transaction.setAmount(resultSet.getInt("AMOUNT"));
                transaction.setTransactionDate(resultSet.getDate("TRANSACTION_DATE"));
            }
            return transaction;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Transaction> getAll() {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM TRANSACTION", Statement.RETURN_GENERATED_KEYS)) {
            var transactions = new HashSet<Transaction>();
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var transaction = new cat.uvic.teknos.bank.domain.jdbc.models.Transaction();
                transaction.setId(resultSet.getInt("TRANSACTION_ID"));
                transaction.setTransactionType(resultSet.getString("TRANSACTION_TYPE"));
                transaction.setAmount(resultSet.getInt("AMOUNT"));
                transaction.setTransactionDate(resultSet.getDate("TRANSACTION_DATE"));

                var customer = new Customer();
                customer.setId(resultSet.getInt("CUSTOMER_ID"));
                transaction.setCustomer(customer);

                transactions.add(transaction);
            }
            return transactions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

