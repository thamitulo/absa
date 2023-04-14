package za.co.bank.x.interview.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentResponse {
    private String reference;
    private String transactionId;
    private String amount;
    private String fromAccount;
    private String toAccount;
}
