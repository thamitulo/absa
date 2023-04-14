package za.co.bank.x.interview.utils;

import za.co.bank.x.interview.enums.AccountType;

public class CommonUtils {

    public static String accountNumberGenerator(AccountType accountType){

        String accountNumber = accountType == AccountType.SAVINGS_ACCOUNT ? generateSavingsAccountNumber() : generateCurrentAccountNumber();

        return accountNumber;
    }

    private static String generateCurrentAccountNumber() {

        return "currentAccount";
    }

    private static String generateSavingsAccountNumber() {

        return "savingsAccount";
    }
}
