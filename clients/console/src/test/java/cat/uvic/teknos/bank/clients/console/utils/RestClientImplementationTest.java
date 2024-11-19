package cat.uvic.teknos.bank.clients.console.utils;

import cat.uvic.teknos.bank.clients.console.dto.CustomerDto;
import cat.uvic.teknos.bank.clients.console.exceptions.RequestException;
import cat.uvic.teknos.bank.models.Customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RestClientImplementationTest {

    @Test
    void getTest() {
        var restClient = new RestClientImplementation("localhost", 3007);
        try {
            Customer customer = restClient.get("customer/1", CustomerDto.class);
            assertNotNull(customer);

            customer.getId();
            customer.getFirstName();
            customer.getLastName();
            customer.getAddress();
            customer.getEmail();

        } catch (RequestException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAllTest() {
        var restClient = new RestClientImplementation("localhost", 3007);
        try {
            Customer[] customers = restClient.getAll("customer", CustomerDto[].class);

            assertNotNull(customers);

            for (Customer customer : customers) {
                customer.getId();
                customer.getFirstName();
                customer.getLastName();
                customer.getAddress();
                customer.getEmail();
            }

        } catch (RequestException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void postTest() {
        var restClient = new RestClientImplementation("localhost", 3007);
        try {
            var customer = new CustomerDto();
            customer.setFirstName("Fede");
            customer.setLastName("Kennedy");
            customer.setAddress("Main Street");
            customer.setEmail("Fede@gmail.com");
            restClient.post("customer", Mappers.get().writeValueAsString(customer));

        } catch (RequestException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void putTest() {
        var restClient = new RestClientImplementation("localhost", 3007);
        try {

            var customer = new CustomerDto();
            customer.setId(1);
            customer.setFirstName("UpdatedFede");
            customer.setLastName("UpdatedKennedy");
            customer.setAddress("Updated Main Street");
            customer.setEmail("UpdatedFede@gmail.com");

            restClient.put("customer/1", Mappers.get().writeValueAsString(customer));

        } catch (RequestException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteTest() {
        var restClient = new RestClientImplementation("localhost", 3007);
        try {
            restClient.delete("customer/1");

        } catch (RequestException e) {
            throw new RuntimeException(e);
        }
    }
}