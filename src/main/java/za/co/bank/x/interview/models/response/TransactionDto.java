package za.co.bank.x.interview.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import za.co.bank.x.interview.enums.TransactionType;

import java.time.LocalDateTime;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDto {
    private TransactionType transactionType;
    private String amount;
    private LocalDateTime transactionTime;
  //  private TransactionStatus status;
    private String reference;
    private String destinationAccount;
    private String sourceAccount;
}
