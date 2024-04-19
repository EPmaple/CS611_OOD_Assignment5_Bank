package role;

import java.util.UUID;

public class User {
    private String name;
    private UUID id;
    private EnumRole role;

    public User(String name, EnumRole role){
        this.name = name;
        this.id = UUID.randomUUID();
        this.role = role;
    }
}
