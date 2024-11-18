package cat.uvic.teknos.bank.clients.console;

import cat.uvic.teknos.bank.clients.console.dto.*;
import cat.uvic.teknos.bank.clients.console.exceptions.RequestException;
import cat.uvic.teknos.bank.clients.console.utils.Mappers;
import cat.uvic.teknos.bank.clients.console.utils.RestClient;
import cat.uvic.teknos.bank.clients.console.utils.RestClientImplementation;
import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.models.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Date;
import java.util.stream.Collectors;

public class App {
    private static  final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static  final PrintStream out = new PrintStream(System.out);
    private static RestClient restClient = new RestClientImplementation("localhost", 3007);

    public static void main(String[] args) throws RequestException {
        showBanner();
        showWelcomeMessage();

        var command = "";
        do {
            showMainMenu();
            command = readLine(in);

            switch (command) {
                case "1" -> manageCustomers();
                case "2" -> manageAccounts();
                case "3" -> manageLoans();
                case "4" -> manageTransactions();
                case "5" -> manageWorkers();
            }

        } while (!command.equals("exit"));

        out.println("Bye!");
    }

    private static void manageCustomers() throws RequestException {
        var command = "";
        do {
            out.println("\nWhat would you like to do?\n");
            showMenu();
            command = readLine(in);

            switch (command) {
                case "1" -> {
                    try {
                        var customers = restClient.getAll("/customers", CustomerDto[].class);
                        for (CustomerDto customer : customers) {
                            out.println("Customer ID: " + customer.getId());
                            out.println("Name: " + customer.getFirstName());
                            out.println("Last Name: " + customer.getLastName());
                            out.println("Email: " + customer.getEmail());
                            out.println("Address: " + customer.getAddress());
                        }
                    } catch (RequestException e) {
                        throw new RuntimeException(e);
                    }
                }

                case "2" -> {
                    out.println("Enter Customer ID:");
                    var customerId = readLine(in);
                    try {
                        var customer = restClient.get("/customers/" + customerId, CustomerDto.class);
                        out.println("Customer ID: " + customer.getId());
                        out.println("Name: " + customer.getFirstName());
                        out.println("Last Name: " + customer.getLastName());
                        out.println("Email: " + customer.getEmail());
                        out.println("Address: " + customer.getAddress());
                    } catch (RequestException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "3" -> {
                    var customer = new CustomerDto();

                    out.println("Enter first name:");
                    customer.setFirstName(readLine(in));

                    out.println("Enter last name:");
                    customer.setLastName(readLine(in));

                    out.println("Enter address:");
                    customer.setAddress(readLine(in));

                    out.println("Enter email:");
                    customer.setEmail(readLine(in));

                    try {
                        restClient.post("/customers", Mappers.get().writeValueAsString(customer));
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }
                }
                case "4" -> {
                    out.println("Enter Customer ID to update:");
                    var customerId = readLine(in);
                    var customer = restClient.get("/customers/" + customerId, CustomerDto.class);

                    out.println("Enter first name:");
                    customer.setFirstName(readLine(in));

                    out.println("Enter last name:");
                    customer.setLastName(readLine(in));

                    out.println("Enter address:");
                    customer.setAddress(readLine(in));

                    out.println("Enter email:");
                    customer.setEmail(readLine(in));
                    try {
                        restClient.put("/customers/" + customerId, Mappers.get().writeValueAsString(customer));
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }
                }
                case "5" -> {
                    out.println("Enter Customer ID to delete:");
                    var customerId = readLine(in);
                    try {
                        restClient.delete("/customers/" + customerId, null);
                    } catch (RequestException e) {
                        out.println(e.getMessage());
                    }
                }
            }

        } while (!command.equals("exit"));
    }

    private static void manageAccounts() throws RequestException {
        var command = "";
        do {
            out.println("\nWhat would you like to do?\n");
            showMenu();
            command = readLine(in);

            switch (command) {
                case "1" -> {
                    try {
                        var accounts = restClient.getAll("/accounts", AccountDto[].class);
                        for (AccountDto account : accounts) {
                            out.println("Account ID: " + account.getId());
                            out.println("Account Type: " + account.getAccountType());
                            out.println("Balance: " + account.getBalance());
                        }
                    } catch (RequestException e) {
                        throw new RuntimeException(e);
                    }
                }

                case "2" -> {
                    out.println("Enter Account ID:");
                    var accountId = readLine(in);
                    try {
                        var account = restClient.get("/accounts/" + accountId, AccountDto.class);
                        out.println("Account ID: " + account.getId());
                        out.println("Account Type: " + account.getAccountType());
                        out.println("Balance: " + account.getBalance());
                    } catch (RequestException e) {
                        throw new RuntimeException(e);
                    }
                }

                case "3" -> {
                    var account = new AccountDto();

                    out.println("Enter account type:");
                    account.setAccountType(readLine(in));

                    out.println("Enter balance:");
                    account.setBalance(Integer.parseInt(readLine(in)));

                    try {
                        restClient.post("/accounts", Mappers.get().writeValueAsString(account));
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }
                }

                case "4" -> {
                    out.println("Enter Account ID to update:");
                    var accountId = readLine(in);
                    var account = restClient.get("/accounts/" + accountId, AccountDto.class);

                    out.println("Enter account type:");
                    account.setAccountType(readLine(in));

                    out.println("Enter balance:");
                    account.setBalance(Integer.parseInt(readLine(in)));

                    try {
                        restClient.put("/accounts/" + accountId, Mappers.get().writeValueAsString(account));
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }
                }

                case "5" -> {
                    out.println("Enter Account ID to delete:");
                    var accountId = readLine(in);
                    try {
                        restClient.delete("/accounts/" + accountId, null);
                    } catch (RequestException e) {
                        out.println(e.getMessage());
                    }
                }
            }

        } while (!command.equals("exit"));
    }


    private static void manageLoans() throws RequestException {
        var command = "";
        do {
            out.println("\nWhat would you like to do?\n");
            showMenu();
            command = readLine(in);

            switch (command) {
                case "1" -> {
                    try {
                        var loans = restClient.getAll("/loans", LoanDto[].class);
                        for (LoanDto loan : loans) {
                            out.println("Loan ID: " + loan.getId());
                            out.println("Loan Date: " + loan.getLoanDate());
                            out.println("Return Date: " + loan.getReturnDate());
                            out.println("Customer ID: " + loan.getCustomer().getId());
                            out.println("Customer Name: " + loan.getCustomer().getFirstName() + " " + loan.getCustomer().getLastName());
                        }
                    } catch (RequestException e) {
                        out.println(e.getMessage());
                    }
                }

                case "2" -> { // Get loan by ID
                    out.println("Enter Loan ID:");
                    var loanId = Integer.parseInt(readLine(in));
                    try {
                        var loan = restClient.get("/loans/" + loanId, LoanDto.class);
                        out.println("Loan ID: " + loan.getCustomer().getId());
                        out.println("Loan Date: " + loan.getLoanDate());
                        out.println("Return Date: " + loan.getReturnDate());

                    } catch (RequestException e) {
                        out.println(e.getMessage());
                    }
                }

                case "3" -> { // Create a new loan
                    var loan = new LoanDto();

                    out.println("Enter Customer ID for this loan:");
                    var customerId = Integer.parseInt(readLine(in));
                    try {
                        // Fetch the customer to associate with the loan
                        var customer = restClient.get("/customers/" + customerId, CustomerDto.class);
                        loan.setCustomer(customer);
                    } catch (RequestException e) {
                        out.println("Customer not found: " + e.getMessage());
                        break;
                    }

                    out.println("Enter Loan Date (YYYY-MM-DD):");
                    loan.setLoanDate(Date.valueOf(readLine(in)));

                    out.println("Enter Return Date (YYYY-MM-DD):");
                    loan.setReturnDate(Date.valueOf(readLine(in)));

                    try {
                        restClient.post("/loans", Mappers.get().writeValueAsString(loan));
                        out.println("Loan created successfully!");
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }
                }

                case "4" -> { // Update an existing loan
                    out.println("Enter Loan ID to update:");
                    var loanId = Integer.parseInt(readLine(in));
                    LoanDto loan;
                    try {
                        loan = restClient.get("/loans/" + loanId, LoanDto.class);
                    } catch (RequestException e) {
                        out.println("Loan not found: " + e.getMessage());
                        break;
                    }

                    out.println("Enter new Loan Date (YYYY-MM-DD):");
                    loan.setLoanDate(Date.valueOf(readLine(in)));

                    out.println("Enter new Return Date (YYYY-MM-DD):");
                    loan.setReturnDate(Date.valueOf(readLine(in)));

                    try {
                        restClient.put("/loans/" + loanId, Mappers.get().writeValueAsString(loan));
                        out.println("Loan updated successfully!");
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }
                }

                case "5" -> { // Delete loan
                    out.println("Enter Loan ID to delete:");
                    var loanId = Integer.parseInt(readLine(in));
                    try {
                        restClient.delete("/loans/" + loanId, null);
                        out.println("Loan deleted successfully!");
                    } catch (RequestException e) {
                        out.println(e.getMessage());
                    }
                }
            }

        } while (!command.equals("exit"));
    }


    private static void manageTransactions() throws RequestException {
        var command = "";
        do {
            out.println("\nWhat would you like to do?\n");
            showMenu();
            command = readLine(in);

            switch (command) {
                case "1" -> {
                    try {
                        var transactions = restClient.getAll("/transactions", TransactionDto[].class);
                        for (TransactionDto transaction : transactions) {
                            out.println("Transaction ID: " + transaction.getId());
                            out.println("Transaction Type: " + transaction.getTransactionType());
                            out.println("Amount: " + transaction.getAmount());
                            out.println("Date: " + transaction.getTransactionDate());
                            out.println("Customer ID: " + (transaction.getCustomer().getId()));

                        }
                    } catch (RequestException e) {
                        throw new RuntimeException(e);
                    }
                }

                case "2" -> {
                    out.println("Enter Transaction ID:");
                    var transactionId = readLine(in);
                    try {
                        var transaction = restClient.get("/transactions/" + transactionId, TransactionDto.class);
                        out.println("Transaction ID: " + transaction.getId());
                        out.println("Transaction Type: " + transaction.getTransactionType());
                        out.println("Amount: " + transaction.getAmount());
                        out.println("Date: " + transaction.getTransactionDate());
                        out.println("Customer ID: " + (transaction.getCustomer().getId()));
                    } catch (RequestException e) {
                        throw new RuntimeException(e);
                    }
                }

                case "3" -> {

                    var transaction = new TransactionDto();

                    out.println("Enter transaction type:");
                    transaction.setTransactionType(readLine(in));

                    out.println("Enter amount:");
                    transaction.setAmount(Integer.parseInt(readLine(in)));

                    out.println("Enter transaction date (yyyy-mm-dd):");
                    transaction.setTransactionDate(Date.valueOf(readLine(in)));

                    /*out.println("Enter Customer ID:");
                    var customerId = readLine(in);
                    transaction.setCustomer(customerId);*/

                    try {
                        restClient.post("/transactions", Mappers.get().writeValueAsString(transaction));
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }
                }

                case "4" -> {

                    out.println("Enter Transaction ID to update:");
                    var transactionId = readLine(in);
                    var transaction = restClient.get("/transactions/" + transactionId, TransactionDto.class);

                    out.println("Enter transaction type:");
                    transaction.setTransactionType(readLine(in));

                    out.println("Enter amount:");
                    transaction.setAmount(Integer.parseInt(readLine(in)));

                    out.println("Enter transaction date (yyyy-mm-dd):");
                    transaction.setTransactionDate(Date.valueOf(readLine(in)));

                    try {
                        restClient.put("/transactions/" + transactionId, Mappers.get().writeValueAsString(transaction));
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }
                }

                case "5" -> {
                    out.println("Enter Transaction ID to delete:");
                    var transactionId = readLine(in);
                    try {
                        restClient.delete("/transactions/" + transactionId, null);
                    } catch (RequestException e) {
                        out.println(e.getMessage());
                    }
                }
            }

        } while (!command.equals("exit"));
    }


    private static void manageWorkers() throws RequestException {
        var command = "";
        do {
            out.println("\nWhat would you like to do?\n");
            showMenu();
            command = readLine(in);

            switch (command) {
                case "1" -> {
                    try {
                        var workers = restClient.getAll("/workers", WorkerDto[].class);
                        for (WorkerDto worker : workers) {
                            out.println("Worker ID: " + worker.getId());
                            out.println("First Name: " + worker.getFirstName());
                            out.println("Last Name: " + worker.getLastName());
                            out.println("Transactions:");
                            if (worker.getTransaction() != null) {
                                for (Transaction transaction : worker.getTransaction()) {
                                    out.println("Transaction ID: " + transaction.getId());
                                    out.println("Type: " + transaction.getTransactionType());
                                    out.println("Amount: " + transaction.getAmount());
                                    out.println("Date: " + transaction.getTransactionDate());
                                }
                            }
                        }
                    } catch (RequestException e) {
                        throw new RuntimeException(e);
                    }
                }

                case "2" -> {

                    out.println("Enter Worker ID:");
                    var workerId = readLine(in);
                    try {
                        var worker = restClient.get("/workers/" + workerId, WorkerDto.class);
                        out.println("Worker ID: " + worker.getId());
                        out.println("First Name: " + worker.getFirstName());
                        out.println("Last Name: " + worker.getLastName());
                        out.println("Transactions:");
                        if (worker.getTransaction() != null) {
                            for (Transaction transaction : worker.getTransaction()) {
                                out.println("Transaction ID: " + transaction.getId());
                                out.println("Type: " + transaction.getTransactionType());
                                out.println("Amount: " + transaction.getAmount());
                                out.println("Date: " + transaction.getTransactionDate());
                            }
                        }
                    } catch (RequestException e) {
                        throw new RuntimeException(e);
                    }
                }

                case "3" -> {
                    var worker = new WorkerDto();

                    out.println("Enter first name:");
                    worker.setFirstName(readLine(in));

                    out.println("Enter last name:");
                    worker.setLastName(readLine(in));

                    try {
                        restClient.post("/workers", Mappers.get().writeValueAsString(worker));
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }
                }

                case "4" -> {

                    out.println("Enter Worker ID to update:");
                    var workerId = readLine(in);
                    var worker = restClient.get("/workers/" + workerId, WorkerDto.class);

                    out.println("Enter first name:");
                    worker.setFirstName(readLine(in));

                    out.println("Enter last name:");
                    worker.setLastName(readLine(in));

                    try {
                        restClient.put("/workers/" + workerId, Mappers.get().writeValueAsString(worker));
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }
                }

                case "5" -> {
                    out.println("Enter Worker ID to delete:");
                    var workerId = readLine(in);
                    try {
                        restClient.delete("/workers/" + workerId, null);
                    } catch (RequestException e) {
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

    private static void showWelcomeMessage() {
        out.println("\nWelcome to the Bank BackOffice!");
        out.println("Select a menu option or type 'exit' to exit the application:\n");
    }

    private static void showMainMenu() {
        out.println("1. Customer");
        out.println("2. Account");
        out.println("3. Loan");
        out.println("4. Transaction");
        out.println("5. Worker");
    }

    private static void showMenu() {
        out.println("1. Get All");
        out.println("2. Get");
        out.println("3. Post");
        out.println("4. Put");
        out.println("5. Delete");
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
