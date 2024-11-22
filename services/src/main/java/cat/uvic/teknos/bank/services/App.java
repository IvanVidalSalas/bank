package cat.uvic.teknos.bank.services;

import cat.uvic.teknos.bank.domain.jdbc.repositories.JdbcRepositoryFactory;
import cat.uvic.teknos.bank.repositories.RepositoryFactory;
import cat.uvic.teknos.bank.services.controllers.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class App {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        RepositoryFactory repositoryFactory = new JdbcRepositoryFactory();


        var customerRepository = repositoryFactory.getCustomerRepository();
        var loanRepository = repositoryFactory.getLoanRepository();
        var accountRepository = repositoryFactory.getAccountRepository();
        var workerRepository = repositoryFactory.getWorkerRepository();
        var transactionRepository = repositoryFactory.getTransactionRepository();

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

        var requestRouter = new RequestRouterImplementation(controllers);
        new Server(requestRouter).start();
    }
}
