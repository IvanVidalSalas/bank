package cat.uvic.teknos.bank.domain.jdbc.models;

import cat.uvic.teknos.bank.models.Customer;

import java.time.LocalDate;

public class Loan implements cat.uvic.teknos.bank.models.Loan{

    private int id;
    private Customer customer;
    private LocalDate localDate;
    private LocalDate returnDate;

    @Override
    public int getId() { return id; }

    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public Customer getCustomer() { return (Customer) customer; }

    @Override
    public void setCustomer(Customer customer) { this.customer = customer; }

    @Override
    public LocalDate getLoanDate() { return localDate; }

    @Override
    public void setLoanDate(LocalDate loanDate) { this.localDate = loanDate; }

    @Override
    public LocalDate getReturnDate() { return returnDate; }

    @Override
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

}
