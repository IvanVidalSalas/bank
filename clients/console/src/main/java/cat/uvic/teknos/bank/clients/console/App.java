package cat.uvic.teknos.bank.clients.console;

import cat.uvic.teknos.bank.clients.console.dto.CustomerDto;
import cat.uvic.teknos.bank.clients.console.exceptions.RequestException;
import cat.uvic.teknos.bank.clients.console.utils.Mappers;
import cat.uvic.teknos.bank.clients.console.utils.RestClient;
import cat.uvic.teknos.bank.clients.console.utils.RestClientImplementation;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.stream.Collectors;

public class App {
    private static  final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static  final PrintStream out = new PrintStream(System.out);
    private static RestClient restClient = new RestClientImplementation("localhost", 88888);

    public static void main(String[] args) {
        showBanner();
        showMainMenu();

        var command = "";
        do {
            showMainMenu();
            command = readLine(in);

            switch (command) {
                case "2" -> manageCustomers();
            }

        } while (!command.equals("exit"));

        out.println("Bye!");
    }

    private static void manageCustomers() {
        var command = "";
        do {
            command = readLine(in);

            switch (command) {
                case "1" -> {
                    try {
                        var clients = restClient.getAll("/customers", CustomerDto[].class);
                    } catch (RequestException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "2" -> {
                    var customerId = readLine(in);
                    try {
                        var client = restClient.get("/customers/" + customerId, CustomerDto.class);
                    } catch (RequestException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "3" -> {

                    var customer = new CustomerDto();
                    customer.setFirstName(readLine(in));

                    try {
                        restClient.post("/customers", Mappers.get().writeValueAsString(customer));
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }

                }
            }

        } while (!command.equals("exit"));
    }

    private static void showBanner() {
        var bannerStream = App.class.getResourceAsStream("/banner.txt");

        var banner = new BufferedReader(new InputStreamReader(bannerStream))
                .lines().collect(Collectors.joining("\n"));

        System.out.println(banner);
    }

    private static void showMainMenu() {
        out.println("1. Customer");
        out.println("2. Account");
        out.println("3. Loan");
        out.println("4. Transaction");
        out.println("5. Worker");
    }

    private static String readLine(BufferedReader in) {
        String command;
        try {
            command = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Error while reading the menu option", e);
        }
        return command;
    }
}
