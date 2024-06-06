package cat.uvic.teknos.bank.domain.jdbc.repositories;

import cat.uvic.teknos.bank.models.Worker;
import cat.uvic.teknos.bank.repositories.WorkerRepository;
import cat.uvic.teknos.bank.models.Transaction;
import cat.uvic.teknos.bank.repositories.TransactionRepository;

import java.sql.*;
import java.util.Set;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class JdbcWorkerRepository implements WorkerRepository {

    private final Connection connection;

    public JdbcWorkerRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Worker model) {
        if (model.getId() <= 0) {
            insert(model);
        } else {
            update(model);
        }
    }

    private void update(Worker model) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE WORKER SET TRANSACTION_ID = ?, FIRST_NAME = ?, LAST_NAME = ? WHERE WORKER_ID = ?", Statement.RETURN_GENERATED_KEYS)) {
            Transaction transaction = (Transaction) model.getTransaction();
            if (transaction != null) {
                statement.setInt(1, transaction.getId());
            } else {
                // If the worker doesn't have an associated transaction, set the field to NULL
                statement.setNull(1, Types.INTEGER);
            }
            statement.setString(2, model.getFirstName());
            statement.setString(3, model.getLastName());
            statement.setInt(4, model.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insert(Worker model) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO WORKER(TRANSACTION_ID, FIRST_NAME, LAST_NAME) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            Transaction transaction = (Transaction) model.getTransaction();
            if (transaction != null) {
                statement.setInt(1, transaction.getId());
            } else {
                // If the worker doesn't have an associated transaction, set the field to NULL
                statement.setNull(1, Types.INTEGER);
            }
            statement.setString(2, model.getFirstName());
            statement.setString(3, model.getLastName());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                model.setId(keys.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Worker model) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM WORKER WHERE WORKER_ID = ?", Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, model.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Worker get(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM WORKER WHERE WORKER_ID = ?", Statement.RETURN_GENERATED_KEYS)) {
            Worker worker = null;
            statement.setInt(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                worker = new cat.uvic.teknos.bank.domain.jdbc.models.Worker();
                worker.setId(resultSet.getInt("WORKER_ID"));
                worker.setFirstName(resultSet.getString("FIRST_NAME"));
                worker.setLastName(resultSet.getString("LAST_NAME"));

                var transaction = new cat.uvic.teknos.bank.domain.jdbc.models.Transaction();
                transaction.setId(resultSet.getInt("TRANSACTION_ID"));
                worker.setTransaction(Set.of(transaction));
            }
            return worker;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Worker> getAll() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM WORKER", Statement.RETURN_GENERATED_KEYS)) {
            Set<Worker> workers = new HashSet<>();
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var worker = new cat.uvic.teknos.bank.domain.jdbc.models.Worker();
                worker.setId(resultSet.getInt("WORKER_ID"));
                worker.setFirstName(resultSet.getString("FIRST_NAME"));
                worker.setLastName(resultSet.getString("LAST_NAME"));

                var transaction = new cat.uvic.teknos.bank.domain.jdbc.models.Transaction();
                transaction.setId(resultSet.getInt("TRANSACTION_ID"));
                worker.setTransaction(Set.of (transaction));
                workers.add(worker);
            }
            return workers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
