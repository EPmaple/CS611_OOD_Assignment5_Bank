package account;

import java.util.UUID;

public class SavingAccount extends Account {
    public SavingAccount(UUID id, String name, String password_hashing) {
        super(id, name, password_hashing, EnumAccount.Saving);
    }
}
