package savings.repository;

import savings.model.AccountIncome;
import savings.model.PaybackConfirmation;
import savings.model.Purchase;

public interface PaybackRepository {

    PaybackConfirmation save(AccountIncome income, Purchase purchase);
}
