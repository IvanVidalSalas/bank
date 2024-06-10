package cat.uvic.teknos.bank.domain.jpa.repositories;

import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.repositories.CustomerRepository;
import jakarta.persistence.EntityManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JpaCustomerRepository implements CustomerRepository {

    private final EntityManager entityManager;

    public JpaCustomerRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void save(Customer model) {
        try{
            entityManager.getTransaction().begin();
            if(model.getId()<=0){
                //insert
                entityManager.persist(model);
            }else if(!entityManager.contains(model)){

                //update
                var customer = entityManager.find(Customer.class, model.getId());

                if(model.getFirstName().isEmpty() || model.getFirstName()==null){
                    model.setFirstName(customer.getFirstName());
                }
                if(model.getLastName().isEmpty() || model.getLastName()==null){
                    model.setLastName(customer.getLastName());
                }
                if(model.getAddress().isEmpty() || model.getAddress()==null){
                    model.setAddress(customer.getAddress());
                }
                if(model.getEmail().isEmpty() || model.getEmail()==null){
                    model.setEmail(customer.getEmail());
                }

                entityManager.merge(model);
            }
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("ERROR SAVING CUSTOMER");
        }
    }

    @Override
    public void delete(Customer model) {
        try{
            entityManager.getTransaction().begin();
            Customer customer = entityManager.find(Customer.class, model.getId());
            entityManager.remove(customer);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("ERROR DELETING CUSTOMER");
        }

    }

    @Override
    public Customer get(Integer Id) {
        return entityManager.find(Customer.class, Id);
    }

    @Override
    public Set<Customer> getAll() {
        List<Customer> customerList = entityManager.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
        return new HashSet<>(customerList);
    }
}
