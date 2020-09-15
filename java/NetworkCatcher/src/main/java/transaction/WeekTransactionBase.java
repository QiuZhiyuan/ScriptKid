package transaction;

import entry.ComputeLineEntry;
import entry.CsvLineEntry;
import entry.TransactionEntry;
import provider.DataProvider;
import utils.Utils;

import java.util.List;

/**
 * 测试交易策略，用于测试
 * 每周五买入，每周一卖出
 */
public class WeekTransactionBase extends TransactionBaseImpl {

    private static final float holdValue = 100000;

    private static final float BUY_RATE = 0.5f;

    private static final float SELL_RATE = 1f;

    public WeekTransactionBase(String stockCode) {
        super(holdValue, stockCode);
    }

    @Override
    public void start() {
        List<ComputeLineEntry> entryList = DataProvider.i().getComputeLines(stockCode);

        for (CsvLineEntry entry : entryList) {
            if (shouldBuy(entry)) {
                buy(entry, BUY_RATE);
            }
            if (shouldSell(entry)) {
                sell(entry, SELL_RATE);
            }
        }
        for (TransactionEntry entry : transactionList) {
            Utils.log(entry.toString());
        }
//        Utils.log("Tax cost:" + getTaxCost());
//        Utils.log("totalValue:" + getTotalValue(entryList.get(entryList.size() - 1)));

    }

    @Override
    public boolean shouldBuy(CsvLineEntry entry) {
        return entry.date.getDay() == 5;
    }

    @Override
    public boolean shouldSell(CsvLineEntry entry) {
        return entry.date.getDay() == 1;
    }
}
