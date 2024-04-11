package cat.uvic.teknos.bank.repositories;


import cat.uvic.teknos.bank.models.Transaction;

import java.util.Set;

public interface TransactionRepository extends Repository<Integer, Transaction>{
    @Override
    void save(Transaction model);

    @Override
    void delete(Transaction model);

    @Override
    Transaction get(Integer id);

    @Override
    Set<Transaction> getAll();
}
