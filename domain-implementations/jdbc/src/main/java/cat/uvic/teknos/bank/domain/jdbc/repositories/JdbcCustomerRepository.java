package cat.uvic.teknos.bank.domain.jdbc.repositories;

import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.repositories.CustomerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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

    private void insert(Customer model) {
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO CUSTOMER(ID) VALUES(?)", Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, model.getId());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if(keys.next()){
                model.setId(keys.getInt(1));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void update(Customer model) {
        try(PreparedStatement statement = connection.prepareStatement("UPDATE CUSTOMER VALUES(?)", Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, model.getId());
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

    }

    @Override
    public Customer get(Integer Id) {
        return null;
    }

    @Override
    public Set<Customer> getAll() {
        return Set.of();
    }

    @Override
    public Customer getByName(String name) {
        return null;
    }
}
