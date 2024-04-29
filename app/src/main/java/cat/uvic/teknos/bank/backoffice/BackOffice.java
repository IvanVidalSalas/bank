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

    public  BackOffice (InputStream inputStream, OutputStream outputStream, RepositoryFactory repositoryFactory, ModelFactory modelFactory) {
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
            }
        }
        while (!command.equals("exit"));

        out.println("Bye!");
    }

    private void manageCustomers() {
        new CustomersManager(in, out, repositoryFactory.getCustomerRepository(), modelFactory).start();
    }

    private void showWelcomeMessage() {
        out.println("Welcome to the Bank BackOffice!");
        out.println("Select a menu option or type exit to exit the application");
    }

    private void showMainMenu() {
        out.print("1. Customer");
        out.print("2. Account");
        out.print("3. Loan");
        out.print("4. Transaction");
        out.print("5. Worker");
        out.print("6. Exit");
    }
}
