package esgi.projet_ddd_gr6_kisseimedemo.model.payment;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BankAccount {
    private final String rib;
    private final UUID ownerId;

    public BankAccount(String rib, UUID ownerId) {
        this.rib = rib;
        this.ownerId = ownerId;
    }
}
