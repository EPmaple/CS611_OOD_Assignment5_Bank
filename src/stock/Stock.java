package stock;

import java.util.UUID;

public class Stock {
    private String name;
    private float price;
    private UUID id;
    private int quantity;


    public Stock(String name, float price, int quantity) {
        this.name = name;
        this.price = price;
        this.id = UUID.randomUUID();
        this.quantity = quantity;
    }
}
