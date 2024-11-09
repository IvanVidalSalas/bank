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
        var restClient = new RestClientImplementation("localhost", 8888);
        try {
            Customer customer = restClient.get("customers/1", CustomerDto.class);

            assertNotNull(customer);
        } catch (RequestException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAllTest() {
        var restClient = new RestClientImplementation("localhost", 8888);
        try {
            Customer[] customers = restClient.getAll("customers", CustomerDto[].class);

            assertNotNull(customers);
        } catch (RequestException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void postTest() {
        var restClient = new RestClientImplementation("localhost", 8888);
        try {
            var customer = new CustomerDto();
            customer.setFirstName("Test");
            customer.setLastName("Doe");

            restClient.post("customers", Mappers.get().writeValueAsString(customer));

        } catch (RequestException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}