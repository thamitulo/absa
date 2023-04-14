package za.co.bank.x.interview.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import za.co.bank.x.interview.models.request.PaymentRequest;
import za.co.bank.x.interview.models.response.TransactionResponse;
import za.co.bank.x.interview.repositories.AccountsRepository;
import za.co.bank.x.interview.repositories.TransactionRepository;
import za.co.bank.x.interview.services.NotificationsService;
import za.co.bank.x.interview.services.impl.entities.Account;
import za.co.bank.x.interview.services.impl.entities.Transaction;

@Aspect
@Slf4j
@RequiredArgsConstructor
@Component
public class TransactionsAspect {

    private final TransactionRepository transactionRepository;
    private final AccountsRepository accountsRepository;
    private final NotificationsService notificationsService;

    //The point here is to intercept calls to processTransaction method & save any transaction performed
    @Around("execution ( * processTransaction(..)) && args(paymentRequest)")
    public void after(ProceedingJoinPoint proceedingJoinPoint, PaymentRequest paymentRequest) {
        TransactionResponse transactionResponse = null;
        try {
            transactionResponse = (TransactionResponse) proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        Account account;
        if(transactionResponse.isInternal()){
            account = accountsRepository.findByAccountNumber(paymentRequest.getFromAccount()).get();
        }
        else{
            account = accountsRepository.findByAccountNumber(paymentRequest.getToAccount()).get();
        }

        Transaction transaction = Transaction.builder()
                .account(account)
                .transactionType(transactionResponse.getTransactionType())
                .amount(paymentRequest.getAmount())
                .reference(paymentRequest.getReference())
                .otherAccount(transactionResponse.isInternal() ? paymentRequest.getToAccount() : paymentRequest.getFromAccount())
                .build();

        // Record Transactions on account.
        transactionRepository.save(transaction);
        notificationsService.sendNotification(account, transactionResponse);
    }
}
