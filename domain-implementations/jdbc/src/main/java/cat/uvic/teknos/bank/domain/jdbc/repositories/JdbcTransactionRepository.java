package cat.uvic.teknos.bank.domain.jdbc.repositories;

import cat.uvic.teknos.bank.models.Transaction;
import cat.uvic.teknos.bank.repositories.TransactionRepository;

import java.util.Set;

public class JdbcTransactionRepository implements TransactionRepository {
    @Override
    public void save(Transaction model) {

    }

    @Override
    public void delete(Transaction model) {

    }

    @Override
    public Transaction get(Integer id) {
        return null;
    }

    @Override
    public Set<Transaction> getAll() {
        return Set.of();
    }
}
