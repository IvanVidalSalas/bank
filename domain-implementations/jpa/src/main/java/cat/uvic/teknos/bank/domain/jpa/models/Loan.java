package cat.uvic.teknos.bank.domain.jpa.models;

import cat.uvic.teknos.bank.models.Customer;
import jakarta.persistence.*;

import java.sql.Date;


@Entity
@Table(name = "LOAN")

public class Loan implements cat.uvic.teknos.bank.models.Loan{

    @Id
    @Column(name="CUSTOMER_ID")
    private int id;
    @Column(name = "LOAN_DATE")
    private Date loanDate;
    @Column(name = "RETURN_DATE")
    private Date returnDate;
    @OneToOne(targetEntity = Customer.class, fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @Override
    public int getId() { return id; }
    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public Date getLoanDate() { return loanDate; }
    @Override
    public void setLoanDate(Date loanDate) { this.loanDate = loanDate; }

    @Override
    public Date getReturnDate() { return returnDate; }
    @Override
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }

    @OneToOne(targetEntity = Customer.class)
    @Override
    public Customer getCustomer() { return customer; }
    @Override
    public void setCustomer(Customer customer) { this.customer = customer; }

}
