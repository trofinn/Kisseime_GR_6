package esgi.projet_ddd_gr6_kisseimedemo;


import esgi.projet_ddd_gr6_kisseimedemo.infrastructure.TripRepository;
import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.ElementOrder;
import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.Order;
import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.Trip;
import esgi.projet_ddd_gr6_kisseimedemo.model.users.Client;
import esgi.projet_ddd_gr6_kisseimedemo.model.users.Provider;
import esgi.projet_ddd_gr6_kisseimedemo.use_cases.trips.CancelTrip;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class Tests {

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private CancelTrip cancelTripUseCase;

    @Test
    public void deleteTrip() {
        // GIVEN
        Provider providerKisseime = new Provider("Kisseime");
        Client clientDriss = new Client("Driss");
        Trip trip = new Trip(providerKisseime, "Paris", "Marseille");
        ElementOrder elementOrder = new ElementOrder("TV", 5.0);
        Order order = new Order(trip, clientDriss, List.of(elementOrder));

        // WHEN
        Mockito.when(tripRepository.findtripById("123")).thenReturn(Optional.of(trip));
        var result = cancelTripUseCase.cancelTrip(trip.getId());

        // THEN
        Assertions.assertEquals(result.getTripStatus().name(), "CANCELED");



    }
}
