package cat.uvic.teknos.bank.domain.jpa.repositories;

import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.repositories.CustomerRepository;

import java.util.Set;

public class JpaCustomerRepository implements CustomerRepository {

    @Override
    public void save(Customer model) {

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
}
