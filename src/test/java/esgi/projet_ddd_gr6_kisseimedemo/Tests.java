package esgi.projet_ddd_gr6_kisseimedemo;


import esgi.projet_ddd_gr6_kisseimedemo.infrastructure.BankAccountRepository;
import esgi.projet_ddd_gr6_kisseimedemo.infrastructure.FactureRepository;
import esgi.projet_ddd_gr6_kisseimedemo.infrastructure.TripRepository;
import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.ElementOrder;
import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.Order;
import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.Trip;
import esgi.projet_ddd_gr6_kisseimedemo.model.payment.BankAccount;
import esgi.projet_ddd_gr6_kisseimedemo.model.payment.Facture;
import esgi.projet_ddd_gr6_kisseimedemo.model.payment.FactureStatus;
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

    @Mock
    private FactureRepository factureRepository;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private CancelTrip cancelTripUseCase;

    @Test
    public void deleteTripWithOrders() {

        // GIVEN
        Provider providerKisseime = new Provider("Kisseime");
        Client clientDriss = new Client("Driss");
        Trip trip = new Trip(providerKisseime, "Paris", "Marseille");
        ElementOrder elementOrder = new ElementOrder("TV", 5.0);
        Order order = new Order(trip, clientDriss, List.of(elementOrder));
        trip.setListOfOrders(List.of(order));
        Facture facture = new Facture(order.getId(), new BankAccount("FR1030341", clientDriss.getId()));
        BankAccount providerBankAccount = new BankAccount("FR301042", providerKisseime.getId());
        BankAccount clientBankAccount = new BankAccount("FR301042", clientDriss.getId());
        facture.setFactureStatus(FactureStatus.PAYED);

        // WHEN
        Mockito.when(tripRepository.findtripById(trip.getId())).thenReturn(Optional.of(trip));
        Mockito.when(factureRepository.findfactureByOrderId(order.getId())).thenReturn(facture);
        Mockito.when(bankAccountRepository.findBankAccountByUserId(providerKisseime.getId())).thenReturn(providerBankAccount);
        Mockito.when(bankAccountRepository.findBankAccountByUserId(providerKisseime.getId())).thenReturn(clientBankAccount);
        var result = cancelTripUseCase.cancelTrip(trip.getId());

        // THEN
        Assertions.assertEquals(result.getTripStatus().name(), "CANCELED");
        Assertions.assertEquals(result.getListOfOrders().get(0).getStatusOfOrder().name(), "CANCELED");
        Assertions.assertEquals(result.getListOfOrders().get(0).getStatusOfOrderPayment().name(), "REFUNDED");
        Assertions.assertEquals(facture.getFactureStatus().name(), "REFUNDED");
    }

    @Test
    public void deleteTripWithoutOrders() {

        //GIVEN
        Provider providerKisseime = new Provider("Kisseime");
        Client clientDriss = new Client("Driss");
        Trip trip = new Trip(providerKisseime, "Paris", "Marseille");

        // WHEN
        Mockito.when(tripRepository.findtripById(trip.getId())).thenReturn(Optional.of(trip));
        var result = cancelTripUseCase.cancelTrip(trip.getId());

        // THEN
        Assertions.assertEquals(result.getTripStatus().name(), "CANCELED");
    }
}
