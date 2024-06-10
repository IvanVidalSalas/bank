package cat.uvic.teknos.bank.backoffice;

import cat.uvic.teknos.bank.models.ModelFactory;
import cat.uvic.teknos.bank.models.Transaction;
import cat.uvic.teknos.bank.models.Worker;
import cat.uvic.teknos.bank.repositories.WorkerRepository;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.a7.A7_Grids;
import de.vandermeer.skb.interfaces.document.TableRowStyle;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.Set;

import static cat.uvic.teknos.bank.backoffice.IOUtils.readLine;

public class WorkersManager {
    private final BufferedReader in;
    private final PrintStream out;
    private final WorkerRepository workerRepository;
    private final ModelFactory modelFactory;

    public WorkersManager(BufferedReader in, PrintStream out, WorkerRepository workerRepository, ModelFactory modelFactory ) {
        this.in = in;
        this.out = out;
        this.workerRepository = workerRepository;
        this.modelFactory = modelFactory;
    }
    public void start() {

        out.println("\nWhat would you like to do?\n");
        out.println("1 to insert a new Worker");
        out.println("2 to update a Worker");
        out.println("3 to delete a Worker");
        out.println("4 to get a Worker");
        out.println("5 to show all Workers");
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

        var worker = modelFactory.createWorker();

        out.println("First name:");
        worker.setFirstName(readLine(in));

        out.println("Last name:");
        worker.setLastName(readLine(in));

        workerRepository.save(worker);

        out.println("Inserted Worker successfully " + worker);
        start();
    }

    private void update() {

        var worker = modelFactory.createWorker();

        out.println("Enter the id of the worker you want to update:");
        int id = Integer.parseInt(readLine(in));
        worker = workerRepository.get(id);

        out.println("Update first name:");
        String firstName = readLine(in);
        if (!firstName.isEmpty()) {
            worker.setFirstName(firstName);
        }

        out.println("Update last name:");
        String lastName = readLine(in);
        if (!lastName.isEmpty()) {
            worker.setLastName(lastName);
        }

        workerRepository.save(worker);
        out.println("Updated Worker successfully " + worker);
        start();
    }

    private void delete(){

        var worker = modelFactory.createWorker();

        out.println("Enter the id of worker you want to delete:");
        int id = Integer.parseInt(readLine(in));
        worker.setId(id);
        workerRepository.delete(worker);
        out.println("Deleted Worker successfully " + worker);
        start();
    }

    private void get(){

        out.println("Please enter the worker id: ");
        int id = Integer.parseInt(readLine(in));
        Worker worker = workerRepository.get(id);

        if (worker == null) {
            out.println("Worker not found!");
        } else {
            out.println("Worker:");
            out.println("Worker id:" + worker.getId());
            out.println("Transaction id:" + worker.getTransaction().getClass());
            out.println("First name:" + worker.getFirstName());
            out.println("Last name:" + worker.getLastName());
        }
        start();
    }
    
    private void getAll() {

        var asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("Id", "Transaction id", "First name", "Last name");
        asciiTable.addRule(TableRowStyle.STRONG);

        for (var worker : workerRepository.getAll()) {
            asciiTable.addRow(worker.getId(), worker.getTransaction().getClass(), worker.getFirstName(), worker.getLastName());
            asciiTable.addRule();
        }
        asciiTable.setTextAlignment(TextAlignment.CENTER);
        asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals());
        String render = asciiTable.render();
        System.out.println(render);
        start();
    }
}
