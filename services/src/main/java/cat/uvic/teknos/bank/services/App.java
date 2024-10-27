package cat.uvic.teknos.bank.services;

import cat.uvic.teknos.bank.domain.jdbc.repositories.JdbcRepositoryFactory;
import cat.uvic.teknos.bank.models.ModelFactory;
import cat.uvic.teknos.bank.repositories.RepositoryFactory;
import cat.uvic.teknos.bank.services.controllers.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class App {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Create the repository factory directly
        RepositoryFactory repositoryFactory = new JdbcRepositoryFactory();

        // Get repositories
        var customerRepository = repositoryFactory.getCustomerRepository();
        var loanRepository = repositoryFactory.getLoanRepository();
        var accountRepository = repositoryFactory.getAccountRepository();
        var workerRepository = repositoryFactory.getWorkerRepository();
        var transactionRepository = repositoryFactory.getTransactionRepository();

        // Create controllers using the repositories
        var customerController = new CustomerController(customerRepository);
        var loanController = new LoanController(loanRepository);
        var accountController = new AccountController(accountRepository);
        var workerController = new WorkerController(workerRepository);
        var transactionController = new TransactionController(transactionRepository);

        // Map the controllers to their corresponding route identifiers
        Map<String, Controller> controllers = new HashMap<>();
        controllers.put("customer", customerController);
        controllers.put("loan", loanController);
        controllers.put("account", accountController);
        controllers.put("worker", workerController);
        controllers.put("transaction", transactionController);

        // Set up the request router and start the server
        var requestRouter = new RequestRouterImplementation(controllers);
        new Server(requestRouter).start();
    }
}
