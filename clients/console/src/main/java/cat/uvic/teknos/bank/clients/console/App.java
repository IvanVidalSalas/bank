package cat.uvic.teknos.bank.clients.console;

import cat.uvic.teknos.bank.clients.console.dto.*;
import cat.uvic.teknos.bank.clients.console.exceptions.ConsoleClientException;
import cat.uvic.teknos.bank.clients.console.exceptions.RequestException;
import cat.uvic.teknos.bank.clients.console.utils.Mappers;
import cat.uvic.teknos.bank.clients.console.utils.RestClient;
import cat.uvic.teknos.bank.clients.console.utils.RestClientImplementation;
import cat.uvic.teknos.bank.models.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.a7.A7_Grids;
import de.vandermeer.skb.interfaces.document.TableRowStyle;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Date;
import java.util.stream.Collectors;

public class App {

    private static  final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static  final PrintStream out = new PrintStream(System.out);
    private static final RestClient restClient = new RestClientImplementation("localhost", 3007);

    public static void main(String[] args) throws RequestException {
        showBanner();
        showWelcomeMessage();

        var command = "";
        do {
            showMainMenu();
            command = readLine(in);

            if (command.equals("exit")) {
                break;
            }

            switch (command) {
                case "1" -> manageCustomers();
                case "2" -> manageAccounts();
                case "3" -> manageLoans();
                case "4" -> manageTransactions();
                case "5" -> manageWorkers();
                default -> out.println("\nWrite a correct option.\n");
            }

        } while (true);

        out.println("\nSee you soon!");
    }

    private static void manageCustomers() throws RequestException {
        var command = "";
        do {
            out.println("\nChoose an option or type 'exit' to go back\n");
            showMenu();
            command = readLine(in);

            switch (command) {
                case "1" -> {
                    try {
                        var customers = restClient.getAll("/customer", CustomerDto[].class);

                        var asciiTable = new AsciiTable();
                        asciiTable.addRule();
                        asciiTable.addRow("Id", "First name", "Last name", "Address", "Email");
                        asciiTable.addRule(TableRowStyle.STRONG);

                        for (CustomerDto customer : customers) {
                            asciiTable.addRow(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getAddress(), customer.getEmail());
                            asciiTable.addRule();
                        }

                        asciiTable.setTextAlignment(TextAlignment.CENTER);
                        asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals());
                        String render = asciiTable.render();
                        out.println(render);

                    } catch (RequestException e) {
                        throw new RuntimeException(e);
                    }
                }

                case "2" -> {
                    out.println("Enter Customer ID:");
                    var customerId = readLine(in);

                    try {
                        var customer = restClient.get("/customer/" + customerId, CustomerDto.class);

                        // Initialize AsciiTable and configure it
                        var asciiTable = new AsciiTable();
                        asciiTable.addRule();
                        asciiTable.addRow("Customer ID", "First Name", "Last Name", "Address", "Email");
                        asciiTable.addRule(TableRowStyle.STRONG);

                        // Add the customer data to the table
                        asciiTable.addRow(
                                customer.getId(),
                                customer.getFirstName(),
                                customer.getLastName(),
                                customer.getAddress(),
                                customer.getEmail()
                        );
                        asciiTable.addRule();

                        // Set table styling and render
                        asciiTable.setTextAlignment(TextAlignment.CENTER);
                        asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals());
                        String render = asciiTable.render();
                        out.println(render);

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
                        restClient.post("/customer", Mappers.get().writeValueAsString(customer));
                        out.println("\nCustomer created!");
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }
                }
                case "4" -> {
                    out.println("Enter Customer ID to update:");
                    var customerId = readLine(in);
                    var customer = restClient.get("/customer/" + customerId, CustomerDto.class);

                    out.println("Enter first name:");
                    customer.setFirstName(readLine(in));

                    out.println("Enter last name:");
                    customer.setLastName(readLine(in));

                    out.println("Enter address:");
                    customer.setAddress(readLine(in));

                    out.println("Enter email:");
                    customer.setEmail(readLine(in));
                    try {
                        restClient.put("/customer/" + customerId, Mappers.get().writeValueAsString(customer));
                        out.println("\nCustomer updated!");
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }
                }
                case "5" -> {
                    out.println("Enter Customer ID to delete:");
                    var customerId = readLine(in);
                    try {
                        restClient.delete("/customer/" + customerId);
                        out.println("\nCustomer deleted!");
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
            out.println("\nChoose an option or type 'exit' to go back\n");
            showMenu();
            command = readLine(in);

            switch (command) {
                case "1" -> {
                    try {
                        var accounts = restClient.getAll("/account", AccountDto[].class);

                        // Initialize AsciiTable and configure it
                        var asciiTable = new AsciiTable();
                        asciiTable.addRule();
                        asciiTable.addRow("Account ID", "Customer ID", "Account Type", "Balance");
                        asciiTable.addRule(TableRowStyle.STRONG);

                        // Add account data to the table
                        for (AccountDto account : accounts) {
                            asciiTable.addRow(
                                    account.getId(),
                                    account.getCustomer().getId(),
                                    account.getAccountType(),
                                    account.getBalance()
                            );
                            asciiTable.addRule();
                        }

                        // Set table styling and render
                        asciiTable.setTextAlignment(TextAlignment.CENTER);
                        asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals());
                        String render = asciiTable.render();
                        out.println(render);

                    } catch (RequestException e) {
                        throw new RuntimeException(e);
                    }
                }

                case "2" -> {
                    out.println("Enter Account ID:");
                    var accountId = readLine(in);

                    try {
                        var account = restClient.get("/account/" + accountId, AccountDto.class);

                        // Initialize AsciiTable and configure it
                        var asciiTable = new AsciiTable();
                        asciiTable.addRule();
                        asciiTable.addRow("Account ID", "Customer ID", "Account Type", "Balance");
                        asciiTable.addRule(TableRowStyle.STRONG);

                        // Add the account data to the table
                        asciiTable.addRow(
                                account.getId(),
                                account.getCustomer().getId(),
                                account.getAccountType(),
                                account.getBalance()
                        );
                        asciiTable.addRule();

                        // Set table styling and render
                        asciiTable.setTextAlignment(TextAlignment.CENTER);
                        asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals());
                        String render = asciiTable.render();
                        out.println(render);

                    } catch (RequestException e) {
                        throw new RuntimeException(e);
                    }
                }

                case "3" -> {
                    var account = new AccountDto();

                    out.println("Enter Customer ID:");
                    int customerId = Integer.parseInt(readLine(in));
                    var customer = new CustomerDto();
                    customer.setId(customerId);
                    account.setCustomer(customer);

                    out.println("Enter account type:");
                    account.setAccountType(readLine(in));

                    out.println("Enter balance:");
                    account.setBalance(Integer.parseInt(readLine(in)));

                    try {
                        restClient.post("/account", Mappers.get().writeValueAsString(account));
                        out.println("\nAccount created!");
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }
                }

                case "4" -> {
                    out.println("Enter Account ID to update:");
                    var accountId = readLine(in);
                    var account = restClient.get("/account/" + accountId, AccountDto.class);

                    out.println("Enter Customer ID:");
                    int customerId = Integer.parseInt(readLine(in));
                    var customer = new CustomerDto();
                    customer.setId(customerId);
                    account.setCustomer(customer);

                    out.println("Enter account type:");
                    account.setAccountType(readLine(in));

                    out.println("Enter balance:");
                    account.setBalance(Integer.parseInt(readLine(in)));

                    try {
                        restClient.put("/account/" + accountId, Mappers.get().writeValueAsString(account));
                        out.println("\nAccount updated!");
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }
                }

                case "5" -> {
                    out.println("Enter Account ID to delete:");
                    var accountId = readLine(in);
                    try {
                        restClient.delete("/account/" + accountId);
                        out.println("\nAccount deleted!");
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
            out.println("\nChoose an option or type 'exit' to go back\n");
            showMenu();
            command = readLine(in);

            switch (command) {
                case "1" -> {
                    try {
                        var loans = restClient.getAll("/loan", LoanDto[].class);

                        // Initialize AsciiTable and configure it
                        var asciiTable = new AsciiTable();
                        asciiTable.addRule();
                        asciiTable.addRow("Loan ID", "Loan Date", "Return Date");
                        asciiTable.addRule(TableRowStyle.STRONG);

                        // Add loan data to the table
                        for (LoanDto loan : loans) {
                            asciiTable.addRow(
                                    loan.getCustomer().getId(),
                                    loan.getLoanDate(),
                                    loan.getReturnDate()
                            );
                            asciiTable.addRule();
                        }

                        // Set table styling and render
                        asciiTable.setTextAlignment(TextAlignment.CENTER);
                        asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals());
                        String render = asciiTable.render();
                        out.println(render);

                    } catch (RequestException e) {
                        throw new ConsoleClientException(e);
                    }
                }

                case "2" -> { // Get loan by ID
                    out.println("Enter Loan ID (Customer ID):");
                    var loanId = Integer.parseInt(readLine(in));

                    try {
                        var loan = restClient.get("/loan/" + loanId, LoanDto.class);

                        // Initialize AsciiTable and configure it
                        var asciiTable = new AsciiTable();
                        asciiTable.addRule();
                        asciiTable.addRow("Loan ID", "Loan Date", "Return Date");
                        asciiTable.addRule(TableRowStyle.STRONG);

                        // Add the loan data to the table
                        asciiTable.addRow(
                                loan.getCustomer().getId(),
                                loan.getLoanDate(),
                                loan.getReturnDate()
                        );
                        asciiTable.addRule();

                        // Set table styling and render
                        asciiTable.setTextAlignment(TextAlignment.CENTER);
                        asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals());
                        String render = asciiTable.render();
                        out.println(render);

                    } catch (RequestException e) {
                        out.println(e.getMessage());
                    }
                }

                case "3" -> {
                    // Create a new loan
                    var loan = new LoanDto();

                    out.println("Enter Loan ID (Customer ID) for the new loan:");
                    int customerId = Integer.parseInt(readLine(in));
                    var customer = new CustomerDto();
                    customer.setId(customerId);
                    loan.setCustomer(customer);

                    out.println("Enter Loan Date (YYYY-MM-DD):");
                    loan.setLoanDate(Date.valueOf(readLine(in)));

                    out.println("Enter Return Date (YYYY-MM-DD):");
                    loan.setReturnDate(Date.valueOf(readLine(in)));

                    try {
                        restClient.post("/loan", Mappers.get().writeValueAsString(loan));
                        out.println("\nLoan created!");
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }
                }

                case "4" -> { // Update an existing loan

                    out.println("Enter Loan ID (Customer ID) to update:");
                    var loanId = Integer.parseInt(readLine(in));
                    var loan = restClient.get("/loan/" + loanId, LoanDto.class);

                    out.println("Enter new Loan Date (YYYY-MM-DD):");
                    loan.setLoanDate(Date.valueOf(readLine(in)));

                    out.println("Enter new Return Date (YYYY-MM-DD):");
                    loan.setReturnDate(Date.valueOf(readLine(in)));

                    try {
                        restClient.put("/loan/" + loanId, Mappers.get().writeValueAsString(loan));
                        out.println("\nLoan updated!");
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }
                }

                case "5" -> { // Delete loan
                    out.println("Enter Loan ID (Customer ID) to delete:");
                    var loanId = Integer.parseInt(readLine(in));
                    try {
                        restClient.delete("/loan/" + loanId);
                        out.println("\nLoan deleted!");
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
            out.println("\nChoose an option or type 'exit' to go back\n");
            showMenu();
            command = readLine(in);

            switch (command) {
                case "1" -> {
                    try {
                        var transactions = restClient.getAll("/transaction", TransactionDto[].class);

                        // Initialize AsciiTable and configure it
                        var asciiTable = new AsciiTable();
                        asciiTable.addRule();
                        asciiTable.addRow("Transaction ID", "Customer ID", "Transaction Type", "Amount", "Date");
                        asciiTable.addRule(TableRowStyle.STRONG);

                        // Add transaction data to the table
                        for (TransactionDto transaction : transactions) {
                            asciiTable.addRow(
                                    transaction.getId(),
                                    transaction.getCustomer().getId(),
                                    transaction.getTransactionType(),
                                    transaction.getAmount(),
                                    transaction.getTransactionDate()
                            );
                            asciiTable.addRule();
                        }

                        // Set table styling and render
                        asciiTable.setTextAlignment(TextAlignment.CENTER);
                        asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals());
                        String render = asciiTable.render();
                        out.println(render);

                    } catch (RequestException e) {
                        throw new RuntimeException(e);
                    }
                }

                case "2" -> {
                    out.println("Enter Transaction ID:");
                    var transactionId = readLine(in);

                    try {
                        var transaction = restClient.get("/transaction/" + transactionId, TransactionDto.class);

                        // Initialize AsciiTable and configure it
                        var asciiTable = new AsciiTable();
                        asciiTable.addRule();
                        asciiTable.addRow("Transaction ID", "Customer ID", "Transaction Type", "Amount", "Date");
                        asciiTable.addRule(TableRowStyle.STRONG);

                        // Add the transaction data to the table
                        asciiTable.addRow(
                                transaction.getId(),
                                transaction.getCustomer().getId(),
                                transaction.getTransactionType(),
                                transaction.getAmount(),
                                transaction.getTransactionDate()
                        );
                        asciiTable.addRule();

                        // Set table styling and render
                        asciiTable.setTextAlignment(TextAlignment.CENTER);
                        asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals());
                        String render = asciiTable.render();
                        out.println(render);

                    } catch (RequestException e) {
                        throw new RuntimeException(e);
                    }
                }

                case "3" -> {

                    var transaction = new TransactionDto();

                    out.println("Enter Customer ID:");
                    int customerId = Integer.parseInt(readLine(in));
                    var customer = new CustomerDto();
                    customer.setId(customerId);
                    transaction.setCustomer(customer);

                    out.println("Enter transaction type:");
                    transaction.setTransactionType(readLine(in));

                    out.println("Enter amount:");
                    transaction.setAmount(Integer.parseInt(readLine(in)));

                    out.println("Enter transaction date (YYYY-MM-DD):");
                    transaction.setTransactionDate(Date.valueOf(readLine(in)));

                    try {
                        restClient.post("/transaction", Mappers.get().writeValueAsString(transaction));
                        out.println("\nTransaction created!");
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }
                }

                case "4" -> {

                    out.println("Enter Transaction ID to update:");
                    var transactionId = readLine(in);
                    var transaction = restClient.get("/transaction/" + transactionId, TransactionDto.class);

                    out.println("Enter Customer ID:");
                    int customerId = Integer.parseInt(readLine(in));
                    var customer = new CustomerDto();
                    customer.setId(customerId);
                    transaction.setCustomer(customer);

                    out.println("Enter transaction type:");
                    transaction.setTransactionType(readLine(in));

                    out.println("Enter amount:");
                    transaction.setAmount(Integer.parseInt(readLine(in)));

                    out.println("Enter transaction date (YYYY-MM-DD):");
                    transaction.setTransactionDate(Date.valueOf(readLine(in)));

                    try {
                        restClient.put("/transaction/" + transactionId, Mappers.get().writeValueAsString(transaction));
                        out.println("\nTransaction updated!");
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }
                }

                case "5" -> {
                    out.println("Enter Transaction ID to delete:");
                    var transactionId = readLine(in);
                    try {
                        restClient.delete("/transaction/" + transactionId);
                        out.println("\nTransaction deleted!");
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
            out.println("\nChoose an option or type 'exit' to go back\n");
            showMenu();
            command = readLine(in);

            switch (command) {
                case "1" -> {
                    try {
                        var workers = restClient.getAll("/worker", WorkerDto[].class);

                        // Initialize AsciiTable and configure it
                        var asciiTable = new AsciiTable();
                        asciiTable.addRule();
                        asciiTable.addRow("Worker ID", "Transaction IDs", "First Name", "Last Name");
                        asciiTable.addRule(TableRowStyle.STRONG);

                        // Add worker data to the table
                        for (WorkerDto worker : workers) {
                            // Gather transaction IDs as a single comma-separated string
                            String transactionIds = worker.getTransaction() != null
                                    ? worker.getTransaction().stream()
                                    .map(Transaction::getId)
                                    .map(String::valueOf)
                                    .collect(Collectors.joining(", "))
                                    : "None";

                            // Add a row for the worker
                            asciiTable.addRow(
                                    worker.getId(),
                                    transactionIds,
                                    worker.getFirstName(),
                                    worker.getLastName()
                            );
                            asciiTable.addRule();
                        }

                        // Set table styling and render
                        asciiTable.setTextAlignment(TextAlignment.CENTER);
                        asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals());
                        String render = asciiTable.render();
                        out.println(render);

                    } catch (RequestException e) {
                        throw new RuntimeException(e);
                    }
                }

                case "2" -> {
                    out.println("Enter Worker ID:");
                    var workerId = readLine(in);

                    try {
                        var worker = restClient.get("/worker/" + workerId, WorkerDto.class);

                        // Initialize AsciiTable and configure it
                        var asciiTable = new AsciiTable();
                        asciiTable.addRule();
                        asciiTable.addRow("Worker ID", "First Name", "Last Name", "Transaction IDs");
                        asciiTable.addRule(TableRowStyle.STRONG);

                        // Prepare transaction IDs as a comma-separated string
                        String transactionIds = worker.getTransaction() != null
                                ? worker.getTransaction().stream()
                                .map(Transaction::getId)
                                .map(Object::toString)
                                .collect(Collectors.joining(", "))
                                : "None";

                        // Add the worker's data to the table
                        asciiTable.addRow(
                                worker.getId(),
                                worker.getFirstName(),
                                worker.getLastName(),
                                transactionIds
                        );
                        asciiTable.addRule();

                        // Set table styling and render
                        asciiTable.setTextAlignment(TextAlignment.CENTER);
                        asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals());
                        String render = asciiTable.render();
                        out.println(render);

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
                        restClient.post("/worker", Mappers.get().writeValueAsString(worker));
                        out.println("\nWorker created!");
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }
                }

                case "4" -> {

                    out.println("Enter Worker ID to update:");
                    var workerId = readLine(in);
                    var worker = restClient.get("/worker/" + workerId, WorkerDto.class);


                    out.println("Enter first name:");
                    worker.setFirstName(readLine(in));

                    out.println("Enter last name:");
                    worker.setLastName(readLine(in));

                    try {
                        restClient.put("/worker/" + workerId, Mappers.get().writeValueAsString(worker));
                        out.println("\nWorker updated!");
                    } catch (RequestException | JsonProcessingException e) {
                        out.println(e.getMessage());
                    }
                }

                case "5" -> {
                    out.println("Enter Worker ID to delete:");
                    var workerId = readLine(in);
                    try {
                        restClient.delete("/worker/" + workerId);
                        out.println("\nWorker deleted!");
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
