package transaction;

import java.util.UUID;

public abstract class Transaction {
    private UUID id;
    public Transaction() {
        this.id = UUID.randomUUID();
    }
}
