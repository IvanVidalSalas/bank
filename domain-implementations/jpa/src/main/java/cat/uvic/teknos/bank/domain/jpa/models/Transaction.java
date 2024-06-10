package cat.uvic.teknos.bank.domain.jpa.models;

import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.models.Worker;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "TRANSACTION")

public class Transaction implements cat.uvic.teknos.bank.models.Transaction{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID")
    private int id;
    @Column(name = "TRANSACTION_TYPE")
    private String transactionType;
    @Column(name = "AMOUNT")
    private int amount;
    @Column(name = "TRANSACTION_DATE")
    private Date transactionDate;

    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @ManyToOne(targetEntity = Worker.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "WORKER_ID")
    private Worker worker;

    @Override
    public int getId() { return id; }
    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public String getTransactionType() { return transactionType; }
    @Override
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }


    @Override
    public int getAmount() { return amount; }
    @Override
    public void setAmount(int amount) { this.amount = amount; }

    @Override
    public Date getTransactionDate() { return transactionDate; }
    @Override
    public void setTransactionDate(Date transactionDate) { this.transactionDate = transactionDate; }

    @Override
    public Customer getCustomer() {
        return customer;
    }
    @Override
    public void setCustomer(Customer customer) { this.customer = customer; }

    @Override
    public Worker getWorker() { return worker; }
    @Override
    public void setWorker(Worker worker) { this.worker = worker; }
}
