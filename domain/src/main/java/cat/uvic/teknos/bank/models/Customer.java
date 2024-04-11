package cat.uvic.teknos.bank.models;


public interface Customer {
    int getId();
    void setId(int id);
    String getfirstName();
    void setfirstName(String firstName);
    String getlastName();
    void lastName(String lastName);
    String getAddres();
    void setAddres(String Addres);
    String getEmail();
    void setEmail(String Email);
}
