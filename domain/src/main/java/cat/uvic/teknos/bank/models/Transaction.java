package cat.uvic.teknos.bank.models;

import java.time.LocalDate;
import java.util.Set;

public interface Transaction {
    int getId();
    void setId(int id);
    Set<Customer> getCustomer();
    void setCustomer(Set <Customer> customer);

    void setCustomer(Set<Customer> customer);

    String getTransactionType();
    void setTransactionType(String transactionType);
    int getAmount();
    void setAmount(int amount);
    LocalDate getTransactionDate();
    void setTransactionDate(LocalDate transactionDate);
}
