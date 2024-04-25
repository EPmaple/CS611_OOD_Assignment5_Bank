package transaction;

import java.util.UUID;

public class Deposit extends Transaction {
    private UUID acountId; // account id
    private float amount; // dollar

    public Deposit(UUID accountId, float amount){
        super();
        this.acountId = accountId;
        this.amount = amount;
    }
}
