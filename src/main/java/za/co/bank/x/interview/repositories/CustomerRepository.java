package za.co.bank.x.interview.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.bank.x.interview.services.impl.entities.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByIdNumber(String id);
}
