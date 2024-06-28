package esgi.projet_ddd_gr6_kisseimedemo.infrastructure.payment;

import esgi.projet_ddd_gr6_kisseimedemo.model.payment.BankAccount;

public class PaymentImpl implements IPayment {

    @Override
    public boolean pay(BankAccount from, BankAccount to) {
        System.out.println("payed");
        return true;
    }
}


