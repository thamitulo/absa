package za.co.bank.x.interview.services.impl.entities;

import lombok.*;
import org.springframework.lang.Nullable;
import za.co.bank.x.interview.enums.TransactionType;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction extends AbstractAuditEntity {

    private TransactionType transactionType;
    private BigDecimal amount;
    private LocalDateTime transactionTime;
   // private TransactionStatus status;
    private String reference;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Nullable
    private String otherAccount;
}
