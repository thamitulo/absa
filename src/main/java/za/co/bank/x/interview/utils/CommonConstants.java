package za.co.bank.x.interview.utils;

import java.math.BigDecimal;

public interface CommonConstants {
    BigDecimal SAVINGS_JOINING_BONUS = BigDecimal.valueOf(500.00);
    BigDecimal TRANSACTION_CHARGE = BigDecimal.valueOf(0.0005);
    BigDecimal SAVINGS_INTEREST = BigDecimal.valueOf(0.005);
    BigDecimal TRANSFER_LIMIT = new BigDecimal(100000);

    String BANK_X_ACCOUNT_NUMBER = "000000000001";
    String SAVINGS_JOINING_REFERENCE = "SAVINGS JOINING BONUS";

    String CUSTOMER_EXISTS = "Customer already exists";

    String INVALID_ACCOUNTS = "INVALID OR UNKNOWN ACCOUNTS";
}
