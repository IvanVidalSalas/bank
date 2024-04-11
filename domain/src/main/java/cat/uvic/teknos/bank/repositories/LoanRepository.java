package cat.uvic.teknos.bank.repositories;


import cat.uvic.teknos.bank.models.Loan;

import java.util.Set;

public interface LoanRepository extends Repository<Integer, Loan>{
    @Override
    void save(Loan model);

    @Override
    void delete(Loan model);

    @Override
    Loan get(Integer id);

    @Override
    Set<Loan> getAll();
}
