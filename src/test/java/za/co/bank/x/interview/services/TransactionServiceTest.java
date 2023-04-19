package za.co.bank.x.interview.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import za.co.bank.x.interview.enums.AccountType;
import za.co.bank.x.interview.exceptions.GeneralException;
import za.co.bank.x.interview.mapper.TransactionsMapper;
import za.co.bank.x.interview.models.request.PaymentRequest;
import za.co.bank.x.interview.models.response.TransactionResponse;
import za.co.bank.x.interview.repositories.TransactionRepository;
import za.co.bank.x.interview.services.impl.TransactionsServiceImpl;
import za.co.bank.x.interview.services.impl.entities.Account;
import za.co.bank.x.interview.services.impl.entities.Customer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionServiceTest {

    @MockBean
    private AccountService accountService;

    @MockBean
    private TransactionRepository transactionRepository;

    private TransactionsServiceImpl transactionService;

    TransactionsMapper transactionsMapper = Mappers.getMapper(TransactionsMapper.class);

    @BeforeEach
    public void setup() {
        transactionService = new TransactionsServiceImpl(accountService, transactionRepository, transactionsMapper);
    }

    @Test
    public void test_successful_inward_transaction() {
        Customer customer = customerStub("1234567890123");
        Account toAccount = accountStub("67890", "330", AccountType.CURRENT_ACCOUNT, customer);

        when(accountService.getAccountByAccountNumber("67890")).thenReturn(Optional.of(toAccount));
        TransactionResponse transactionResponse = transactionService.processTransaction(paymentRequestStub("external-account", toAccount.getAccountNumber(), BigDecimal.valueOf(1000), "inward payment"));

        assertNotNull(transactionResponse);
        assertEquals("R 1,000.00", transactionResponse.getAmount());
        assertEquals("inward payment", transactionResponse.getReference());
        assertEquals("external-account", transactionResponse.getFromAccount());
        assertEquals("67890", transactionResponse.getToAccount());

        ArgumentCaptor<List<Account>> toAccountArg = ArgumentCaptor.forClass(List.class);
        verify(accountService).updateAccounts(toAccountArg.capture());
        List<Account> updatedAccounts = toAccountArg.getValue();

        Account currentAccount = updatedAccounts.stream().filter(a -> a.getAccountType() == AccountType.CURRENT_ACCOUNT).findFirst().get();

        // Assert that balance has been updated
        assertEquals("67890", currentAccount.getAccountNumber());
        assertEquals(new BigDecimal("1330"), currentAccount.getBalance());
    }

    @Test
    public void test_successful_outward_transaction() {
        Customer customer = customerStub("1234567890123");
        Account fromAccount = accountStub("from-account", "1500", AccountType.CURRENT_ACCOUNT, customer);

        when(accountService.getAccountByAccountNumber("from-account")).thenReturn(Optional.of(fromAccount));
        TransactionResponse transactionResponse = transactionService.processTransaction(paymentRequestStub(fromAccount.getAccountNumber(), "external-account", BigDecimal.valueOf(1000), "outward payment"));

        assertNotNull(transactionResponse);
        assertEquals("R 1,000.00", transactionResponse.getAmount());
        assertEquals("outward payment", transactionResponse.getReference());
        assertEquals("external-account", transactionResponse.getToAccount());
        assertEquals("from-account", transactionResponse.getFromAccount());

        ArgumentCaptor<List<Account>> fromAccountArg = ArgumentCaptor.forClass(List.class);
        verify(accountService).updateAccounts(fromAccountArg.capture());
        List<Account> updatedAccounts = fromAccountArg.getValue();

        Account currentAccount = updatedAccounts.stream().filter(a -> a.getAccountType() == AccountType.CURRENT_ACCOUNT).findFirst().get();

        // Assert that balance has been updated and balance reduced
        assertEquals("from-account", currentAccount.getAccountNumber());
        assertEquals(new BigDecimal("499.50"), currentAccount.getBalance().setScale(2));

    }

    @Test
    public void test_successful_inter_account_transfer_transaction() {
        Customer customer = customerStub("1234567890123");
        Account fromAccount = accountStub("12345", "1500", AccountType.SAVINGS_ACCOUNT, customer);
        Account toAccount = accountStub("67890", "0", AccountType.CURRENT_ACCOUNT, customer);

        when(accountService.getAccountByAccountNumber("12345")).thenReturn(Optional.of(fromAccount));
        when(accountService.getAccountByAccountNumber("67890")).thenReturn(Optional.of(toAccount));

        TransactionResponse transactionResponse = transactionService.processTransaction(paymentRequestStub(fromAccount.getAccountNumber(), "external-account", BigDecimal.valueOf(1000), "outward payment"));

        assertNotNull(transactionResponse);
        assertEquals("R 1,000.00", transactionResponse.getAmount());
        assertEquals("outward payment", transactionResponse.getReference());
        assertEquals("external-account", transactionResponse.getToAccount());
        assertEquals("12345", transactionResponse.getFromAccount());

    }


    @Test
    public void test_invalid_transfer_from_savings_account() {
        Customer customer = customerStub("1234567890123");;
        Account fromAccount = accountStub("12345", "500", AccountType.SAVINGS_ACCOUNT, customer);
        when(accountService.getAccountByAccountNumber("12345")).thenReturn(Optional.of(fromAccount));

        GeneralException exception = assertThrows(GeneralException.class,
                () -> transactionService.processTransaction(paymentRequestStub(fromAccount.getAccountNumber(), "external-account", BigDecimal.valueOf(1000), "outward payment")));

        assertNotNull(exception);
        assertEquals("Transaction not allowed", exception.getMessage());

    }

    private Customer customerStub(String idNumber){
        return Customer.builder().idNumber(idNumber).build();
    }
    private Account accountStub(String accountNumber, String amount, AccountType accountType, Customer customer) {
        return Account.builder().balance(new BigDecimal(amount))
                .accountNumber(accountNumber).accountType(accountType).customer(customer)
                .build();
    }

    private PaymentRequest paymentRequestStub(String fromAccount, String toAccount, BigDecimal amount, String reference) {
        return PaymentRequest.builder()
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(amount)
                .reference(reference)
                .build();
    }
}
