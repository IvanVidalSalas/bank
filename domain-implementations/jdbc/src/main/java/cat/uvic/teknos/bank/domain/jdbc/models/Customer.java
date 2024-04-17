package cat.uvic.teknos.bank.domain.jdbc.models;

import java.util.Set;

public class Customer implements cat.uvic.teknos.bank.models.Customer{
    private int id;
    private String firstname;
    private String lastname;
    private String addres;
    private String email;

    @Override
    public int getId() { return id; }
    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public String getFirstname() { return firstname; }
    @Override
    public void setFirstname(String firstname) { this.firstname = firstname; }

    @Override
    public String getLastname() { return lastname; }
    @Override
    public void setLastname(String lastname) { this.lastname = lastname; }

    @Override
    public String getaddres() { return addres; }
    @Override
    public void setaddres(String addres) { this.addres = addres; }

    @Override
    public String getemail() { return email; }
    @Override
    public void setemail(String email) { this.email = email; }
}
