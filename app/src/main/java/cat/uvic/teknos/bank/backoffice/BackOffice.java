package cat.uvic.teknos.bank.backoffice;

import cat.uvic.teknos.bank.models.ModelFactory;
import cat.uvic.teknos.bank.repositories.RepositoryFactory;

import java.io.*;

import static cat.uvic.teknos.bank.backoffice.IOUtils.*;

public class BackOffice {
    private final BufferedReader in;
    private final PrintStream out;
    private final RepositoryFactory repositoryFactory;
    private final ModelFactory modelFactory;

    public BackOffice (InputStream inputStream, OutputStream outputStream, RepositoryFactory repositoryFactory, ModelFactory modelFactory) {
        this.in = new BufferedReader(new InputStreamReader(inputStream));
        this.out = new PrintStream(outputStream);
        this.repositoryFactory = repositoryFactory;
        this.modelFactory = modelFactory;

    }
    public void start () {
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
                default -> out.println("Invalid option. Please try again.");
            }
        }
        while (!command.equals("exit"));
        out.println("See you soon!");
    }

    private void manageCustomers() {
        new CustomersManager(in, out, repositoryFactory.getCustomerRepository(), modelFactory).start();
    }
    private void manageAccounts() {
        new AccountsManager(in, out, repositoryFactory.getAccountRepository(), modelFactory).start();
    }
    private void manageLoans() {
        new LoansManager(in, out, repositoryFactory.getLoanRepository(), modelFactory).start();
    }
    private void manageTransactions() {
        new TransactionsManager(in, out, repositoryFactory.getTransactionRepository(), modelFactory).start();
    }
    private void manageWorkers() {
        new WorkersManager(in, out, repositoryFactory.getWorkerRepository(), modelFactory).start();
    }

    private void showWelcomeMessage() {
        out.println("\nWelcome to the Bank BackOffice!");
        out.println("Select a menu option or type 'exit' to exit the application:\n");
    }

    private void showMainMenu() {
        out.println("1. Customer");
        out.println("2. Account");
        out.println("3. Loan");
        out.println("4. Transaction");
        out.println("5. Worker");
    }
}
