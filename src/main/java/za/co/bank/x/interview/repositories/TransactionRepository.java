package za.co.bank.x.interview.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.bank.x.interview.services.impl.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Iterable<Transaction> findByAccount(String accountNumber);
}
