package esgi.projet_ddd_gr6_kisseimedemo.model.users;

import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.Order;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Client {
    private final String name;
    private List<Order> listOfOrders = new ArrayList<>();

    public Client(String name) {
        this.name = name;
    }
}
