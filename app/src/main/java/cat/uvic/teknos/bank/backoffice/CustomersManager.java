package cat.uvic.teknos.bank.backoffice;

import cat.uvic.teknos.bank.models.ModelFactory;
import cat.uvic.teknos.bank.repositories.CustomerRepository;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.a7.A7_Grids;
import de.vandermeer.skb.interfaces.document.TableRowStyle;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

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

        out.println("\nWhat would you like to do?\n");
        out.println("1 to insert a new Customer");
        out.println("2 to update a Customer");
        out.println("3 to delete a Customer");
        out.println("4 to get a Customer");
        out.println("5 to show all Customers");
        out.println("'exit' to exit");

        var command = "";
        do {
            command = readLine(in);

            switch (command) {
                case "1" -> insert();
                case "2" -> update();
                case "3" -> delete();
                case "4" -> get();
                case "5" -> getAll();
            }
        }
        while (!command.equals("exit"));
        out.println("\nSelect a menu option or type 'exit' to exit the application:");
    }

    private void insert() {

        var customer = modelFactory.createCustomer();

        out.println("First name:");
        customer.setFirstName(readLine(in));

        out.println("Last name:");
        customer.setLastName(readLine(in));

        out.println("Address:");
        customer.setAddress(readLine(in));

        out.println("Email:");
        customer.setEmail(readLine(in));

        customerRepository.save(customer);

        out.println("Inserted Customer successfully " + customer);
        start();
    }

    private void update() {

        var customer = modelFactory.createCustomer();

        out.println("Enter the id of the customer you want to update:");
        int id = Integer.parseInt(readLine(in));
        customer = customerRepository.get(id);

        if (customer == null) {
            out.println("Customer not found!");
            return;
        }

        out.println("Update first name:");
        String firstName = readLine(in);
        if (!firstName.isEmpty()) {
            customer.setFirstName(firstName);
        }

        out.println("Update last name:");
        String lastName = readLine(in);
        if (!lastName.isEmpty()) {
            customer.setLastName(lastName);
        }

        out.println("Update address:");
        String address = readLine(in);
        if (!address.isEmpty()) {
            customer.setAddress(address);
        }

        out.println("Update email:");
        String email = readLine(in);
        if (!email.isEmpty()) {
            customer.setEmail(email);
        }
        customerRepository.save(customer);
        out.println("Updated Customer successfully " + customer);
        start();
    }

    private void delete(){

        var customer = modelFactory.createCustomer();

        out.println("Enter the id of customer you want to delete:");
        int id = Integer.parseInt(readLine(in));
        customer.setId(id);
        customerRepository.delete(customer);
        out.println("Deleted Customer successfully " + customer);
        start();
    }

    private void get(){
        
        out.println("Please enter the customer id: ");
        int id = Integer.parseInt(readLine(in));

        var customer = customerRepository.get(id);

        if (customer == null) {
            out.println("Customer not found!");
        } else {
            out.println("Customer:");
            out.println("Customer First Name: " + customer.getFirstName());
            out.println("Customer Last Name: " + customer.getLastName());
            out.println("Customer Address: " + customer.getAddress());
            out.println("Customer Email: " + customer.getEmail());
        }
        start();
    }
    
    private void getAll() {

        var asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("Id", "First name", "Last name", "Address", "Email");
        asciiTable.addRule(TableRowStyle.STRONG);

        for (var customer : customerRepository.getAll()) {
            asciiTable.addRow(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getAddress(), customer.getEmail());
            asciiTable.addRule();
        }
        asciiTable.setTextAlignment(TextAlignment.CENTER);
        asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals());
        String render = asciiTable.render();
        out.println(render);
        start();
    }

}
