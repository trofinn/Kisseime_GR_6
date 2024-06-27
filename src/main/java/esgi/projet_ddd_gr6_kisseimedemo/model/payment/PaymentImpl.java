package esgi.projet_ddd_gr6_kisseimedemo.model.payment;

public class PaymentImpl implements IPayment {

    @Override
    public boolean pay(BankAccount from, BankAccount to) {
        System.out.println("payed");
        return true;
    }
}


