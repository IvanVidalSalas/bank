package cat.uvic.teknos.bank.models;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

public interface Transaction {
    int getId();
    void setId(int id);

    String getTransactionType();
    void setTransactionType(String transactionType);

    int getAmount();
    void setAmount(int amount);

    Date getTransactionDate();
    void setTransactionDate(Date transactionDate);

    Customer getCustomer();
    void setCustomer(Customer customer);

    Worker getWorker();
    void setWorker(Worker worker);
}
