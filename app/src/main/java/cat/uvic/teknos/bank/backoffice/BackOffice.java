package cat.uvic.teknos.bank.backoffice;

import cat.uvic.teknos.bank.backoffice.exceptions.BackOfficeException;

import java.io.*;

import static cat.uvic.teknos.bank.backoffice.IOUtils.*;

public class BackOffice {
    private final BufferedReader in;
    private final PrintStream out;

    public  BackOffice (InputStream inputStream, OutputStream outputStream) {
        this.in = new BufferedReader(new InputStreamReader(inputStream));
        this.out = new PrintStream(outputStream);
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
        new CustomerManager(in,out).start();
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
    }
}
