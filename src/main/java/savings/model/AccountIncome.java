package savings.model;

import java.util.Set;

import org.joda.money.Money;

import common.math.Percentage;

public class AccountIncome {

    private String accountNumber;

    private Money amount;

    private Set<Distribution> distributions;

    public AccountIncome(String accountNumber, Money amount, Set<Distribution> distributions) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.distributions = distributions;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Money getAmount() {
        return amount;
    }

    public Set<Distribution> getDistributions() {
        return distributions;
    }

    public Distribution getDistribution(String objective) {
        for (Distribution distribution : distributions) {
            if (distribution.objective.equals(objective)) {
                return distribution;
            }
        }
        throw new IllegalArgumentException("No such distribution for '" + objective + "'");
    }

    public static class Distribution {

        private String objective;

        private Money amount;

        private Percentage allocation;

        private Money totalSavings ;

        public Distribution(String objective, Money amount, Percentage allocation, Money totalSavings) {
            this.objective = objective;
            this.amount = amount;
            this.allocation = allocation;
            this.totalSavings = totalSavings;
        }

        public String getObjective() {
            return objective;
        }

        public Money getAmount() {
            return amount;
        }

        public Percentage getAllocation() {
            return allocation;
        }

        public Money getTotalSavings() {
            return totalSavings;
        }

        public String toString() {
            return amount + " to '" + objective + "' (" + allocation + ")";
        }
    }

    public String toString() {
        return "Contribution of " + amount + " to account '" + accountNumber + "' distributed " + distributions;
    }
}
