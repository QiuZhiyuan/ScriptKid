package statistics;

import entry.StockDailyEntry;
import entry.CsvLineEntry;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ComputeUtils {

    // 平均阶段，5日、100日、350日
    public static final int[] AVG_STATE = new int[]{5, 30, 100, 350};

    /**
     * 计算日平均
     */
    public static List<StockDailyEntry> computeStateAvg(List<CsvLineEntry> lineEntryList) {
        List<StockDailyEntry> stockDailyEntryList = new ArrayList<>();
        for (int i = 0; i < lineEntryList.size(); i++) {
            StockDailyEntry stockDailyEntry = new StockDailyEntry(lineEntryList.get(i));
            for (Integer state : AVG_STATE) {
                float avg = Utils.formatPrice(getClosePriceAvg(i, state, lineEntryList));
                stockDailyEntry.avgPriceMap.put(state, avg);
            }
            stockDailyEntryList.add(stockDailyEntry);
        }
        return stockDailyEntryList;
    }

    private static float getClosePriceAvg(int start, int state, List<CsvLineEntry> lineEntryList) {
        if (start - state + 1 < 0) {
            return 0;
        }
        float sum = 0;
        // TODO 算法可以优化，avg = lastAvg + (newValue - lastValue) / state
        for (int i = start - state + 1; i <= start; i++) {
            sum += lineEntryList.get(i).closePrice;
        }

        return sum / state;
    }
}
