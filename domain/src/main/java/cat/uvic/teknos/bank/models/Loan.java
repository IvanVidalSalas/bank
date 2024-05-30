package cat.uvic.teknos.bank.models;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

public interface Loan {
    int getId();
    void setId(int id);

    Date getLoanDate();
    void setLoanDate(Date loanDate);

    Date getReturnDate();
    void setReturnDate(Date returnDate);

    Customer getCustomer();
    void setCustomer(Customer Customer);

}
