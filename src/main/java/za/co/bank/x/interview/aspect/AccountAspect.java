package za.co.bank.x.interview.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import za.co.bank.x.interview.enums.AccountType;
import za.co.bank.x.interview.enums.TransactionType;
import za.co.bank.x.interview.models.request.PaymentRequest;
import za.co.bank.x.interview.models.response.TransactionResponse;
import za.co.bank.x.interview.repositories.AccountsRepository;
import za.co.bank.x.interview.repositories.TransactionRepository;
import za.co.bank.x.interview.services.NotificationsService;
import za.co.bank.x.interview.services.impl.entities.Account;
import za.co.bank.x.interview.services.impl.entities.Customer;
import za.co.bank.x.interview.services.impl.entities.Transaction;

import static za.co.bank.x.interview.utils.CommonConstants.*;

@Aspect
@Slf4j
@RequiredArgsConstructor
@Component
public class AccountAspect {

    private final TransactionRepository transactionRepository;
    private final AccountsRepository accountsRepository;
    private final NotificationsService notificationsService;

    @Around("execution ( * createAccount(..)) && args(accountType, customer)")
    public void after(ProceedingJoinPoint proceedingJoinPoint, AccountType accountType, Customer customer) {
        Account account = null;
        try {
            account = (Account) proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        if (accountType == AccountType.SAVINGS_ACCOUNT) {
            Transaction transaction = Transaction.builder()
                    .account(account)
                    .transactionType(TransactionType.CREDIT)
                    .amount(SAVINGS_JOINING_BONUS)
                    .reference(SAVINGS_JOINING_REFERENCE)
                    .otherAccount(BANK_X_ACCOUNT_NUMBER)
                    .build();

            // REcord initial trnsaction for onboarding & creating savings account.
            transactionRepository.save(transaction);
            notificationsService.sendNotification(account, TransactionResponse.builder().amount(SAVINGS_JOINING_BONUS.toString()).reference(SAVINGS_JOINING_REFERENCE).build());
        }
    }
}
