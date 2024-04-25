package transaction;

import java.util.UUID;

public class Loan extends Transaction {
    private UUID accountId; // stock id
    private float amount; // dollar


    public Loan(UUID accountId, float amount) {
        super();
        this.accountId = accountId;
        this.amount = amount;
    }
}
