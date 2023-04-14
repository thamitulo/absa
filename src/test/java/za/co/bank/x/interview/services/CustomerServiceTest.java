package za.co.bank.x.interview.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.omg.IOP.TransactionService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import za.co.bank.x.interview.exceptions.GeneralException;
import za.co.bank.x.interview.services.impl.entities.Customer;
import za.co.bank.x.interview.enums.IdType;
import za.co.bank.x.interview.mapper.CustomerMapper;
import za.co.bank.x.interview.models.request.CustomerRequest;
import za.co.bank.x.interview.models.response.AccountDto;
import za.co.bank.x.interview.models.response.CustomerDto;
import za.co.bank.x.interview.repositories.AccountsRepository;
import za.co.bank.x.interview.repositories.CustomerRepository;
import za.co.bank.x.interview.services.impl.CustomerServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static za.co.bank.x.interview.enums.AccountType.CURRENT_ACCOUNT;
import static za.co.bank.x.interview.enums.AccountType.SAVINGS_ACCOUNT;

@SpringBootTest
public class CustomerServiceTest {


}
