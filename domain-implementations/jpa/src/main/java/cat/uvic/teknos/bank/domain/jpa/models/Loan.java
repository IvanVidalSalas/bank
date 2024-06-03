package cat.uvic.teknos.bank.domain.jpa.models;

import cat.uvic.teknos.bank.models.Customer;
import jakarta.persistence.*;

import java.sql.Date;


@Entity
@Table(name = "LOAN")

public class Loan implements cat.uvic.teknos.bank.models.Loan{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "CUSTOMER")
    private Customer customer;
    @Column(name = "LOAN_DATE")
    private Date loanDate;
    @Column(name = "RETURN_DATE")
    private Date returnDate;

    @Override
    public int getId() { return id; }

    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public Customer getCustomer() { return (Customer) customer; }

    @Override
    public void setCustomer(Customer customer) { this.customer = customer; }

    @Override
    public Date getLoanDate() { return loanDate; }

    @Override
    public void setLoanDate(Date loanDate) { this.loanDate = loanDate; }

    @Override
    public Date getReturnDate() { return returnDate; }

    @Override
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }

}
