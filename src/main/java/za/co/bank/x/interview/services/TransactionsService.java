package za.co.bank.x.interview.services;

import za.co.bank.x.interview.models.request.PaymentRequest;
import za.co.bank.x.interview.models.response.TransactionDto;
import za.co.bank.x.interview.models.response.TransactionResponse;
import za.co.bank.x.interview.services.impl.entities.Account;

import java.util.List;

public interface TransactionsService {

    TransactionResponse processTransaction(PaymentRequest paymentRequest);
    List<TransactionDto> retrieveTransactions();
}
