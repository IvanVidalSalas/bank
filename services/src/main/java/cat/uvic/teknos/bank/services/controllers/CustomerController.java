package cat.uvic.teknos.bank.services.controllers;

import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.models.ModelFactory;
import cat.uvic.teknos.bank.repositories.CustomerRepository;
import cat.uvic.teknos.bank.repositories.RepositoryFactory;
import cat.uvic.teknos.bank.services.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomerController implements Controller {
    private RepositoryFactory repositoryFactory;
    private ModelFactory modelFactory;
    private ObjectMapper mapper = new ObjectMapper();
    private CustomerRepository customerRepository;

    public CustomerController(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
        //this.modelFactory = modelFactory;
        this.customerRepository = repositoryFactory.getCustomerRepository();
    }

    @Override
    public void delete(int id) {
        Customer customer = customerRepository.get(id);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer" + id + " not found");
        }
        customerRepository.delete(customer);
    }

    @Override
    public void put(int id, String json) {
        Customer customer = customerRepository.get(id);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer " + id + " not found");
        }
        try {
            // Deserialize JSON to Customer object
            Customer updatedCustomer = mapper.readValue(json, Customer.class);

            customer.setFirstName(updatedCustomer.getFirstName());
            customer.setLastName(updatedCustomer.getLastName());
            customer.setAddress(updatedCustomer.getAddress());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setLoan(updatedCustomer.getLoan());
            customer.setAccount(updatedCustomer.getAccount());
            customer.setTransaction(updatedCustomer.getTransaction());

            customerRepository.save(customer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse customer JSON", e);
        }
    }

    @Override
    public void post(String json) {
        try {
            // Deserialize JSON to Customer object
            Customer newCustomer = mapper.readValue(json, Customer.class);
            customerRepository.save(newCustomer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse customer JSON", e);
        }
    }

    @Override
    public String get() {
        var customers = customerRepository.getAll();
        try {
            return mapper.writeValueAsString(customers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize customers to JSON", e);
        }
    }

    @Override
    public String get(int id) {
        Customer customer = customerRepository.get(id);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer " + id + " not found");
        }
        try {
            return mapper.writeValueAsString(customer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize customer to JSON", e);
        }
    }
}
