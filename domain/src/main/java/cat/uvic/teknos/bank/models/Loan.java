package cat.uvic.teknos.bank.models;

import java.time.LocalDate;
import java.util.Set;

public interface Loan {
    int getId();
    void setId(int id);
    Customer getCustomer();
    void setCustomer(Customer Customer);
    LocalDate getLoanDate();
    void setLoanDate(LocalDate loanDate);
    LocalDate getReturnDate();
    void setReturnDate(LocalDate returnDate);

}
