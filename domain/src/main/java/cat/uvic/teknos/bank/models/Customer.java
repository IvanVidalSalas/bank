package cat.uvic.teknos.bank.models;

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
}
