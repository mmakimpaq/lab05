package savings.model;

public class PaybackConfirmation {

    private String number;

    private AccountIncome income;

    public PaybackConfirmation(AccountIncome income) {
        this(null, income);
    }

    public PaybackConfirmation(String number, AccountIncome income) {
        this.number = number;
        this.income = income;
    }

    public AccountIncome getIncome() {
        return income;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Savings confirmation " + number;
    }
}
