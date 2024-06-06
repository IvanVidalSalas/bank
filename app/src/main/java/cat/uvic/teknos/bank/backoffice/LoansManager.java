package cat.uvic.teknos.bank.backoffice;

import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.models.Loan;
import cat.uvic.teknos.bank.models.ModelFactory;
import cat.uvic.teknos.bank.repositories.LoanRepository;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.document.TableRowStyle;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.sql.Date;

import static cat.uvic.teknos.bank.backoffice.IOUtils.readLine;

public class LoansManager {
    private final BufferedReader in;
    private final PrintStream out;
    private final LoanRepository loanRepository;
    private final ModelFactory modelFactory;

    public LoansManager(BufferedReader in, PrintStream out, LoanRepository loanRepository, ModelFactory modelFactory) {
        this.in = in;
        this.out = out;
        this.loanRepository = loanRepository;
        this.modelFactory = modelFactory;
    }

    public void start() {
        out.println("Loans");
        out.println("Type:");
        out.println("1 to insert a new Loan");
        out.println("2 to update a Loan");
        out.println("3 to delete a Loan");
        out.println("4 to get a Loan");
        out.println("5 to show all Loans");
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

        var loan = modelFactory.createLoan();

        out.println("Enter customer id:");
        int customerId = Integer.parseInt(readLine(in));
        Customer customer = modelFactory.createCustomer();
        customer.setId(customerId);
        loan.setCustomer(customer);

        out.println("Enter loan date:");
        Date loanDate = Date.valueOf(readLine(in));
        loan.setLoanDate(loanDate);

        out.println("Enter return date:");
        Date returnDate = Date.valueOf(readLine(in));
        loan.setReturnDate(returnDate);

        loanRepository.save(loan);
        out.println("Inserted Loan successfully: " + loan);
    }

    private void update() {

        var loan = modelFactory.createLoan();

        out.println("Enter the id you want to update:");
        int customerId = Integer.parseInt(readLine(in));
        Customer customer = modelFactory.createCustomer();
        customer.setId(customerId);

        loan.setCustomer(customer);

        out.println("Update loan date:");
        String loanDateString = readLine(in);
        if (!loanDateString.isEmpty()) {
            Date loanDate = Date.valueOf(loanDateString);
            loan.setLoanDate(loanDate);
        }

        out.println("Update return date:");
        String returnDateString = readLine(in);
        if (!returnDateString.isEmpty()) {
            Date returnDate = Date.valueOf(returnDateString);
            loan.setReturnDate(returnDate);
        }

        loanRepository.save(loan);
        out.println("Loan updated successfully: " + loan);
    }

    private void delete() {

        out.println("Enter the id of the loan you want to delete:");
        int loanId = Integer.parseInt(readLine(in));

        Loan loan = loanRepository.get(loanId);
        if (loan == null) {
            out.println("Loan not found!");
            return;
        }

        loanRepository.delete(loan);
        out.println("Loan deleted successfully");
    }

    private void get() {

        out.println("Enter the id of the loan you want to retrieve:");
        int loanId = Integer.parseInt(readLine(in));

        Loan loan = loanRepository.get(loanId);
        if (loan == null) {
            out.println("Loan not found!");
        } else {
            out.println("Loan Details:");
            out.println(loan);
        }
    }

    private void getAll() {

        var asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("ID", "Loan Date", "Return Date");
        asciiTable.addRule(TableRowStyle.STRONG);

        for (var loan : loanRepository.getAll()) { asciiTable.addRow( loan.getCustomer().getId(), loan.getLoanDate(), loan.getReturnDate());
            asciiTable.addRule();
        }
        asciiTable.setTextAlignment(TextAlignment.CENTER);
        String render = asciiTable.render();
        out.println(render);
    }

}
