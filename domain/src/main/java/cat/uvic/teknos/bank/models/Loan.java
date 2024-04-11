package cat.uvic.teknos.bank.models;

import java.util.Set;

public interface Loan {
    int getId();
    void setId(int id);
    Set<Customer> getCustomer();
    void setCustomer(Customer Customer);
    String getdate();
    void setdate(String date);
    String getreturnDate();
    void setreturnDate(String returnDate);
}
