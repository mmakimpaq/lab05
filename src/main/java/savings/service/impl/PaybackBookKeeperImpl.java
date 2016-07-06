package savings.service.impl;

import org.joda.money.Money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import savings.model.Account;
import savings.model.AccountIncome;
import savings.model.Merchant;
import savings.model.PaybackConfirmation;
import savings.repository.AccountRepository;
import savings.repository.MerchantRepository;
import savings.repository.PaybackRepository;
import savings.service.PaybackBookKeeper;
import savings.model.Purchase;

// TODO #1 mark as service component
@Service
public class PaybackBookKeeperImpl implements PaybackBookKeeper {

    private final AccountRepository accountRepository;

    private final MerchantRepository merchantRepository;

    private final PaybackRepository paybackRepository;

    // TODO #2 enable constructor arguments auto-wiring
    @Autowired
    public PaybackBookKeeperImpl(AccountRepository accountRepository, MerchantRepository merchantRepository,
            PaybackRepository paybackRepository) {
        this.accountRepository = accountRepository;
        this.merchantRepository = merchantRepository;
        this.paybackRepository = paybackRepository;
    }

    @Override
    public PaybackConfirmation registerPaybackFor(Purchase purchase) {
        Account account = accountRepository.findByCreditCard(purchase.getCreditCardNumber());
        Merchant merchant = merchantRepository.findByNumber(purchase.getMerchantNumber());
        Money paybackAmount = merchant.calculatePaybackFor(account, purchase);
        AccountIncome income = account.addPayback(paybackAmount);
        accountRepository.update(account);
        return paybackRepository.save(income, purchase);
    }
}
