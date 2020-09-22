package transaction;

import entry.ComputeLineEntry;
import entry.ComputeLineEntry;
import entry.TransactionEntry;
import provider.DataProvider;
import utils.Utils;

import java.util.List;

/**
 * 测试交易策略，用于测试
 * 每周五买入，每周一卖出
 */
public class WeekTransactionHelper extends TransactionBaseHelper {

    private static final float holdValue = 100000;

    private static final float BUY_RATE = 0.5f;

    private static final float SELL_RATE = 1f;

    public WeekTransactionHelper(String stockCode) {
        super(holdValue, stockCode);
    }

    @Override
    public void start() {
        for (int i = 0; i < getComputeLineSize(); i++) {
            if (shouldBuy(i)) {
                buy(i, BUY_RATE);
            }
            if (shouldSell(i)) {
                sell(i, SELL_RATE);
            }
        }
        logInfo();
    }

    @Override
    public List<ComputeLineEntry> getComputeLineEntryList() {
        return DataProvider.i().getComputeLines(stockCode);
    }

    @Override
    public boolean shouldBuy(int index) {
        return getComputeLineEntry(index).date.getDay() == 5;
    }

    @Override
    public boolean shouldSell(int index) {
        return getComputeLineEntry(index).date.getDay() == 1;
    }
}
