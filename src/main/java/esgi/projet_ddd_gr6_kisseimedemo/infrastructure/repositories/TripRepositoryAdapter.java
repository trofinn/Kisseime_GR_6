package esgi.projet_ddd_gr6_kisseimedemo.infrastructure.repositories;

import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.Trip;
import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.TripInfos;
import esgi.projet_ddd_gr6_kisseimedemo.model.users.Provider;
import esgi.projet_ddd_gr6_kisseimedemo.use_cases.port_in.TripRepository;

import java.util.List;
import java.util.Optional;

public class TripRepositoryAdapter implements TripRepository {
    private List<Trip> listOfTrips = List.of(
            new Trip(
                    new Provider("Kisseime"),
                    new TripInfos("Paris", "Marseille")
            )
    );

    public Optional<Trip> findtripById(String tripId) {
        return listOfTrips.stream().filter(trip -> trip.getId().equals(tripId)).findFirst();
    }

    public void updateTrip(Trip updatedTrip) {
        listOfTrips.stream().map(trip -> {
            if (trip.getId() == updatedTrip.getId()) {
                return updatedTrip;
            }
            return trip;
        });
    }
}
