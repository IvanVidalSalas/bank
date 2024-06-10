package cat.uvic.teknos.bank.domain.jpa.repositories;

import cat.uvic.teknos.bank.models.Account;
import cat.uvic.teknos.bank.models.Transaction;
import cat.uvic.teknos.bank.repositories.AccountRepository;
import jakarta.persistence.EntityManager;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JpaAccountRepository implements AccountRepository {

    private final EntityManager entityManager;

    public JpaAccountRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Account model) {

        try{
            entityManager.getTransaction().begin();
            if(model.getId()<=0){
                //insert
                entityManager.persist(model);
            }else if(!entityManager.contains(model)){
                //update
                var account = entityManager.find(Account.class, model.getId());

                if(model.getAccountType().isEmpty() || model.getAccountType()==null){
                    model.setAccountType(account.getAccountType());
                }
                if(model.getBalance()<=0){
                    model.setBalance(account.getBalance());
                }
                if (model.getCustomer() == null) {
                    model.setCustomer(account.getCustomer());
                }
                entityManager.merge(model);
            }
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println("ERROR SAVING ACCOUNT");
        }
    }

    @Override
    public void delete(Account model) {
        try{
            entityManager.getTransaction().begin();
            var account = entityManager.find(cat.uvic.teknos.bank.domain.jpa.models.Account.class, model.getId());
            entityManager.remove(account);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("ERROR DELETING ACCOUNT");
        }
    }

    @Override
    public Account get(Integer id) {
        return entityManager.find(Account.class, id);
    }

    @Override
    public Set<Account> getAll() {
        List<Account> accountList = entityManager.createQuery("SELECT a FROM Account a", Account.class).getResultList();
        return new HashSet<>(accountList);
    }

}
