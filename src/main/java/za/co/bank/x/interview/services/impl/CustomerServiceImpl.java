package za.co.bank.x.interview.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import za.co.bank.x.interview.exceptions.GeneralException;
import za.co.bank.x.interview.services.impl.entities.Account;
import za.co.bank.x.interview.services.impl.entities.Customer;
import za.co.bank.x.interview.enums.AccountType;
import za.co.bank.x.interview.mapper.CustomerMapper;
import za.co.bank.x.interview.models.request.CustomerRequest;
import za.co.bank.x.interview.models.response.CustomerDto;
import za.co.bank.x.interview.repositories.CustomerRepository;
import za.co.bank.x.interview.services.AccountService;
import za.co.bank.x.interview.services.CustomerService;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static za.co.bank.x.interview.utils.CommonConstants.CUSTOMER_EXISTS;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountService accountService;

    private final CustomerMapper customerMapper;

    @Override
    public CustomerDto onboardCustomer(CustomerRequest request) {
        // Check if customer already exists
        Optional<Customer> existingCustomer = customerRepository.findByIdNumber(request.getIdNumber());
        if(existingCustomer.isPresent()) {
            throw new GeneralException(CUSTOMER_EXISTS);
        }

        // Convert from request to customer entity
        Customer customer = customerMapper.fromCustomerRequest(request);
        Customer savedCustomer = this.customerRepository.save(customer);

        log.info("Saved customer {}", savedCustomer);

        // Create default accounts for customer
         Account savingsAccount = accountService.createAccount(AccountType.SAVINGS_ACCOUNT,customer);
         Account currentAccount =  accountService.createAccount(AccountType.CURRENT_ACCOUNT, customer);

         savedCustomer.setAccounts(Stream.of(savingsAccount,currentAccount).collect(Collectors.toSet()));

        return customerMapper.toDto(customerRepository.save(savedCustomer));
    }
}
