package savings;

import common.sql.TestDataSourceFactory;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import savings.model.PaybackConfirmation;
import savings.model.Purchase;
import savings.repository.impl.RepositoryConfiguration;
import savings.service.PaybackBookKeeper;
import savings.service.impl.ServiceConfiguration;

import javax.sql.DataSource;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.Assertions.assertThat;
import static org.joda.money.CurrencyUnit.EUR;
import static org.joda.time.DateTime.now;

// TODO #0 remove @Ignore to enable the test
// TODO #1 remove inheritance and annotate the test to be run using SpringJUnit4ClassRunner
// TODO #2 change context configuration to use internal configuration class instead of XML files
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PaybackBookKeeperModuleTest{

    // TODO #3 use internal configuration class to import required production configs and set up test data source
    // TODO #4 enable property source based configuration to set up test data source utilizing META-INF/application.properties

    @Autowired
    PaybackBookKeeper bookKeeper;

    String creditCardNumber = "1234123412341234";

    String merchantNumber = "1234567890";

    Purchase purchase = new Purchase(Money.of(EUR, 100L), creditCardNumber, merchantNumber, now());

    @Configuration
//    @Import({RepositoryConfiguration.class, ServiceConfiguration.class})
    @ComponentScan(basePackages = {"savings.repository.impl", "savings.service.impl"})
    @PropertySource("classpath:/META-INF/application.properties")
    static class contextConfiguration {

        @Value("${test.schema.location}")
        private Resource schemaLocation;
        @Value("${test.data.location}")
        private Resource dataLocation;

        @Bean
        public FactoryBean<DataSource> dataSourceFactoryBean(){
            return new TestDataSourceFactory(schemaLocation, dataLocation);
        }

    }


    @Before
    public void setUp() {
        // TODO #5 use annotation based field injections to deliver this test dependency from test context
//        bookKeeper = applicationContext.getBean("paybackBookKeeper", PaybackBookKeeper.class);
    }

    @Test
    public void shouldThrowWhenAccountNotFound() {
        purchase = new Purchase(Money.of(EUR, 100L), "4321432143214321", merchantNumber, now());

        catchException(bookKeeper, EmptyResultDataAccessException.class).registerPaybackFor(purchase);

        assertThat(caughtException()).isNotNull();
    }

    @Test
    public void shouldThrowWhenMerchantNotFound() {
        purchase = new Purchase(Money.of(EUR, 100L), creditCardNumber, "1111111111", now());

        catchException(bookKeeper, EmptyResultDataAccessException.class).registerPaybackFor(purchase);

        assertThat(caughtException()).isNotNull();
    }

    @Test
    public void shouldRegisterPayback() {
        PaybackConfirmation confirmation = bookKeeper.registerPaybackFor(purchase);

        assertThat(confirmation.getNumber()).isNotNull();
        assertThat(confirmation.getIncome().getAmount()).isEqualTo(Money.of(EUR, 6L));
        assertThat(confirmation.getIncome().getDistributions()).hasSize(2);
        assertThat(confirmation.getIncome().getDistribution("Glock").getAmount()).isEqualTo(Money.of(EUR, 3L));
        assertThat(confirmation.getIncome().getDistribution("M60").getAmount()).isEqualTo(Money.of(EUR, 3L));
    }
}
