package cat.uvic.teknos.bank.backoffice;

import cat.uvic.teknos.bank.models.ModelFactory;
import cat.uvic.teknos.bank.repositories.CustomerRepository;

import java.io.BufferedReader;
import java.io.PrintStream;

import static cat.uvic.teknos.bank.backoffice.IOUtils.readLine;


public class CustomersManager {
    private final BufferedReader in;
    private final PrintStream out;
    private final CustomerRepository customerRepository;
    private final ModelFactory modelFactory;

    public CustomersManager(BufferedReader in, PrintStream out, CustomerRepository customerRepository, ModelFactory modelFactory ) {
        this.in = in;
        this.out = out;
        this.customerRepository = customerRepository;
        this.modelFactory = modelFactory;
    }
    public void start() {
        out.println("Customers:");
        out.println("Type:");
        out.println("1 to insert a new Customer");

        var command = "";
        do {
            command = readLine(in);
            switch (command) {
                case "1" -> insert();
                case "2" -> update();
                case "4" -> getAll();
            }
        }
        while (!command.equals("exit"));

        out.println("Bye!");      
        
    }

    private void insert() {
        var customer = modelFactory.createCustomer();

        out.println("First name:");
        customer.setFirstName(readLine(in));

        out.println("Last name:");
        customer.setLastName(readLine(in));

        customerRepository.save(customer);

        out.println("Inserted Customer successfully" + customer);
    }

    private void update() {
        var customer = modelFactory.createCustomer();
        customerRepository.save(customer);
    }
    
    private void getAll() {

        var asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("Id", "First name", "Last name");
        asciiTable.addRule(TableRowStyle.STRONG);

        for (var customer : customerRepository.getAll()) {
            asciiTable.addRow(customer.getId(), customer.getFirstName(), customer.getLastName());
            asciiTable.addRule();
        }
        asciiTable.setTextAlignment(TextAlignment.CENTER);
        String render = asciiTable.render();
        System.out.println(render);
    }
}
