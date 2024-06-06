package cat.uvic.teknos.bank.domain.jpa.repositories;


import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.models.Loan;
import cat.uvic.teknos.bank.repositories.LoanRepository;
import jakarta.persistence.EntityManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JpaLoanRepository implements LoanRepository {

    private final EntityManager entityManager;

    public JpaLoanRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void save(Loan model) {
        try {
            entityManager.getTransaction().begin();
            if (model.getId() == 0) {
                //insert
                entityManager.persist(model);
            } else if (!entityManager.contains(model)) {
                //update
                var loan = entityManager.find(Loan.class, model.getId());
                if (model.getLoanDate() == null) {
                    model.setLoanDate(loan.getLoanDate());
                }
                if (model.getReturnDate() == null) {
                    model.setReturnDate(loan.getReturnDate());
                }
                if (model.getCustomer() == null) {
                    model.setCustomer(loan.getCustomer());
                }
                entityManager.merge(model);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println("ERROR SAVING LOAN");
        }
    }

    @Override
    public void delete(Loan model) {
        try{
            entityManager.getTransaction().begin();
            var loan = entityManager.find(Loan.class, model.getId());
            entityManager.remove(loan);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("ERROR DELETING LOAN");
        }

    }

    @Override
    public Loan get(Integer id) {
        return entityManager.find(Loan.class, id);
    }

    @Override
    public Set<Loan> getAll() {
        List<Loan> loanList = entityManager.createQuery("SELECT l FROM Loan l", Loan.class).getResultList();
        return new HashSet<>(loanList);
    }
}
