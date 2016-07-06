package savings.model;

public interface PaybackPolicy {

    String getId();

    boolean isEligible(Account account, Purchase purchase);
}
