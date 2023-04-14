package za.co.bank.x.interview.services;

import za.co.bank.x.interview.models.response.TransactionResponse;
import za.co.bank.x.interview.services.impl.entities.Account;

public interface NotificationsService {

    void sendNotification(Account account, TransactionResponse transactionResponse);
}
