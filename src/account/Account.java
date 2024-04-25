package account;

import java.util.UUID;

public class Account {
    private UUID id;
    private String name;
    private String password_hashing;
    private EnumAccount type;

    public Account(UUID id, String name, String password_hashing, EnumAccount type) {
        this.id = id;
        this.name = name;
        this.password_hashing = password_hashing;
    }

}
