package esgi.projet_ddd_gr6_kisseimedemo.infrastructure.repositories;

import esgi.projet_ddd_gr6_kisseimedemo.model.payment.Facture;
import esgi.projet_ddd_gr6_kisseimedemo.use_cases.port_in.FactureRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactureRepositoryAdapter implements FactureRepository {
    private List<Facture> factureList = new ArrayList<>();

    public Facture findfactureByOrderId(UUID id) {
        return null;
    }

    public void update(Facture facture) {
    }
}
