package za.co.bank.x.interview.rules;

import org.springframework.stereotype.Component;
import za.co.bank.x.interview.exceptions.GeneralException;
import za.co.bank.x.interview.services.impl.entities.Account;
import za.co.bank.x.interview.utils.CommonConstants;

import java.math.BigDecimal;

import static za.co.bank.x.interview.enums.AccountType.CURRENT_ACCOUNT;
import static za.co.bank.x.interview.utils.CommonConstants.INSUFFICIENT_FUNDS;
import static za.co.bank.x.interview.utils.CommonConstants.INVALID_TRANSACTION;

public class RulesProcess {

    public static boolean canTransact(Account account){
        boolean canTransact;

        if(account.getAccountType() == CURRENT_ACCOUNT ){
            if(account.getBalance().compareTo(BigDecimal.ZERO) < 0){
                throw new GeneralException(INSUFFICIENT_FUNDS);
            }
            else {
                canTransact = true;
            }
        }
        else {
            throw new GeneralException(INVALID_TRANSACTION);
        }
        return canTransact;
    }
}
