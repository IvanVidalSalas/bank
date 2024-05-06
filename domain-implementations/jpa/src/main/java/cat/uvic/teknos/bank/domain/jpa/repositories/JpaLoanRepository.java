package cat.uvic.teknos.bank.domain.jpa.repositories;

import cat.uvic.teknos.bank.models.Loan;
import cat.uvic.teknos.bank.repositories.LoanRepository;

import java.util.Set;

public class JpaLoanRepository implements LoanRepository {

    @Override
    public void save(Loan model) {

    }

    @Override
    public void delete(Loan model) {

    }

    @Override
    public Loan get(Integer id) {
        return null;
    }

    @Override
    public Set<Loan> getAll() {
        return Set.of();
    }
}
