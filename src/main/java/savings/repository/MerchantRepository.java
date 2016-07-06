package savings.repository;

import savings.model.Merchant;

public interface MerchantRepository {

    Merchant findByNumber(String number);
}
