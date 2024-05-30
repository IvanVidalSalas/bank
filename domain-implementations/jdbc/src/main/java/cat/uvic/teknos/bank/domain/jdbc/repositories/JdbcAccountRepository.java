package cat.uvic.teknos.bank.domain.jdbc.repositories;

import cat.uvic.teknos.bank.models.Account;
import cat.uvic.teknos.bank.repositories.AccountRepository;

import java.sql.Connection;
import java.util.Set;

public class JdbcAccountRepository implements AccountRepository {

    private final Connection connection;

    public JdbcAccountRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Account model) {

    }

    @Override
    public void delete(Account model) {

    }

    @Override
    public Account get(Integer id) {
        return null;
    }

    @Override
    public Set<Account> getAll() {
        return Set.of();
    }
}
