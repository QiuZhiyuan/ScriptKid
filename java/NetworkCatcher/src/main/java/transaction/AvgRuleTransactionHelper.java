package transaction;

import entry.StockDailyEntry;
import provider.StockDataProvider;
import statistics.TrendHolder;

import java.util.List;

/**
 * 根据X日均线决定买入卖出
 *
 * TODO developing
 */
public class AvgRuleTransactionHelper extends TransactionBaseHelper {

    private static final int AVG_IMPROVE = 30;

    private static final int AVG_BASE = 100;

    private static final float BUY_RATE = 0.5f;

    private static final float SELL_RATE = 1f;

    private static final float cashValue = 100000;

    private final float[] avg100;

    private final TrendHolder trendHolderImprove;

    private final float[] avg350;

    private final TrendHolder trendHolderBase;

    public AvgRuleTransactionHelper(String stockCode) {
        super(cashValue, stockCode);
        List<StockDailyEntry> entryList = getStockDailyEntryList();
        avg100 = new float[entryList.size()];
        avg350 = new float[entryList.size()];
        for (int i = 0; i < entryList.size(); i++) {
            avg100[i] = entryList.get(i).avgPriceMap.get(AVG_IMPROVE);
            avg350[i] = entryList.get(i).avgPriceMap.get(AVG_BASE);

        }
        trendHolderImprove = new TrendHolder(avg100);
        trendHolderImprove.printTrendList();
        trendHolderBase = new TrendHolder(avg350);
        trendHolderBase.printTrendList();
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
    public List<StockDailyEntry> getStockDailyEntryList() {
        return StockDataProvider.i().getStockDailyEntry(stockCode);
    }

    @Override
    public boolean shouldBuy(int index) {
        return isAvgIntersection(index, TrendHolder.TrendType.UPWARD, TrendHolder.TrendType.UPWARD);
    }

    @Override
    public boolean shouldSell(int index) {
        return isAvgIntersection(index, TrendHolder.TrendType.DOWNWARD, TrendHolder.TrendType.UPWARD);
    }

    /**
     * 判断是否是日均线的交叉点（上升或下降）
     */
    private boolean isAvgIntersection(int index, TrendHolder.TrendType type1, TrendHolder.TrendType type2) {
        StockDailyEntry entry = getComputeLineEntry(index);
        if (entry == null) {
            return false;
        }
//        if (Utils.isEqualByTolerance(entry.avgPriceMap.get(AVG_IMPROVE), entry.avgPriceMap.get(AVG_BASE), Utils.EQUAL_TOLERANCE * 5)) {
        if (isCrossingAvg(index)) {
            TrendHolder.TrendType trend100 = trendHolderImprove.getTrendByIndex(index);
            TrendHolder.TrendType trend350 = trendHolderBase.getTrendByIndex(index);
            return trend100 == type1 && trend350 == type2;
        }
        return false;
    }

    private boolean isCrossingAvg(int index) {
        if (index < 1 || index >= getStockDailyEntryList().size() - 1) {
            return false;
        }
        StockDailyEntry entry1 = getComputeLineEntry(index - 1);
        StockDailyEntry entry2 = getComputeLineEntry(index + 1);
        return entry1.avgPriceMap.get(AVG_IMPROVE) < entry1.avgPriceMap.get(AVG_BASE) &&
                entry2.avgPriceMap.get(AVG_IMPROVE) > entry2.avgPriceMap.get(AVG_BASE);
    }
}
