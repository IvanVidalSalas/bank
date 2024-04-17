package cat.uvic.teknos.bank.file.models;

import cat.uvic.teknos.bank.models.Customer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Loan implements cat.uvic.teknos.bank.models.Loan {
    private int id;
    private Set<Customer> customers;
    private LocalDate loanDate;
    private LocalDate returnDate;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Set<Customer> getCustomer() {
        return customers;
    }

    @Override
    public void setCustomer(Customer customer) {
        if (customers == null) {
            customers = new HashSet<>();
        }
        customers.add(customer);
    }

    @Override
    public LocalDate getloandate() {
        return loanDate;
    }

    @Override
    public void setloandate(String date) {
        this.loanDate = LocalDate.parse(date);
    }

    @Override
    public void setloandate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    @Override
    public LocalDate getreturnDate() {
        return returnDate;
    }

    @Override
    public void setreturnDate(String returnDate) {
        this.returnDate = LocalDate.parse(returnDate);
    }

    @Override
    public void setreturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
