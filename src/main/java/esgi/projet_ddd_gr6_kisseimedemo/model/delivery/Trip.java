package esgi.projet_ddd_gr6_kisseimedemo.model.delivery;

import esgi.projet_ddd_gr6_kisseimedemo.model.users.Provider;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Trip {
    private final String id = "123";
    private final Provider provider;
    private final TripInfos tripInfos;
    private TripStatus tripStatus = TripStatus.LOADING;
    private List<Order> listOfOrders = new ArrayList<>();

    public Trip(Provider provider, TripInfos tripInfos) {
        this.provider = provider;
        this.tripInfos = tripInfos;
    }

    public void cancel() {
        boolean tripHasOrders = this.listOfOrders.size() > 0;

        this.tripStatus = TripStatus.CANCELED;
        if (tripHasOrders) {
            this.listOfOrders = this.listOfOrders.stream().peek(Order::cancelOrder).toList();
        }
    }

    public void addOrder(Order order) {
        this.listOfOrders.add(order);
    }
}
