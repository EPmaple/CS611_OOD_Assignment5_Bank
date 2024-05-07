package account;

import utility.InputHandler;

import java.util.UUID;

// Singleton
public class ManageAccount extends Account {
    private static ManageAccount instance;
    private UUID id;
    private String name;
    private String password_hashing;
    public ManageAccount(UUID id, String name, String password_hashing) {
        this.id = id;
        this.name = name;
        this.password_hashing = password_hashing;
    }

    public ManageAccount get(){
        if (instance == null) {
            instance = new ManageAccount(UUID.randomUUID(), "admin", "zzhzzhzzh");
        }
        return instance;
    }
}
