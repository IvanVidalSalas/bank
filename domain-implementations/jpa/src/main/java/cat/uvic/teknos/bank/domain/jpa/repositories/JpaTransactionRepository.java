package cat.uvic.teknos.bank.domain.jpa.repositories;

import cat.uvic.teknos.bank.models.Transaction;
import cat.uvic.teknos.bank.repositories.TransactionRepository;
import jakarta.persistence.EntityManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class JpaTransactionRepository implements TransactionRepository {

    private final EntityManager entityManager;

    public JpaTransactionRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void save(Transaction model) {

        try{
            entityManager.getTransaction().begin();
            if(model.getId()<=0){
                //insert
                entityManager.persist(model);
            }else if(!entityManager.contains(model)){
                //update
                var transaction = entityManager.find(Transaction.class, model.getId());

                if(model.getTransactionType().isEmpty() || model.getTransactionType()==null){
                    model.setTransactionType(transaction.getTransactionType());
                }
                if(model.getAmount()<=0){
                    model.setAmount(transaction.getAmount());
                }
                if(model.getTransactionDate()==null){
                    model.setTransactionDate(transaction.getTransactionDate());
                }
                if (model.getCustomer() == null) {
                    model.setCustomer(transaction.getCustomer());
                }
                if (model.getWorker() == null) {
                    model.setWorker(transaction.getWorker());
                }
                entityManager.merge(model);
            }
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println("ERROR SAVING TRANSACTION");
        }
    }

    @Override
    public void delete(Transaction model) {
        try{
            entityManager.getTransaction().begin();
            var transaction = entityManager.find(cat.uvic.teknos.bank.domain.jpa.models.Transaction.class, model.getId());
            entityManager.remove(transaction);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("ERROR DELETING TRANSACTION");
        }

    }

    @Override
    public Transaction get(Integer id) {
        return entityManager.find(Transaction.class, id);
    }

    @Override
    public Set<Transaction> getAll() {
        List<Transaction> transactionList = entityManager.createQuery("SELECT t FROM Transaction t", Transaction.class).getResultList();
        return new HashSet<>(transactionList);
    }
}
