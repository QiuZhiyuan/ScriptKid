package entry;

import java.util.Date;

public class TransactionEntry {

    public enum Action {
        BUY,
        SELL,
    }

    public final Date date;

    public final float price;

    public final int amount;

    public final float tax;

    public final Action action;


    public TransactionEntry(Date date, float price, int amount, float tax, Action action) {
        this.date = date;
        this.price = price;
        this.amount = amount;
        this.tax = tax;
        this.action = action;
    }

    public float cashChanged() {
        return (action == Action.BUY ? -1 : 1) * price * amount - tax;
    }

    @Override
    public String toString() {
        return "TransactionEntry{" +
                "date=" + date +
                ", price=" + price +
                ", amount=" + amount +
                ", tax=" + tax +
                ", cashChanged=" + cashChanged() +
                ", action=" + action +
                '}';
    }
}
