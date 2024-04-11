package cat.uvic.teknos.bank.models;

import java.util.Set;

public interface Worker {
    int getId();
    void setId(int id);
    Set<Transaction> getTransaction();
    void setTransaction(Transaction Transaction);
    Set<Account> getAccount();
    void setAccount(Account Account);
    String getfirstName();
    void setfirstName(String firstName);
    String getlastName();
    void setlastName(String lastName);
}
