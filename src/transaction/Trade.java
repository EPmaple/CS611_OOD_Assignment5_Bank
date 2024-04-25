package transaction;

import java.util.UUID;

public class Trade extends Transaction {
    private UUID stockId; // stock id
    private int quantity; // number of stock bought
    private UUID buyerId; // account id
    private UUID sellerId; // account id

    public Trade(UUID stockId, int quantity, UUID buyerId, UUID sellerId) {
        super();
        this.stockId = stockId;
        this.quantity = quantity;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
    }
}
