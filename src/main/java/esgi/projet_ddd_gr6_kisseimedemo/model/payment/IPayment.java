package esgi.projet_ddd_gr6_kisseimedemo.model.payment;

public interface IPayment {
    boolean pay(BankAccount from, BankAccount to);
}
