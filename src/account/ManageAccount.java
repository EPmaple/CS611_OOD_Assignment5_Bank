package account;

import utility.InputHandler;

import java.util.UUID;

// Singleton
public class ManageAccount extends Account {
    private static ManageAccount instance;
    public ManageAccount(UUID id, String name, String password_hashing) {
        super(id, name, password_hashing, EnumAccount.Manage);
    }

    public ManageAccount get(){
        if (instance == null) {
            instance = new ManageAccount(UUID.randomUUID(), "admin", "zzhzzhzzh");
        }
        return instance;
    }
}
