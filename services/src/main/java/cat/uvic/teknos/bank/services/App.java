package cat.uvic.teknos.bank.services;

import cat.uvic.teknos.bank.domain.jdbc.repositories.JdbcCustomerRepository;
import cat.uvic.teknos.bank.models.ModelFactory;
import cat.uvic.teknos.bank.repositories.RepositoryFactory;
import cat.uvic.teknos.bank.services.controllers.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

public class App {

    public static void main(String[] args) throws  IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        var properties = new Properties();
        properties.load(App.class.getResourceAsStream("/app.properties"));

        RepositoryFactory repositoryFactory = (RepositoryFactory) Class.forName(properties.getProperty("repositoryFactory")).getConstructor().newInstance();
        //ModelFactory modelFactory = (ModelFactory) Class.forName(properties.getProperty("modelFactory")).getConstructor().newInstance();


        var controllers = new HashMap<String, Controller>();
        controllers.put("customer", new CustomerController((RepositoryFactory) repositoryFactory.getCustomerRepository()));
        controllers.put("loan", new LoanController((RepositoryFactory) repositoryFactory.getLoanRepository()));
        controllers.put("account", new AccountController((RepositoryFactory) repositoryFactory.getAccountRepository()));
        controllers.put("worker", new WorkerController((RepositoryFactory) repositoryFactory.getWorkerRepository()));
        controllers.put("transaction", new TransactionController((RepositoryFactory) repositoryFactory.getTransactionRepository()));

        var requestRouter = new RequestRouterImplementation(controllers);
        new Server(requestRouter).start();
    }
}
