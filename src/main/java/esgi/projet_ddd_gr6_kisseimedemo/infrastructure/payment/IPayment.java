package esgi.projet_ddd_gr6_kisseimedemo.infrastructure.payment;

import esgi.projet_ddd_gr6_kisseimedemo.model.payment.BankAccount;

public interface IPayment {
    boolean pay(BankAccount from, BankAccount to);
}
