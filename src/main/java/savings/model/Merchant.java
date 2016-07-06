package savings.model;

import static java.math.RoundingMode.HALF_EVEN;
import static org.joda.money.CurrencyUnit.EUR;

import org.joda.money.Money;

import common.math.Percentage;
import common.model.Entity;

public class Merchant extends Entity {

    private String number;

    private String name;

    private Percentage payback;

    private PaybackPolicy paybackPolicy;

    public Merchant(String number, String name, Percentage payback, PaybackPolicy paybackPolicy) {
        this.number = number;
        this.name = name;
        this.payback = payback;
        this.paybackPolicy = paybackPolicy;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Percentage getPayback() {
        return payback;
    }

    public Money calculatePaybackFor(Account account, Purchase purchase) {
        if (paybackPolicy.isEligible(account, purchase)) {
            return purchase.getAmount().multipliedBy(payback.asBigDecimal(), HALF_EVEN);
        } else {
            return Money.zero(EUR);
        }
    }

    @Override
    public String toString() {
        return "Merchant number = '" + number + "', name = '" + name + "', payback = " + payback;
    }
}
