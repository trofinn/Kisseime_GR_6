package esgi.projet_ddd_gr6_kisseimedemo.use_cases.trips;

import esgi.projet_ddd_gr6_kisseimedemo.infrastructure.TripRepository;
import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.OrderStatus;
import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.Trip;
import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.TripStatus;

import java.util.Optional;

public class CancelTrip {
    private final TripRepository tripRepository;

    public CancelTrip(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public Trip cancelTrip(String tripId) {
        Optional<Trip> optionalTrip = tripRepository.findtripById(tripId);

        if(optionalTrip.isPresent()) {
            final Trip trip = optionalTrip.get();

            boolean tripHasOrders = trip.getListOfOrders().size() > 0;

            if(!tripHasOrders) {
                trip.setTripStatus(TripStatus.CANCELED);
            }
            else {
                var listOfOrders = trip.getListOfOrders();
                var modifiedList = listOfOrders.stream().peek(order -> order.setStatusOfOrder(OrderStatus.CANCELED)).toList();
                trip.setListOfOrders(modifiedList);
            }
            tripRepository.updateTrip(trip);
            return trip;
        }
        return null;
    }
}
