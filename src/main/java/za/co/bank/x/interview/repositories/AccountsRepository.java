package za.co.bank.x.interview.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.bank.x.interview.services.impl.entities.Account;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
}
