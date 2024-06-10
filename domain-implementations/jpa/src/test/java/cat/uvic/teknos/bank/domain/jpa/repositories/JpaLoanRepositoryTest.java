package cat.uvic.teknos.bank.domain.jpa.repositories;

import cat.uvic.teknos.bank.domain.jpa.models.Customer;
import cat.uvic.teknos.bank.domain.jpa.models.Loan;
import cat.uvic.teknos.bank.domain.jpa.repositories.JpaLoanRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JpaLoanRepositoryTest {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    @BeforeAll
    static void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("bank-mysql");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll
    static void tearDown() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void insertLoanTest() {

        var loanRepository = new JpaLoanRepository(entityManager);

        Loan loan = new Loan();
        loan.setLoanDate(new Date(System.currentTimeMillis()));
        loan.setReturnDate(new Date(System.currentTimeMillis() + 86400000)); // 1 day later

        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setAddress("Doe street");
        customer.setEmail("John@example.com");
        loan.setCustomer(customer);

        entityManager.getTransaction().begin();
        loanRepository.save(loan);
        entityManager.getTransaction().commit();

        assertNotNull(loan.getCustomer().getId());
    }

    @Test
    void updateLoanTest() {

        var loanRepository = new JpaLoanRepository(entityManager);

        Loan loan = new Loan();
        loan.setLoanDate(new Date(System.currentTimeMillis()));
        loan.setReturnDate(new Date(System.currentTimeMillis() + 86400000)); // 1 day later

        Customer customer = new Customer();
        loan.getCustomer().getId();
        loan.setCustomer(customer);

        entityManager.getTransaction().begin();
        loanRepository.save(loan);
        entityManager.getTransaction().commit();

        loan.setReturnDate(new Date(System.currentTimeMillis() + 172800000)); // 2 days later

        entityManager.getTransaction().begin();
        loanRepository.save(loan);
        entityManager.getTransaction().commit();

        Loan updatedLoan = entityManager.find(Loan.class, loan.getId());
        assertEquals(loan.getReturnDate(), updatedLoan.getReturnDate(), "Return date should be updated");
    }

    @Test
    void deleteLoanTest() {
        var loanRepository = new JpaLoanRepository(entityManager);

        Loan loan = new Loan();
        loan.setLoanDate(new Date(System.currentTimeMillis()));
        loan.setReturnDate(new Date(System.currentTimeMillis() + 86400000)); // 1 day later

        Customer customer = new Customer();
        loan.getCustomer().getId();
        loan.setCustomer(customer);

        entityManager.getTransaction().begin();
        loanRepository.save(loan);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        loanRepository.delete(loan);
        entityManager.getTransaction().commit();

        assertNull(entityManager.find(Loan.class, loan.getId()), "Loan should be deleted");
    }

    @Test
    void getLoanTest() {

        var loanRepository = new JpaLoanRepository(entityManager);

        Loan loan = new Loan();
        loan.setLoanDate(new Date(System.currentTimeMillis()));
        loan.setReturnDate(new Date(System.currentTimeMillis() + 86400000)); // 1 day later

        Customer customer = new Customer();
        loan.getCustomer().getId();
        loan.setCustomer(customer);

        entityManager.getTransaction().begin();
        loanRepository.save(loan);
        entityManager.getTransaction().commit();

        Loan retrievedLoan = (Loan) loanRepository.get(loan.getCustomer().getId());
        assertNotNull(retrievedLoan, "Loan should be retrieved");
        assertEquals(loan.getId(), retrievedLoan.getId(), "Retrieved loan ID should match");
    }

    @Test
    void getAllLoansTest() {

        var loanRepository = new JpaLoanRepository(entityManager);

        Loan loan1 = new Loan();
        loan1.setLoanDate(new Date(System.currentTimeMillis()));
        loan1.setReturnDate(new Date(System.currentTimeMillis() + 86400000)); // 1 day later

        Customer customer1 = new Customer();
        loan1.getCustomer().getId();
        loan1.setCustomer(customer1);

        Loan loan2 = new Loan();
        loan2.setLoanDate(new Date(System.currentTimeMillis()));
        loan2.setReturnDate(new Date(System.currentTimeMillis() + 86400000)); // 1 day later

        Customer customer2 = new Customer();
        loan2.getCustomer().getId();
        loan2.setCustomer(customer2);

        entityManager.getTransaction().begin();
        loanRepository.save(loan1);
        loanRepository.save(loan2);
        entityManager.getTransaction().commit();

        Set<cat.uvic.teknos.bank.models.Loan> loans = loanRepository.getAll();
        assertFalse(loans.isEmpty(), "The list of loans should not be empty");
    }
}
