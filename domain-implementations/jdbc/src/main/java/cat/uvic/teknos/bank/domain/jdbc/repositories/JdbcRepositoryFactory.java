package cat.uvic.teknos.bank.domain.jdbc.repositories;

import cat.uvic.teknos.bank.repositories.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class JdbcRepositoryFactory implements RepositoryFactory {


    public class JdbcCustomerRepository(){

        private Connection connection;

        public JdbcCustomerRepository(Connection connection){
            try{
                var properties = new Properties();
                properties.setload(this.getClass().getResourceAsStream("datasource.properties"));
                connection = DriverManager.getConnection(String.format());

            }
        }


    }

    @Override
    public CustomerRepository getCustomerRepository() {
        return new JdbcCustomerRepository();
    }

    @Override
    public AccountRepository getAccountRepository() {
        return null;
    }

    @Override
    public LoanRepository getLoanRepository() {
        return null;
    }

    @Override
    public TransactionRepository getTransactionRepository() {
        return null;
    }

    @Override
    public WorkerRepository getWorkerRepository() {
        return null;
    }
}
