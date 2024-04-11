package cat.uvic.teknos.bank.repositories;

import cat.uvic.teknos.bank.models.Customer;

import java.util.Set;

public interface CustomerRepository extends Repository<Integer, Customer> {
    @Override
    void save(Customer model);

    @Override
    void delete(Customer model);

    @Override
    Customer get(Integer Id);

    @Override
    Set<Customer> getAll();

    Customer getByName(String name);
}
