package savings.repository;

import savings.model.Account;

public interface AccountRepository {

    Account findByCreditCard(String creditCardNumber);

    void update(Account account);
}
