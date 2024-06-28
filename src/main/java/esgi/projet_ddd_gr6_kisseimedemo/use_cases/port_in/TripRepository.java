package esgi.projet_ddd_gr6_kisseimedemo.use_cases.port_in;


import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.Trip;

import java.util.Optional;

public interface TripRepository {
    Optional<Trip> findtripById(String tripId);
    void updateTrip(Trip updatedTrip);
}
