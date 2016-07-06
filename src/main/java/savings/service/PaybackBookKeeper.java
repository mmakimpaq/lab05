package savings.service;

import savings.model.PaybackConfirmation;
import savings.model.Purchase;

public interface PaybackBookKeeper {

    /**
     * Register payback on an account for the purchase.
     *
     * For a purchase to be accepted it must have been made using a registered credit card of a valid account.
     * Also it must have been made from a merchant participating in the payback program.
     *
     * @param purchase a charge made to a credit card for a purchase.
     * @return confirmation of registered payback.
     */
    PaybackConfirmation registerPaybackFor(Purchase purchase);
}
