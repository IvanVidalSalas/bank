package cat.uvic.teknos.bank.models;

import java.util.Set;

public interface Account {
    int getId();
    void setId(int id);
    Set<Customer> getCustomer();
    void setCustomer(Set <Customer> customer);
    Set<Worker> getWorker();
    void setWorker(Set <Worker> worker);
    String getAccountType();
    void setAccountType(String accountType);
    int getBalance();
    void setBalance(int balance);
}
