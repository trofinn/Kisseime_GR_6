package esgi.projet_ddd_gr6_kisseimedemo.use_cases.trips;

import esgi.projet_ddd_gr6_kisseimedemo.infrastructure.BankAccountRepository;
import esgi.projet_ddd_gr6_kisseimedemo.infrastructure.FactureRepository;
import esgi.projet_ddd_gr6_kisseimedemo.infrastructure.TripRepository;
import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.Order;
import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.Trip;
import esgi.projet_ddd_gr6_kisseimedemo.model.payment.Facture;
import esgi.projet_ddd_gr6_kisseimedemo.model.payment.FactureStatus;
import esgi.projet_ddd_gr6_kisseimedemo.model.payment.IPayment;

import java.util.List;
import java.util.Optional;

public class CancelTrip {
    private final TripRepository tripRepository;
    private final IPayment paymentService;
    private final BankAccountRepository bankAccountRepository;
    private final FactureRepository factureRepository;


    public CancelTrip(TripRepository tripRepository, IPayment paymentService, BankAccountRepository bankAccountRepository, FactureRepository factureRepository) {
        this.tripRepository = tripRepository;
        this.paymentService = paymentService;
        this.bankAccountRepository = bankAccountRepository;
        this.factureRepository = factureRepository;
    }

    public Trip cancelTrip(String tripId) {
        Optional<Trip> optionalTrip = tripRepository.findtripById(tripId);

        if (optionalTrip.isPresent()) {
            final Trip trip = optionalTrip.get();
            trip.cancel();
            tripRepository.updateTrip(trip);
            return trip;
        }
        return null;
    }

    public List<Order> refundOrders(Trip trip) {
        return trip.getListOfOrders().stream().map(order -> {
                    var factureForOrder = factureRepository.findfactureByOrderId(order.getId());
                    if (factureForOrder.wasPayed()) {
                        var paymentSuccessfull = paymentService.pay(
                                bankAccountRepository.findBankAccountByUserId(trip.getProvider().getId()),
                                bankAccountRepository.findBankAccountByUserId(order.getClient().getId()));

                        if (paymentSuccessfull) {
                            factureForOrder.setFactureStatus(FactureStatus.REFUNDED);
                            factureRepository.update(factureForOrder);
                        }
                    }
                    return order;
                }
        ).toList();
    }
}
