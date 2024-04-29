package role;

import java.util.UUID;

public class User {
    protected String name;
    protected UUID id;
    protected EnumRole role;

    public User(String name, EnumRole role){
        this.name = name;
        this.id = UUID.randomUUID();
        this.role = role;
    }
    public String get_name(){
        return name;
    }
    public UUID get_id(){
        return id;
    }
    public void set_id(UUID newid){
        id = newid;
    }
}
