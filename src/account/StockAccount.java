package account;

import java.util.UUID;

public class StockAccount extends Account {
    public StockAccount(UUID id, String name, String password_hashing) {
        super(id, name, password_hashing, EnumAccount.Stock);
    }
}
