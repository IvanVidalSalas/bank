package cat.uvic.teknos.bank.domain.jdbc.models;

public class Customer implements cat.uvic.teknos.bank.models.Customer {

    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }
}

