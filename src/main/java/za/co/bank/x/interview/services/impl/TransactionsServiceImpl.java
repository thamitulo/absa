package za.co.bank.x.interview.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import za.co.bank.x.interview.enums.TransactionType;
import za.co.bank.x.interview.exceptions.GeneralException;
import za.co.bank.x.interview.services.impl.entities.Account;
import za.co.bank.x.interview.mapper.TransactionsMapper;
import za.co.bank.x.interview.models.request.PaymentRequest;
import za.co.bank.x.interview.models.response.TransactionDto;
import za.co.bank.x.interview.models.response.TransactionResponse;
import za.co.bank.x.interview.repositories.TransactionRepository;
import za.co.bank.x.interview.services.AccountService;
import za.co.bank.x.interview.services.TransactionsService;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

import static za.co.bank.x.interview.enums.AccountType.CURRENT_ACCOUNT;
import static za.co.bank.x.interview.enums.AccountType.SAVINGS_ACCOUNT;
import static za.co.bank.x.interview.rules.RulesProcess.canTransact;
import static za.co.bank.x.interview.utils.CommonConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionsServiceImpl implements TransactionsService {

    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final TransactionsMapper transactionsMapper;

    @Override
    public TransactionResponse processTransaction(PaymentRequest paymentRequest) {
        Optional<Account> optionalFromAccount = accountService.getAccountByAccountNumber(paymentRequest.getFromAccount());
        Optional<Account> optionalToAccount = accountService.getAccountByAccountNumber(paymentRequest.getToAccount());

         canTransact(optionalFromAccount.get());

        if (!optionalFromAccount.isPresent() && !optionalToAccount.isPresent()) {
            throw new GeneralException(INVALID_ACCOUNTS);
        } else if (!optionalFromAccount.isPresent()) {
            // Inward payment from external bank
            return processInwardPayment(optionalToAccount.get(), paymentRequest);
        } else if (!optionalToAccount.isPresent()) {
            // Outward payment to external bank
                return processOutwardPayment(optionalFromAccount.get(), paymentRequest);
        } else {
            // Inter-Account transfer
            return processInternalTransfer(optionalFromAccount.get(), optionalToAccount.get(), paymentRequest);
        }
    }


    private TransactionResponse processInwardPayment(Account toAccount, PaymentRequest paymentRequest) {
        BigDecimal toAccountBalance = toAccount.getBalance() == null ? BigDecimal.ZERO : toAccount.getBalance();

        BigDecimal interest = BigDecimal.ZERO;

        if (toAccount.getAccountType() == SAVINGS_ACCOUNT) {
            interest = paymentRequest.getAmount().add(toAccountBalance).multiply(SAVINGS_INTEREST);
        }
        // Update account balances
        toAccount.setBalance(toAccountBalance.add(interest).add(paymentRequest.getAmount()));

        this.accountService.updateAccounts(Arrays.asList(toAccount));

        return buildResponse(paymentRequest, TransactionType.CREDIT, Boolean.FALSE);
    }

    public TransactionResponse processOutwardPayment(Account fromAccount, PaymentRequest paymentRequest) {
        BigDecimal accountBalance = fromAccount.getBalance() == null ? BigDecimal.ZERO : fromAccount.getBalance();
        BigDecimal paymentCharge = paymentRequest.getAmount().multiply(TRANSACTION_CHARGE);

        paymentRequest.toBuilder()
                .amount(paymentCharge)
                .build();

        fromAccount.setBalance(accountBalance.subtract(paymentCharge.add(paymentRequest.getAmount())));

        this.accountService.updateAccounts(Arrays.asList(fromAccount));

        return buildResponse(paymentRequest, TransactionType.DEBIT, Boolean.TRUE);
    }


    private TransactionResponse processInternalTransfer(Account fromAccount, Account toAccount, PaymentRequest paymentRequest) {

        BigDecimal newFromAccountBalance = fromAccount.getBalance() == null ? BigDecimal.ZERO : fromAccount.getBalance();
        BigDecimal toAccountBalance = toAccount.getBalance() == null ? BigDecimal.ZERO : toAccount.getBalance();

        BigDecimal paymentCharge = BigDecimal.ZERO;
        BigDecimal interest = BigDecimal.ZERO;

        if (toAccount.getAccountType() == SAVINGS_ACCOUNT) {
            // Record saving interest charge
            interest = paymentRequest.getAmount().add(toAccountBalance).multiply(SAVINGS_INTEREST);
       /*       PaymentRequest request = paymentRequest.toBuilder()
                    .amount(interest)
                    .build();
        */
        }

        if (fromAccount.getAccountType() == CURRENT_ACCOUNT) {
            // Charge the paying account only if it's current account
            paymentCharge = paymentRequest.getAmount().multiply(TRANSACTION_CHARGE);
   /*         PaymentRequest request = paymentRequest.toBuilder()
                    .amount(paymentCharge)
                    .build(); */
//            transactionService.record(toAccount, "SYSTEM_ACC", DEBIT, request);
        }

        // Update account balances
        fromAccount.setBalance(newFromAccountBalance.subtract(paymentCharge.add(paymentRequest.getAmount())));
        toAccount.setBalance(toAccountBalance.add(interest).add(paymentRequest.getAmount()));

        this.accountService.updateAccounts(new ArrayList<>(Arrays.asList(fromAccount, toAccount)));

        return buildResponse(paymentRequest, TransactionType.DEBIT, Boolean.TRUE);
    }

    @Override
    public List<TransactionDto> retrieveTransactions() {
        return transactionsMapper.toDtoList(transactionRepository.findAll());
    }

    private TransactionResponse buildResponse(PaymentRequest paymentRequest, TransactionType transactionType, Boolean internal) {
        String amount = NumberFormat.getCurrencyInstance(new Locale("en", "ZA"))
                .format(paymentRequest.getAmount().doubleValue());

        return TransactionResponse.builder()
                .fromAccount(paymentRequest.getFromAccount())
                .toAccount(paymentRequest.getToAccount())
                .amount(amount)
                .reference(paymentRequest.getReference())
                .transactionType(transactionType)
                .internal(internal)
                .build();
    }
}
