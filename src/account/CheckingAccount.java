package account;

import java.util.UUID;

public class CheckingAccount extends Account {

    public CheckingAccount(UUID id, String name, String password_hashing) {
        super(id, name, password_hashing, EnumAccount.Checking);
    }
}
