package cat.uvic.teknos.bank.domain.jdbc.models;

import cat.uvic.teknos.bank.models.Customer;

import java.time.LocalDate;
import java.util.Set;

public class Loan implements cat.uvic.teknos.bank.models.Loan{
    private int id;
    private int customerid;
    private Set<cat.uvic.teknos.bank.models.Customer> customer;
    private LocalDate loandate;
    private LocalDate returndate;

    @Override
    public int getId() { return id; }
    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public int getCustomerId() { return customerid; }
    @Override
    public void setCustomerId(int customerid) { this.customerid = customerid; }
    @Override
    public Set<cat.uvic.teknos.bank.models.Customer> getCustomer() { return customer; }
    @Override
    public void setCustomer(Set<Customer> customer) { this.customer = customer; }

    @Override
    public LocalDate getLoandate() { return loandate; }
    @Override
    public void setLoandate(LocalDate loandate) { this.loandate = loandate; }

    @Override
    public LocalDate getReturndate() { return returndate; }
    @Override
    public void setReturndate(LocalDate returndate) { this.returndate = returndate; }



}
