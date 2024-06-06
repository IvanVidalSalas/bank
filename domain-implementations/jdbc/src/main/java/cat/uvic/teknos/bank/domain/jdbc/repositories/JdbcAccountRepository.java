package cat.uvic.teknos.bank.domain.jdbc.repositories;

import cat.uvic.teknos.bank.domain.jdbc.models.Customer;
import cat.uvic.teknos.bank.domain.jdbc.models.Worker;
import cat.uvic.teknos.bank.models.Account;
import cat.uvic.teknos.bank.repositories.AccountRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class JdbcAccountRepository implements AccountRepository {

    private final Connection connection;

    public JdbcAccountRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Account model) {
        if (model.getId() <= 0) {
            insert(model);
        } else {
            update(model);
        }
    }
    private void update(Account model) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE ACCOUNT SET ACCOUNT_TYPE = ?, BALANCE = ?, CUSTOMER_ID = ? WHERE ACCOUNT_ID = ?")) {
            statement.setString(1, model.getAccountType());
            statement.setInt(2, model.getBalance());
            statement.setInt(3, model.getCustomer().getId());
            statement.setInt(4, model.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insert(Account model) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO ACCOUNT(ACCOUNT_TYPE, BALANCE, CUSTOMER_ID) VALUES(?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, model.getAccountType());
            statement.setDouble(2, model.getBalance());
            statement.setInt(3, model.getCustomer().getId());


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
    public void delete(Account model) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM ACCOUNT WHERE ACCOUNT_ID = ?", Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, model.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account get(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM ACCOUNT WHERE ACCOUNT_ID = ?", Statement.RETURN_GENERATED_KEYS)) {
            Account account = null;
            statement.setInt(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                account = new cat.uvic.teknos.bank.domain.jdbc.models.Account();
                account.setId(resultSet.getInt("ACCOUNT_ID"));
                account.setAccountType(resultSet.getString("ACCOUNT_TYPE"));
                account.setBalance(resultSet.getInt("BALANCE"));

                var customer = new Customer();
                customer.setId(resultSet.getInt("CUSTOMER_ID"));
                account.setCustomer(customer);

            }
            return account;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Account> getAll() {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM ACCOUNT", Statement.RETURN_GENERATED_KEYS)) {
            var accounts = new HashSet<Account>();
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var account = new cat.uvic.teknos.bank.domain.jdbc.models.Account();
                account.setId(resultSet.getInt("ACCOUNT_ID"));
                account.setAccountType(resultSet.getString("ACCOUNT_TYPE"));
                account.setBalance(resultSet.getInt("BALANCE"));

                var customer = new Customer();
                customer.setId(resultSet.getInt("CUSTOMER_ID"));
                account.setCustomer(customer);

                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
