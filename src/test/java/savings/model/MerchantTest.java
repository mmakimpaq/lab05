package savings.model;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.fest.assertions.Assertions.assertThat;
import static org.joda.money.CurrencyUnit.EUR;
import static org.joda.time.DateTime.now;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.joda.money.Money;
import org.junit.Test;

import common.math.Percentage;

public class MerchantTest {

    Account account = new Account("123456789", "Jane & John Smith")
            .withObjective("Glock", Percentage.of("50%"))
            .withObjective("M60", Percentage.of("50%"));

    String merchantNumber = "1234567890";

    Purchase purchase = new Purchase(Money.of(EUR, 100L), "1234123412341234", merchantNumber, now());

    PaybackPolicy paybackPolicy = mock(PaybackPolicy.class);

    Merchant merchant = new Merchant(merchantNumber, "Guns & Bombs", Percentage.of("6%"), paybackPolicy);

    @Test
    public void shouldCalculatePaybackIfEligible() {
        when(paybackPolicy.isEligible(account, purchase)).thenReturn(TRUE);

        Money payback = merchant.calculatePaybackFor(account, purchase);

        assertThat(payback).isEqualTo(Money.of(EUR, 6));
    }

    @Test
    public void shouldReturnZeroIfNotEligible() {
        when(paybackPolicy.isEligible(account, purchase)).thenReturn(FALSE);

        Money payback = merchant.calculatePaybackFor(account, purchase);

        assertThat(payback).isEqualTo(Money.zero(EUR));
    }
}
