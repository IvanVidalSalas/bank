package cat.uvic.teknos.bank.domain.jpa.repositories;

import cat.uvic.teknos.bank.models.Account;
import cat.uvic.teknos.bank.repositories.AccountRepository;

import java.util.Set;

public class JpaAccountRepository implements AccountRepository {

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