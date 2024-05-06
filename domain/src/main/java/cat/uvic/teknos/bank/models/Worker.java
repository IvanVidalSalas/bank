package cat.uvic.teknos.bank.models;

import java.util.Set;

public interface Worker {
    int getId();
    void setId(int id);
    Set<Transaction> getTransaction();
    void setTransaction(Set <Transaction> transaction);
    Set<Account> getAccount();
    void setAccount(Set <Account> account);
    String getFirstName();
    void setFirstName(String firstName);
    String getLastName();
    void setLastName(String lastName);
}
