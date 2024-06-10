package cat.uvic.teknos.bank.backoffice;

import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.models.ModelFactory;
import cat.uvic.teknos.bank.repositories.AccountRepository;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.a7.A7_Grids;
import de.vandermeer.skb.interfaces.document.TableRowStyle;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.sql.Date;

import static cat.uvic.teknos.bank.backoffice.IOUtils.readLine;

public class AccountsManager {
    private final BufferedReader in;
    private final PrintStream out;
    private final AccountRepository accountRepository;
    private final ModelFactory modelFactory;

    public AccountsManager(BufferedReader in, PrintStream out, AccountRepository accountRepository, ModelFactory modelFactory ) {
        this.in = in;
        this.out = out;
        this.accountRepository = accountRepository;
        this.modelFactory = modelFactory;
    }
    public void start() {

        out.println("\nWhat would you like to do?\n");
        out.println("1 to insert a new Account");
        out.println("2 to update a Account");
        out.println("3 to delete a Account");
        out.println("4 to get an Account");
        out.println("5 to show all Accounts");
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

        var account = modelFactory.createAccount();

        out.println("Customer id:");
        int customerId = Integer.parseInt(readLine(in));
        Customer customer = modelFactory.createCustomer();
        customer.setId(customerId);
        account.setCustomer(customer);

        out.println("Account Type:");
        account.setAccountType(readLine(in));

        out.println("Balance:");
        account.setBalance(Integer.parseInt(readLine(in)));

        accountRepository.save(account);
        out.println("Inserted Account successfully " + account);
        start();
    }

    private void update() {

        var account = modelFactory.createAccount();

        out.println("Enter the id of the account you want to update:");
        int id = Integer.parseInt(readLine(in));
        account = accountRepository.get(id);

        out.println("Update the customer id:");
        int customerId = Integer.parseInt(readLine(in));
        Customer customer = modelFactory.createCustomer();
        customer.setId(customerId);
        account.setCustomer(customer);

        out.println("Update account type:");
        String accountType = readLine(in);
        if (!accountType.isEmpty()) {
            account.setAccountType(accountType);
        }

        out.println("Update balance:");
        int balance = Integer.parseInt(readLine(in));
        account.setBalance(balance);

        accountRepository.save(account);
        out.println("Updated Account successfully " + account);
        start();
    }

    private void delete(){

        var account = modelFactory.createAccount();

        out.println("Enter the id of account you want to delete");
        int id = Integer.parseInt(readLine(in));
        account.setId(id);
        accountRepository.delete(account);
        out.println("Deleted Account successfully " + account);
        start();
    }

    private void get(){

        out.println("Please enter the account id: ");
        int id = Integer.parseInt(readLine(in));

        var account = accountRepository.get(id);

        if (account == null) {
            out.println("Account not found!");
        } else {
            out.println("Account:");
            out.println("Customer id: " + account.getCustomer().getId());
            out.println("Account type: " + account.getAccountType());
            out.println("Balance: " + account.getBalance());
            start();
        }
    }
    
    private void getAll() {

        var asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("Id", "Customer id" , "Account type", "Balance");
        asciiTable.addRule(TableRowStyle.STRONG);

        for (var account : accountRepository.getAll()) {
            asciiTable.addRow(account.getId(), account.getCustomer().getId(), account.getAccountType(), account.getBalance());
            asciiTable.addRule();
        }
        asciiTable.setTextAlignment(TextAlignment.CENTER);
        asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals());
        String render = asciiTable.render();
        System.out.println(render);
        start();
    }


}
