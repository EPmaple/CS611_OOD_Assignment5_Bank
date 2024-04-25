package role;

public class Customer extends User {
    public Customer(String name) {
        super(name, 5, EnumRole.Customer);
    }
}
