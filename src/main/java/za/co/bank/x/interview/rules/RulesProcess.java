package za.co.bank.x.interview.rules;

import org.springframework.stereotype.Component;
import za.co.bank.x.interview.services.impl.entities.Account;

import static za.co.bank.x.interview.enums.AccountType.CURRENT_ACCOUNT;

@Component
public class RulesProcess {

    private boolean canTransact(Account account){
        boolean canTransact = false;

        if(account.getAccountType() == CURRENT_ACCOUNT){
            canTransact = true;
        }

        return canTransact;
    }
}
