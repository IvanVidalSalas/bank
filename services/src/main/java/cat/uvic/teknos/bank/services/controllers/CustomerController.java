package cat.uvic.teknos.bank.services.controllers;

import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.models.ModelFactory;
import cat.uvic.teknos.bank.repositories.CustomerRepository;
import cat.uvic.teknos.bank.repositories.RepositoryFactory;
import org.gradle.internal.impldep.com.fasterxml.jackson.core.JsonProcessingException;
import org.gradle.internal.impldep.com.fasterxml.jackson.databind.ObjectMapper;

public class CustomerController implements Controller<Integer, Customer> {

    private RepositoryFactory repositoryFactory;
    private ModelFactory modelFactory ;

    public CustomerController(RepositoryFactory repositoryFactory, ModelFactory modelFactory) {
        this.repositoryFactory = repositoryFactory;
        this.modelFactory = modelFactory;
    }

    @Override
    public void delete(Integer key) {

    }

    @Override
    public void put(Integer key, Customer value) {

    }

    @Override
    public void post(Customer value) {
        //afegir customer
        repositoryFactory.getCustomerRepository().save(value);
    }

    @Override
    public String get() {

        var customers = repositoryFactory.getCustomerRepository().getAll();

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(customers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        /*var customers = customerRepository.getAll();
        var objectMapper = new ObjectMapper();
        yield objectMapper.writeValueAsString(customers);*/
    }

    @Override
    public String get(Integer integer) {

        //retrieve (get) student from db
        // serialize student in json format
        return ""; //json
    }
}
