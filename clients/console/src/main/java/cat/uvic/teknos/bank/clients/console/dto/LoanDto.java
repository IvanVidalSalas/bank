package cat.uvic.teknos.bank.clients.console.dto;

import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.models.Loan;

import java.sql.Date;

public class LoanDto implements Loan{

    private int id;
    private Date loanDate;
    private Date returnDate;
    private Customer customer;

    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Date getLoanDate() {
        return loanDate;
    }
    @Override
    public void setLoanDate(Date loanDate) { this.loanDate = loanDate; }

    @Override
    public Date getReturnDate() {
        return returnDate;
    }

    @Override
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public Customer getCustomer() { return customer; }
    @Override
    public void setCustomer(Customer customer) { this.customer = customer; }

}
