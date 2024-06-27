package esgi.projet_ddd_gr6_kisseimedemo.model.delivery;

import esgi.projet_ddd_gr6_kisseimedemo.model.users.Client;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Order {
    private final UUID id = UUID.randomUUID();
    private final Trip trip;
    private final Client client;
    private final List<ElementOrder> listOfElements;
    OrderStatus statusOfOrder = OrderStatus.PENDING;
    OrderStatus statusOfOrderPayment = OrderStatus.PENDING;

    public Order(Trip trip, Client client, List<ElementOrder> listOfElements) {
        this.trip = trip;
        this.client = client;
        this.listOfElements = listOfElements;
    }
}
