package cat.uvic.teknos.bank.models;

import java.util.Set;

public interface Account {
    int getId();
    void setId(int id);

    int getCustomerId();

    void setCustomerId(int id);

    Set<Customer> getCustomer();
    void setCustomer(Customer Customer);

    void setCustomer(Set<Customer> customer);

    Set<Worker> getWorker();
    void setWorker(Worker Worker);
    String getaccountType();
    void setaccountType(String accountType);
    int getbalance();
    void setbalance(int balance);
}
