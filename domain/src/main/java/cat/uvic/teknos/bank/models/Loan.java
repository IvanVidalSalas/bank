package cat.uvic.teknos.bank.models;

import java.time.LocalDate;
import java.util.Set;

public interface Loan {
    int getId();
    void setId(int id);
    Set<Customer> getCustomer();
    void setCustomer(Customer Customer);
    LocalDate getloandate();
    void setloandate(String date);

    void setloandate(LocalDate loandate);

    LocalDate getreturnDate();
    void setreturnDate(String returnDate);

    void setreturnDate(LocalDate returnDate);
}
