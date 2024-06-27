package esgi.projet_ddd_gr6_kisseimedemo.model.payment;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Facture {
    private final UUID id = UUID.randomUUID();
    private final UUID orderId;
    private final BankAccount bankAccount;
    private FactureStatus factureStatus = FactureStatus.TO_BE_PAYED;

    public Facture(UUID orderId, BankAccount bankAccount) {
        this.orderId = orderId;
        this.bankAccount = bankAccount;
    }

    public boolean wasPayed() {
        return this.factureStatus.equals(FactureStatus.PAYED);
    }

}
