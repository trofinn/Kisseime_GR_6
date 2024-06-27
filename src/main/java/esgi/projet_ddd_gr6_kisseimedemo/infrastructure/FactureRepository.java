package esgi.projet_ddd_gr6_kisseimedemo.infrastructure;

import esgi.projet_ddd_gr6_kisseimedemo.model.payment.Facture;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactureRepository {
    private List<Facture> factureList = new ArrayList<>();

    public Facture findfactureByOrderId(UUID id) {
        return null;
    }

    public void update(Facture facture) {
    }
}
