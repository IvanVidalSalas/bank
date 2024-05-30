package cat.uvic.teknos.bank.models;

import java.util.Set;

public interface Customer {
    int getId();
    void setId(int id);

    String getFirstName();
    void setFirstName(String firstName);

    String getLastName();
    void setLastName(String lastName);

    String getAddress();
    void setAddress(String address);

    String getEmail();
    void setEmail(String email);

    Loan getLoan();
    void setLoan(Loan loan);

    Set<Transaction> getTransaction();
    void setTransaction(Set<Transaction> transactions);
}
