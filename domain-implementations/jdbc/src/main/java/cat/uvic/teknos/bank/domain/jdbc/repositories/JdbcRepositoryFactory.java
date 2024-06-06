package cat.uvic.teknos.bank.domain.jdbc.repositories;

import cat.uvic.teknos.bank.exceptions.RepositoryException;
import cat.uvic.teknos.bank.repositories.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcRepositoryFactory implements RepositoryFactory {

    private Connection connection;

    public JdbcRepositoryFactory(){
            try {
                var properties = new Properties();
                properties.load(this.getClass().getResourceAsStream("/datasource.properties"));

                connection = DriverManager.getConnection(String.format("%s:%s://%s/%s",
                        properties.getProperty("protocol"),
                        properties.getProperty("subprotocol"),
                        properties.getProperty("url"),
                        properties.getProperty("database")), properties.getProperty("user"), properties.getProperty("password"));
            } catch (SQLException e) {
                throw new RepositoryException(e);
            } catch (IOException e) {
                throw new RepositoryException();
            }
    }


    @Override
    public CustomerRepository getCustomerRepository() {
        return new JdbcCustomerRepository(connection);
    }

    @Override
    public AccountRepository getAccountRepository() { return new JdbcAccountRepository(connection); }

    @Override
    public LoanRepository getLoanRepository() { return new JdbcLoanRepository(connection); }

    @Override
    public TransactionRepository getTransactionRepository() { return new JdbcTransactionRepository(connection); }

    @Override
    public WorkerRepository getWorkerRepository() { return new JdbcWorkerRepository(connection); }
}
