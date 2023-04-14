package za.co.bank.x.interview.enums;

public enum AccountType {
    CURRENT_ACCOUNT("Current Account"), SAVINGS_ACCOUNT("Savings Account");
    private final String desc;
    AccountType(String descr) {
        this.desc =descr;
    }

    @Override
    public String toString() {
        return desc;
    }
}
