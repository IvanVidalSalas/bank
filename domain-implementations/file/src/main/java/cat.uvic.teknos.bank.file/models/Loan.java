package cat.uvic.teknos.bank.file.models;

import cat.uvic.teknos.bank.models.Customer;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

public class Loan implements cat.uvic.teknos.bank.models.Loan, Serializable {
    private int id;
    private Customer customer;
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
    public Customer getCustomer() {
        return (Customer) customer;
    }

    @Override
    public void setCustomer(Customer customer) { this.customer = customer; }
    @Override
    public LocalDate getLoanDate() {
        return loanDate;
    }

    @Override
    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    @Override
    public LocalDate getReturnDate() {
        return returnDate;
    }

    @Override
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
