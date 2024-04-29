package cat.uvic.teknos.bank.backoffice;

import java.io.BufferedReader;
import java.io.PrintStream;

import static cat.uvic.teknos.bank.backoffice.IOUtils.readLine;


public class CustomerManager {
    private final BufferedReader in;
    private final PrintStream out;

    public CustomerManager(BufferedReader in, PrintStream out) {
        this.in = in;
        this.out = out;
    }
    public void start() {
        out.println("Customers");

        var command = "";
        do {
            command = readLine(in);
            switch (command) {
                case "1" -> insert();
            }
        }
        while (!command.equals("exit"));

        out.println("Bye!");      
        
    }

    private void insert() {
    }
}
