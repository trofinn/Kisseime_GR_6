package esgi.projet_ddd_gr6_kisseimedemo.infrastructure;

import esgi.projet_ddd_gr6_kisseimedemo.model.payment.BankAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankAccountRepository {
    private List<BankAccount> bankAccountList = new ArrayList<>();

    public BankAccount findBankAccountByUserId(UUID id) {
        return null;
    }
}
