package role;

public class Manager extends User {

    public Manager(String name){
        super(name, 1, EnumRole.Manager);
    }
}
