package za.co.bank.x.interview.services.impl.entities;

import lombok.*;
import za.co.bank.x.interview.enums.AccountType;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Set;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AbstractAuditEntity {
    private String accountNumber;
    private AccountType accountType;
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "account")
    Set<Transaction> transactions;
}

