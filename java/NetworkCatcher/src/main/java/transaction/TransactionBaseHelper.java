package transaction;

import com.sun.istack.internal.Nullable;
import entry.StockDailyEntry;
import entry.TransactionEntry;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易策略基类，用于制定交易策略，记录每次交易的内容，计算现金与持仓
 * 需要遍历列表，在适当的时候进行交易
 */
public abstract class TransactionBaseHelper {

    public static final float TRANSACTION_TAX_RATE = 0.0028f;

    protected final String stockCode;

    protected List<TransactionEntry> transactionList = new ArrayList<>();

    private final List<StockDailyEntry> stockDailyEntryList;

    // 现金
    protected float cashValue;

    public TransactionBaseHelper(float cashValue, String stockCode) {
        this.cashValue = cashValue;
        this.stockCode = stockCode;
        this.stockDailyEntryList = getStockDailyEntryList();
    }

    /**
     * @param index   当天价格
     * @param buyRate 买入仓位百分比
     * @return 现金剩余
     */
    public float buy(int index, float buyRate) {
        final StockDailyEntry entry = stockDailyEntryList.get(index);
        final int amount = Math.round(cashValue * buyRate / entry.closePrice);
        final float value = amount * entry.closePrice;
        final float tax = value * TRANSACTION_TAX_RATE;
        TransactionEntry transaction = new TransactionEntry(entry.date, entry.closePrice, amount, tax, TransactionEntry.Action.BUY);
        transactionList.add(transaction);
        cashValue += transaction.cashChanged();
        return getTotalValue(entry);
    }

    /**
     * @param index    当天价格
     * @param sellRate 卖出仓位百分比
     * @return 现金剩余
     */
    public float sell(int index, float sellRate) {
        final StockDailyEntry entry = stockDailyEntryList.get(index);
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
    public float getTotalValue(StockDailyEntry entry) {
        if (entry == null) {
            return 0;
        }
        return cashValue + getRemainAmount() * entry.closePrice;
    }

    @Nullable
    protected StockDailyEntry getComputeLineEntry(int index) {
        if (index < 0 || index >= stockDailyEntryList.size()) {
            return null;
        }
        return stockDailyEntryList.get(index);
    }

    protected int getComputeLineSize() {
        return stockDailyEntryList.size();
    }

    protected void logInfo() {
        for (int i = 0; i < transactionList.size(); i++) {
            logTransaction(transactionList.get(i));
        }
        Utils.log("Total transaction count:" + transactionList.size());
        Utils.log("Tax cost:" + getTaxCost());
        Utils.log("totalValue:" + getTotalValue(getComputeLineEntry(getComputeLineSize() - 1)));
    }

    protected void logTransaction(TransactionEntry entry) {
        Utils.log(entry.toString());
    }

    public abstract void start();

    public abstract List<StockDailyEntry> getStockDailyEntryList();

    public abstract boolean shouldBuy(int index);

    public abstract boolean shouldSell(int index);
}
