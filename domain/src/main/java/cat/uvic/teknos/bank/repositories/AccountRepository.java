package cat.uvic.teknos.bank.repositories;

import cat.uvic.teknos.bank.models.Account;

import java.util.Set;


public interface AccountRepository extends Repository<Integer, Account>{
    @Override
    void save(Account model);

    @Override
    void delete(Account model);

    @Override
    Account get(Integer id);

    @Override
    Set<Account> getAll();
}
