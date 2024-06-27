package esgi.projet_ddd_gr6_kisseimedemo.infrastructure;

import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.Trip;
import esgi.projet_ddd_gr6_kisseimedemo.model.users.Provider;

import java.util.List;
import java.util.Optional;

public class TripRepository {
    private List<Trip> listOfTrips = List.of(
            new Trip(
                    new Provider("Kisseime"),
                    "Paris",
                    "Marseille"
            )
    );

    public Optional<Trip> findtripById(String tripId) {
        return listOfTrips.stream().filter(trip -> trip.getId().equals(tripId)).findFirst();
    }

    public void updateTrip(Trip updatedTrip) {
        listOfTrips.stream().map(trip -> {
            if(trip.getId() == updatedTrip.getId()) {
                return updatedTrip;
            }
            return trip;
        });
    }
}
