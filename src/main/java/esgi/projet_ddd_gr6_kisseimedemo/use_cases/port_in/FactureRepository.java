package esgi.projet_ddd_gr6_kisseimedemo.use_cases.port_in;

import esgi.projet_ddd_gr6_kisseimedemo.model.payment.Facture;

import java.util.UUID;

public interface FactureRepository {
    Facture findfactureByOrderId(UUID id);
    void update(Facture facture);
}
