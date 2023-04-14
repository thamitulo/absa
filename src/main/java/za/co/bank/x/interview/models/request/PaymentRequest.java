package za.co.bank.x.interview.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import za.co.bank.x.interview.enums.TransactionType;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class PaymentRequest {
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private String reference;
}