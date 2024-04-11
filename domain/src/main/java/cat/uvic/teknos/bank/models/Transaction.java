package cat.uvic.teknos.bank.models;

import java.util.Set;

public interface Transaction {
    int getId();
    void setId(int id);
    Set<Customer> getCustomer();
    void setCustomer(Customer Customer);
    String gettransactionType();
    void settransactionType(String transactionType);
    int getacomunt();
    void setamount(int amount);
    String gettransactionDate();
    void settransactionDate(String transactionDate);
}
