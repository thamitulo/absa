package za.co.bank.x.interview.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import za.co.bank.x.interview.enums.TransactionType;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {
    private String reference;
    private String transactionId;
    private String amount;
    private String fromAccount;
    private String toAccount;
    private boolean internal;
    private TransactionType transactionType;
}