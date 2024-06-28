package esgi.projet_ddd_gr6_kisseimedemo.use_cases;

import esgi.projet_ddd_gr6_kisseimedemo.infrastructure.repositories.FactureRepositoryAdapter;
import esgi.projet_ddd_gr6_kisseimedemo.infrastructure.repositories.TripRepositoryAdapter;
import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.Order;
import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.Trip;
import esgi.projet_ddd_gr6_kisseimedemo.model.payment.Facture;
import esgi.projet_ddd_gr6_kisseimedemo.model.payment.FactureStatus;
import esgi.projet_ddd_gr6_kisseimedemo.infrastructure.payment.IPayment;
import esgi.projet_ddd_gr6_kisseimedemo.use_cases.port_in.FactureRepository;
import esgi.projet_ddd_gr6_kisseimedemo.use_cases.port_in.TripRepository;

import java.util.List;
import java.util.Optional;

public class CancelTrip {
    private final TripRepository tripRepository;
    private final IPayment paymentService;
    private final FactureRepository factureRepository;


    public CancelTrip(TripRepositoryAdapter tripRepository, IPayment paymentService, FactureRepositoryAdapter factureRepository) {
        this.tripRepository = tripRepository;
        this.paymentService = paymentService;
        this.factureRepository = factureRepository;
    }

    public void cancelTrip(String tripId) {
        Trip trip = updateTripStatusAndOrders(tripId);
        var allFactures = refundFactures(trip.getListOfOrders());
        var facturesToBeRefunded = getPayedFactures(allFactures);
        var facturesNotPayed = getNotPayedFactures(allFactures);

        updateAndRefundFactures(facturesToBeRefunded);
        onlyUpdateFactureStatus(facturesNotPayed);

    }


    public Trip updateTripStatusAndOrders(String tripId) {
        Optional<Trip> optionalTrip = tripRepository.findtripById(tripId);

        if (optionalTrip.isPresent()) {
            final Trip trip = optionalTrip.get();
            trip.cancel();
            tripRepository.updateTrip(trip);
            return trip;
        }
        return null;
    }

    public List<Facture> refundFactures(List<Order> orders) {
        return orders.stream().map(order -> factureRepository.findfactureByOrderId(order.getId())).toList();
    }

    public boolean refundOrder(Facture facture) {
        return paymentService.pay(facture.getFrom(), facture.getTo());
    }

    public List<Facture> getPayedFactures(List<Facture> allFactures) {
        return allFactures.stream().filter(Facture::wasPayed).toList();
    }

    public List<Facture> getNotPayedFactures(List<Facture> allFactures) {
        return allFactures.stream().filter(facture -> !facture.wasPayed()).toList();
    }

    public List<Facture> updateAndRefundFactures(List<Facture> payedFactures) {
        return payedFactures.stream().map(facture -> {
            var resultRefund = refundOrder(facture);
            if (resultRefund) {
                facture.setFactureStatus(FactureStatus.REFUNDED);
            }
            else {
                throw new RuntimeException();
            }
            return facture;
        }).toList();
    }


    public List<Facture> onlyUpdateFactureStatus(List<Facture> notPayedFactures) {
        return notPayedFactures.stream().peek(facture -> facture.setFactureStatus(FactureStatus.REFUNDED)).toList();
    }


}

