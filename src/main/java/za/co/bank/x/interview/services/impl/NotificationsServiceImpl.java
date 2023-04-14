package za.co.bank.x.interview.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import za.co.bank.x.interview.models.response.TransactionResponse;
import za.co.bank.x.interview.services.NotificationsService;
import za.co.bank.x.interview.services.impl.entities.Account;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationsServiceImpl implements NotificationsService {

    @Override
    public void sendNotification(Account account, TransactionResponse transactionResponse) {
        //TO-DO trigger email or sms notification with transaction details
        log.info(String.format("%s%s%s%s", "Hi {} , Transaction on Account Number {} for amount {} noted on your account with reference {} ", account.getCustomer().getFirstName(), account.getAccountNumber(), transactionResponse.getAmount(), transactionResponse.getReference()));
    }
}
