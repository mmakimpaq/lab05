package savings.model;

import static org.joda.money.CurrencyUnit.EUR;

import org.joda.money.Money;

import common.math.Percentage;
import common.model.Entity;

public class Objective extends Entity {

    private String name;

    private Percentage allocation;

    private Money savings = Money.zero(EUR);

    public Objective(String name, Percentage allocation) {
        this(name, allocation, Money.zero(EUR));
    }

    public Objective(String name, Percentage allocation, Money savings) {
        this.name = name;
        this.allocation = allocation;
        this.savings = savings;
    }

    public String getName() {
        return name;
    }

    public Percentage getAllocation() {
        return allocation;
    }

    public Money getSavings() {
        return savings;
    }

    public void credit(Money amount) {
        savings.plus(amount);
    }

    @Override
    public String toString() {
        return "Objective of " + name + " with allocation " + allocation + " and total savings=" + savings;
    }
}
