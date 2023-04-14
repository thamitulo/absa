package za.co.bank.x.interview.services;

import za.co.bank.x.interview.services.impl.entities.Account;
import za.co.bank.x.interview.services.impl.entities.Customer;
import za.co.bank.x.interview.enums.AccountType;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account createAccount(AccountType accountType, Customer customer);

    Optional<Account> getAccountByAccountNumber(String accountNumber);

    void updateAccounts(List<Account> accounts);

}
