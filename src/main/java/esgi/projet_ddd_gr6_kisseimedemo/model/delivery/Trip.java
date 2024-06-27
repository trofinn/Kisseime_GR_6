package esgi.projet_ddd_gr6_kisseimedemo.model.delivery;

import esgi.projet_ddd_gr6_kisseimedemo.model.users.Provider;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Trip {
    private final String id = "123";
    private final Provider provider;
    private final String departure;
    private final String arrival;
    private TripStatus tripStatus = TripStatus.LOADING;
    private List<Order> listOfOrders = new ArrayList<>();

    public Trip(Provider provider, String departure, String arrival) {
        this.provider = provider;
        this.departure = departure;
        this.arrival = arrival;
    }
}
