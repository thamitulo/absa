package za.co.bank.x.interview.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import za.co.bank.x.interview.enums.AccountType;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {
    private String accountNumber;
    private AccountType accountType;
    private String balance;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    Set<TransactionDto> transactions;
}
