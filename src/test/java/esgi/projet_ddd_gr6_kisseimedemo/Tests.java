package esgi.projet_ddd_gr6_kisseimedemo;


import esgi.projet_ddd_gr6_kisseimedemo.infrastructure.payment.PaymentImpl;
import esgi.projet_ddd_gr6_kisseimedemo.infrastructure.repositories.FactureRepositoryAdapter;
import esgi.projet_ddd_gr6_kisseimedemo.infrastructure.repositories.TripRepositoryAdapter;
import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.ElementOrder;
import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.Order;
import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.Trip;
import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.TripInfos;
import esgi.projet_ddd_gr6_kisseimedemo.model.payment.BankAccount;
import esgi.projet_ddd_gr6_kisseimedemo.model.payment.Facture;
import esgi.projet_ddd_gr6_kisseimedemo.model.payment.FactureStatus;
import esgi.projet_ddd_gr6_kisseimedemo.model.users.Client;
import esgi.projet_ddd_gr6_kisseimedemo.model.users.Provider;
import esgi.projet_ddd_gr6_kisseimedemo.use_cases.CancelTrip;
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
    private TripRepositoryAdapter tripRepository;

    @Mock
    private FactureRepositoryAdapter factureRepository;

    @InjectMocks
    private CancelTrip cancelTripUseCase;

    @Mock
    private PaymentImpl payment;

    @Test
    public void updateTripStatusAndOrders() {

        // GIVEN
        Provider providerKisseime = new Provider("Kisseime");
        Client clientDriss = new Client("Driss");
        Trip trip = new Trip(providerKisseime, new TripInfos("Paris", "Marseille"));
        ElementOrder elementOrder = new ElementOrder("TV", 5.0);
        Order order = new Order(trip, clientDriss, List.of(elementOrder));
        trip.setListOfOrders(List.of(order));
        BankAccount providerBankAccount = new BankAccount("FR301042", providerKisseime.getId());
        BankAccount clientBankAccount = new BankAccount("FR301042", clientDriss.getId());
        Facture facture = new Facture(order.getId(), clientBankAccount, providerBankAccount);
        facture.setFactureStatus(FactureStatus.PAYED);

        // WHEN
        Mockito.when(tripRepository.findtripById(trip.getId())).thenReturn(Optional.of(trip));
        var result = cancelTripUseCase.updateTripStatusAndOrders(trip.getId());

        // THEN
        Assertions.assertEquals(result.getTripStatus().name(), "CANCELED");
        Assertions.assertEquals(result.getListOfOrders().get(0).getStatusOfOrder().name(), "CANCELED");
        Assertions.assertEquals(result.getListOfOrders().get(0).getStatusOfOrderPayment().name(), "REFUNDED");
    }

    @Test
    public void updateTripStatus() {

        //GIVEN
        Provider providerKisseime = new Provider("Kisseime");
        Trip trip = new Trip(providerKisseime, new TripInfos("Paris", "Marseille"));

        // WHEN
        Mockito.when(tripRepository.findtripById(trip.getId())).thenReturn(Optional.of(trip));
        var result = cancelTripUseCase.updateTripStatusAndOrders(trip.getId());

        // THEN
        Assertions.assertEquals(result.getTripStatus().name(), "CANCELED");
    }

    @Test
    public void getUpdatedAndRefundedFacturesShouldPass() {
        // GIVEN
        Provider providerKisseime = new Provider("Kisseime");
        Client clientDriss = new Client("Driss");
        Trip trip = new Trip(providerKisseime, new TripInfos("Paris", "Marseille"));
        ElementOrder elementOrder = new ElementOrder("TV", 5.0);
        Order order = new Order(trip, clientDriss, List.of(elementOrder));
        trip.setListOfOrders(List.of(order));
        BankAccount providerBankAccount = new BankAccount("FR301042", providerKisseime.getId());
        BankAccount clientBankAccount = new BankAccount("FR301042", clientDriss.getId());
        Facture facture = new Facture(order.getId(), clientBankAccount, providerBankAccount);
        facture.setFactureStatus(FactureStatus.PAYED);

        // WHEN
        Mockito.when(payment.pay(facture.getFrom(), facture.getTo())).thenReturn(true);
        cancelTripUseCase.getUpdatedAndRefundedFactures(List.of(facture));

        // THEN
        Assertions.assertEquals(facture.getFactureStatus().name(), "REFUNDED");

    }

    @Test
    public void getUpdatedAndRefundedFacturesShouldFail() {
        // GIVEN
        Provider providerKisseime = new Provider("Kisseime");
        Client clientDriss = new Client("Driss");
        Trip trip = new Trip(providerKisseime, new TripInfos("Paris", "Marseille"));
        ElementOrder elementOrder = new ElementOrder("TV", 5.0);
        Order order = new Order(trip, clientDriss, List.of(elementOrder));
        trip.setListOfOrders(List.of(order));
        BankAccount providerBankAccount = new BankAccount("FR301042", providerKisseime.getId());
        BankAccount clientBankAccount = new BankAccount("FR301042", clientDriss.getId());
        Facture facture = new Facture(order.getId(), clientBankAccount, providerBankAccount);
        facture.setFactureStatus(FactureStatus.PAYED);

        // WHEN
        Mockito.when(payment.pay(facture.getFrom(), facture.getTo())).thenReturn(false);

        // THEN
        Assertions.assertEquals(facture.getFactureStatus().name(), "PAYED");
        Assertions.assertThrows(RuntimeException.class, () -> cancelTripUseCase.getUpdatedAndRefundedFactures(List.of(facture)));

    }


}
