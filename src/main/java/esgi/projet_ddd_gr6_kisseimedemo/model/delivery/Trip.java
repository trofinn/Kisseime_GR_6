package esgi.projet_ddd_gr6_kisseimedemo.model.delivery;

import esgi.projet_ddd_gr6_kisseimedemo.model.users.Provider;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trip trip)) return false;
        return Objects.equals(id, trip.id) && Objects.equals(provider, trip.provider) && Objects.equals(tripInfos, trip.tripInfos) && tripStatus == trip.tripStatus && Objects.equals(listOfOrders, trip.listOfOrders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, provider, tripInfos, tripStatus, listOfOrders);
    }
}
