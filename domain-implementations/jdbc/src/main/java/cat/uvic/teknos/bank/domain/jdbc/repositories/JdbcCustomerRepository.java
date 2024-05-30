package cat.uvic.teknos.bank.domain.jdbc.repositories;

import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.repositories.CustomerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class JdbcCustomerRepository implements CustomerRepository {

    private final Connection connection;

    public JdbcCustomerRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Customer model) {
        if (model.getId() <= 0) {
            insert(model);
        } else {
            update(model);
        }
    }

    private void update(Customer model) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE CUSTOMER SET FIRST_NAME = ?,LAST_NAME = ?,ADDRES = ?,EMAIL = ? WHERE CUSTOMER_ID = ?", Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, model.getFirstName());
            statement.setString(2, model.getLastName());
            statement.setString(3, model.getAddress());
            statement.setString(4, model.getEmail());
            statement.setInt(5, model.getId());

            statement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void insert(Customer model) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO CUSTOMER(CUSTOMER_ID, FIRST_NAME, LAST_NAME, ADDRES, EMAIL) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)){
            statement.setInt(1, model.getId());
            statement.setString(2, model.getFirstName());
            statement.setString(3, model.getLastName());
            statement.setString(4, model.getAddress());
            statement.setString(5, model.getEmail());
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
    public void delete(Customer model) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM CUSTOMER WHERE CUSTOMER_ID = (?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, model.getId());
        try (PreparedStatement statement2 = connection.prepareStatement("DELETE FROM CUSTOMER WHERE CUSTOMER_ID = (?)")){
            statement.executeUpdate();
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer get(Integer Id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE CUSTOMER_ID =  (?)", Statement.RETURN_GENERATED_KEYS)) {
            Customer customer = null;
            statement.setInt(1, Id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                customer = new cat.uvic.teknos.bank.domain.jdbc.models.Customer();
                customer.setId(resultSet.getInt("CUSTOMER_ID"));
                customer.setFirstName(resultSet.getString("FIRST_NAME"));
                customer.setLastName(resultSet.getString("LAST_NAME"));
                customer.setAddress(resultSet.getString("ADDRESS"));
                customer.setEmail(resultSet.getString("EMAIL"));
            }
            return customer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Customer> getAll() {
        try (PreparedStatement statement = connection.prepareStatement( "SELECT * FROM CUSTOMER", Statement.RETURN_GENERATED_KEYS)) {
            var customers = new HashSet<Customer>();
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var customer = new cat.uvic.teknos.bank.domain.jdbc.models.Customer();
                customer.setId(resultSet.getInt("CUSTOMER_ID"));
                customer.setFirstName(resultSet.getString("FIRST_NAME"));
                customer.setLastName(resultSet.getString("LAST_NAME"));
                customer.setAddress(resultSet.getString("ADDRESS"));
                customer.setEmail(resultSet.getString("EMAIL"));
                customers.add(customer);
            }
            return customers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
