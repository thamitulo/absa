package za.co.bank.x.interview.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.omg.IOP.TransactionService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import za.co.bank.x.interview.enums.IdType;
import za.co.bank.x.interview.exceptions.GeneralException;
import za.co.bank.x.interview.mapper.CustomerMapper;
import za.co.bank.x.interview.models.request.CustomerRequest;
import za.co.bank.x.interview.models.response.AccountDto;
import za.co.bank.x.interview.models.response.CustomerDto;
import za.co.bank.x.interview.repositories.AccountsRepository;
import za.co.bank.x.interview.repositories.CustomerRepository;
import za.co.bank.x.interview.services.impl.AccountServiceImpl;
import za.co.bank.x.interview.services.impl.CustomerServiceImpl;
import za.co.bank.x.interview.services.impl.entities.Customer;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static za.co.bank.x.interview.enums.AccountType.CURRENT_ACCOUNT;
import static za.co.bank.x.interview.enums.AccountType.SAVINGS_ACCOUNT;
import static za.co.bank.x.interview.utils.CommonConstants.CUSTOMER_EXISTS;

@SpringBootTest
public class CustomerServiceTest {

    @MockBean
    private AccountService accountService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private AccountsRepository accountsRepository;

    @MockBean
    private TransactionService transactionService;

    CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setup() {
        accountService = new AccountServiceImpl(accountsRepository);
        when(customerRepository.save(any())).thenReturn(Customer.builder().build());
        customerService = new CustomerServiceImpl(customerRepository, accountService, customerMapper);
    }

    @Test
    public void createCustomer_success() {
        CustomerRequest customerRequest = customerRequestStub();

        when(customerRepository.findByIdNumber("1234567891045")).thenReturn(Optional.empty());
        when(customerRepository.save(any())).thenReturn(customerMapper.fromCustomerRequest(customerRequestStub()));
        CustomerDto response = customerService.onboardCustomer(customerRequest);

        //Assert Customer Created
        assertNotNull(response);
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals("john@gmail.com", response.getEmail());

        //Verify accounts were created
        assertEquals(2, response.getAccounts().size());
        assertTrue(response.getAccounts().stream().map(AccountDto::getAccountType).anyMatch(a -> a == SAVINGS_ACCOUNT));
        assertTrue(response.getAccounts().stream().map(AccountDto::getAccountType).anyMatch(a -> a == CURRENT_ACCOUNT));

        // Verify savings bonus was credited
        Optional<AccountDto> savingsAccount = response.getAccounts().stream().filter(a -> a.getAccountType() == SAVINGS_ACCOUNT).findFirst();
        assertTrue(savingsAccount.isPresent());
        assertEquals("500.0", savingsAccount.get().getBalance());
    }

    @Test
    public void testCreateExistingCustomer_throwsException() {
        CustomerRequest customerRequest = customerRequestStub();

        when(customerRepository.findByIdNumber("1234567891045")).thenReturn(Optional.of(Customer.builder().idNumber("1234567891045").build()));

        Exception exception = assertThrows(GeneralException.class,
                () -> customerService.onboardCustomer(customerRequest));

        assertNotNull(exception);
        assertEquals(CUSTOMER_EXISTS, exception.getMessage());
    }

    private CustomerRequest customerRequestStub() {
        return CustomerRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.now())
                .idType(IdType.RSA_ID)
                .idNumber("1234567891045")
                .cellphoneNumber("0780000001")
                .email("john@gmail.com")
                .build();
    }

    private Customer customerStub() {
        return Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.now())
                .idType(IdType.RSA_ID)
                .idNumber("1234567891045")
                .cellphoneNumber("0780000001")
                .email("john@gmail.com")
                .build();
    }

}
