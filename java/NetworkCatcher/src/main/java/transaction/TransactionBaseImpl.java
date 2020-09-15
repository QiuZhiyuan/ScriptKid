package transaction;

import entry.CsvLineEntry;
import entry.TransactionEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易策略基类，用于制定交易策略，记录每次交易的内容，计算现金与持仓
 * 需要遍历列表，在适当的时候进行交易
 */
public abstract class TransactionBaseImpl {

    public static final float TRANSACTION_TAX_RATE = 0.0028f;

    protected final String stockCode;

    protected List<TransactionEntry> transactionList = new ArrayList<>();

    // 现金
    protected float cashValue;

    public TransactionBaseImpl(float cashValue, String stockCode) {
        this.cashValue = cashValue;
        this.stockCode = stockCode;
    }

    /**
     * @param entry   当天价格
     * @param buyRate 买入仓位百分比
     * @return 现金剩余
     */
    public float buy(CsvLineEntry entry, float buyRate) {
        final int amount = Math.round(cashValue * buyRate / entry.closePrice);
        final float value = amount * entry.closePrice;
        final float tax = value * TRANSACTION_TAX_RATE;
        TransactionEntry transaction = new TransactionEntry(entry.date, entry.closePrice, amount, tax, TransactionEntry.Action.BUY);
        transactionList.add(transaction);
        cashValue += transaction.cashChanged();
        return getTotalValue(entry);
    }

    /**
     * @param entry    当天价格
     * @param sellRate 卖出仓位百分比
     * @return 现金剩余
     */
    public float sell(CsvLineEntry entry, float sellRate) {
        final int amount = Math.round(getRemainAmount() * sellRate);
        final float value = entry.closePrice * amount;
        final float tax = value * TRANSACTION_TAX_RATE;
        TransactionEntry transaction = new TransactionEntry(entry.date, entry.closePrice, amount, tax, TransactionEntry.Action.SELL);
        transactionList.add(transaction);
        cashValue += transaction.cashChanged();
        return getTotalValue(entry);
    }

    public int getRemainAmount() {
        int amount = 0;
        for (TransactionEntry entry : transactionList) {
            if (entry.action == TransactionEntry.Action.BUY) {
                amount += entry.amount;
            } else if (entry.action == TransactionEntry.Action.SELL) {
                amount -= entry.amount;
            }
        }
        return amount;
    }

    public float getCashValue() {
        return cashValue;
    }

    public float getTaxCost() {
        float totalTax = 0;
        for (TransactionEntry entry : transactionList) {
            totalTax += entry.tax;
        }
        return totalTax;
    }

    /**
     * 总价值
     *
     * @return 现金+持仓
     */
    public float getTotalValue(CsvLineEntry entry) {
        return cashValue + getRemainAmount() * entry.closePrice;
    }

    public abstract void start();

    public abstract boolean shouldBuy(CsvLineEntry entry);

    public abstract boolean shouldSell(CsvLineEntry entry);
}
