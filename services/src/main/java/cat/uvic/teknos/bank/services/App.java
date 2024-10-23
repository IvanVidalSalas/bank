package cat.uvic.teknos.bank.services;

import cat.uvic.teknos.bank.models.ModelFactory;
import cat.uvic.teknos.bank.repositories.RepositoryFactory;
import cat.uvic.teknos.bank.services.controllers.Controller;
import cat.uvic.teknos.bank.services.controllers.CustomerController;
import cat.uvic.teknos.bank.services.controllers.LoanController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

public class App {

    public static void main(String[] args) throws  IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        var properties = new Properties();
        properties.load(App.class.getResourceAsStream("/app.properties"));

        RepositoryFactory repositoryFactory = (RepositoryFactory) Class.forName(properties.getProperty("repositoryFactory")).getConstructor().newInstance();
        ModelFactory modelFactory = (ModelFactory) Class.forName(properties.getProperty("modelFactory")).getConstructor().newInstance();

        // TODO: fix raw type warning
        var controllers = new HashMap<String, Controller>();

        // TODO: review how to deserialize json objects
        controllers.put("customers", new CustomerController(repositoryFactory, modelFactory));
        controllers.put("loans", new LoanController(repositoryFactory, modelFactory));

        var requestRouter = new RequestRouterImplementation(controllers);
        new Server(requestRouter)
                .start();
    }
}
