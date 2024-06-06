package cat.uvic.teknos.bank.backoffice;

import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.models.ModelFactory;
import cat.uvic.teknos.bank.repositories.CustomerRepository;
import cat.uvic.teknos.bank.repositories.TransactionRepository;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.document.TableRowStyle;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.sql.Date;

import static cat.uvic.teknos.bank.backoffice.IOUtils.readLine;

public class TransactionsManager {
    private final BufferedReader in;
    private final PrintStream out;
    private final TransactionRepository transactionRepository;
    private final ModelFactory modelFactory;

    public TransactionsManager(BufferedReader in, PrintStream out, TransactionRepository transactionRepository, ModelFactory modelFactory ) {
        this.in = in;
        this.out = out;
        this.transactionRepository = transactionRepository;
        this.modelFactory = modelFactory;
    }
    public void start() {
        out.println("Transactions");
        out.println("Type:");
        out.println("1 to insert a new Transaction");
        out.println("2 to update a Transaction");
        out.println("3 to delete a Transaction");
        out.println("4 to get a Transaction");
        out.println("5 to show all Transactions");
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

        out.println("Bye!");

    }

    private void insert() {

        var transaction = modelFactory.createTransaction();

        out.println("Enter customer ID:");
        int customerId = Integer.parseInt(readLine(in));
        Customer customer = modelFactory.createCustomer();
        customer.setId(customerId);
        transaction.setCustomer(customer);

        out.println("Transaction Type:");
        transaction.setTransactionType(readLine(in));

        out.println("Amount:");
        transaction.setAmount(Integer.parseInt(readLine(in)));

        out.println("Transaction date:");
        Date transaDate = Date.valueOf(readLine(in));
        transaction.setTransactionDate(transaDate);

        out.println("Inserted Transaction successfully" + transaction);
    }

    private void update() {

        var transaction= modelFactory.createTransaction();

        out.println("Enter the id of the transaction you want to update:");
        int id = Integer.parseInt(readLine(in));
        transaction = transactionRepository.get(id);

        if (transaction == null) {
            out.println("Transaction not found!");
            return;
        }

        out.println("Enter the customer ID:");
        int customerId = Integer.parseInt(readLine(in));
        Customer customer = modelFactory.createCustomer();
        customer.setId(customerId);
        transaction.setCustomer(customer);

        out.println("Update transaction type name:");
        String transactionType = readLine(in);
        if (!transactionType.isEmpty()) {
            transaction.setTransactionType(transactionType);
        }

        out.println("Update amount:");
        int amount = Integer.parseInt(readLine(in));
            transaction.setAmount(amount);

        transactionRepository.save(transaction);
    }

    private void delete(){

        var transaction = modelFactory.createTransaction();

        out.println("Enter the id of transaction you want to delete:");
        int id = Integer.parseInt(readLine(in));
        transaction.setId(id);
        transactionRepository.delete(transaction);
    }

    private void get(){

        out.println("Please enter the transaction ID: ");
        int id = Integer.parseInt(readLine(in));

        var transaction = transactionRepository.get(id);

        if (transaction == null) {
            out.println("Transaction not found!");
        } else {
            out.println("Transaction Details:");
            out.println(transaction);
        }
    }

    private void getAll() {

        var asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("Id","Customer_id", "Transaction type", "Amount", "Transaction date");
        asciiTable.addRule(TableRowStyle.STRONG);

        for (var transaction : transactionRepository.getAll()) {
            asciiTable.addRow(transaction.getId(), transaction.getCustomer().getId(), transaction.getTransactionType(), transaction.getAmount(), transaction.getTransactionDate());
            asciiTable.addRule();
        }
        asciiTable.setTextAlignment(TextAlignment.CENTER);
        String render = asciiTable.render();
        System.out.println(render);
    }
}
