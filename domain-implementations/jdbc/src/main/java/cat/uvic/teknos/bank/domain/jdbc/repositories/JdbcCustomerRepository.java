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
        try(PreparedStatement statement = connection.prepareStatement("UPDATE CUSTOMER FIRST_NAME = ? WHERE ID = ?", Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, model.getFirstName());
            statement.setInt(2, model.getId());
            statement.executeUpdate();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void insert(Customer model) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO CUSTOMER(ID) VALUES(?)", Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, model.getFirstName());
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
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM CUSTOMER WHERE ID = (?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,model.getFirstName());
        try (PreparedStatement statement2 = connection.prepareStatement("DELETE FROM CUSTOMER WHERE ID = (?)")){
            statement.executeUpdate();
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer get(Integer Id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE ID =  (?)", Statement.RETURN_GENERATED_KEYS)) {
            Customer customer = null;
            statement.setInt(1, Id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                customer = new cat.uvic.teknos.bank.domain.jdbc.models.Customer();
                customer.setId(resultSet.getInt("ID"));
                customer.setFirstName(resultSet.getString("FIRST NAME"));
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
                customer.setId(resultSet.getInt("ID"));
                customer.setFirstName(resultSet.getString("FIRST NAME"));
                customers.add(customer);
            }
            return customers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer getByName(String name) {
        return null;
    }


}
