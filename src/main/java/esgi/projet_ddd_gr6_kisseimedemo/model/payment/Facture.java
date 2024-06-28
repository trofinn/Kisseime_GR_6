package esgi.projet_ddd_gr6_kisseimedemo.model.payment;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class Facture {
    private final UUID id = UUID.randomUUID();
    private final UUID orderId;
    private final BankAccount from;
    private final BankAccount to;
    private FactureStatus factureStatus = FactureStatus.TO_BE_PAYED;

    public Facture(UUID orderId, BankAccount bankAccount, BankAccount to) {
        this.orderId = orderId;
        this.from = bankAccount;
        this.to = to;
    }

    public boolean wasPayed() {
        return this.factureStatus.equals(FactureStatus.PAYED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Facture facture)) return false;
        return Objects.equals(id, facture.id) && Objects.equals(orderId, facture.orderId) && Objects.equals(from, facture.from) && Objects.equals(to, facture.to) && factureStatus == facture.factureStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, from, to, factureStatus);
    }

    @Override
    public String toString() {
        return "Facture{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", from=" + from +
                ", to=" + to +
                ", factureStatus=" + factureStatus +
                '}';
    }
}
