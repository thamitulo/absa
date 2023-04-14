package za.co.bank.x.interview.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import za.co.bank.x.interview.services.impl.entities.Account;
import za.co.bank.x.interview.services.impl.entities.Customer;
import za.co.bank.x.interview.enums.AccountType;
import za.co.bank.x.interview.repositories.AccountsRepository;
import za.co.bank.x.interview.services.AccountService;
import za.co.bank.x.interview.utils.CommonConstants;
import za.co.bank.x.interview.utils.CommonUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountsRepository accountsRepository;

    @Override
    public Account createAccount(AccountType accountType, Customer customer) {
        Account.AccountBuilder accountBuilder = Account.builder()
                .accountNumber(CommonUtils.accountNumberGenerator(accountType))
                .accountType(accountType)
                .customer(customer);

        if(accountType == AccountType.SAVINGS_ACCOUNT) {
            accountBuilder.balance(CommonConstants.SAVINGS_JOINING_BONUS);
        }
        return accountBuilder.build();
    }

    @Override
    public Optional<Account> getAccountByAccountNumber(String accountNumber) {
        return this.accountsRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public void updateAccounts(List<Account> accounts) {
        this.accountsRepository.saveAllAndFlush(accounts);
    }
}
