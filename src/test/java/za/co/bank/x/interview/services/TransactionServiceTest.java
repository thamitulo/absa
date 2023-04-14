package za.co.bank.x.interview.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import za.co.bank.x.interview.mapper.TransactionsMapper;
import za.co.bank.x.interview.services.impl.entities.Account;
import za.co.bank.x.interview.services.impl.entities.Customer;
import za.co.bank.x.interview.enums.AccountType;
import za.co.bank.x.interview.models.request.PaymentRequest;
import za.co.bank.x.interview.models.response.TransactionResponse;
import za.co.bank.x.interview.repositories.TransactionRepository;
import za.co.bank.x.interview.services.impl.TransactionsServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TransactionServiceTest {

}
