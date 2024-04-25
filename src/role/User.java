package role;

import account.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private String name;
    private UUID id;
    private EnumRole type;
    private List<Account> accounts;
    private int accounts_limit; // 3 <=> The user can have at most 3 accounts

    public User(String name, int accounts_limit, EnumRole type){
        this.name = name;
        this.id = UUID.randomUUID();
        this.accounts = new ArrayList<>();
        this.accounts_limit = accounts_limit;
        this.type = type;
    }
}
