package statistics;

import entry.StockDailyEntry;
import utils.Utils;

import java.util.List;

public class ComputeUtils {

    // 平均阶段，5日、100日、350日
    public static final int[] AVG_STATE = new int[]{5, 30, 100, 350};

    /**
     * 计算日平均
     */
    public static List<StockDailyEntry> computeStateAvg(List<StockDailyEntry> lineEntryList) {
        for (int i = 0; i < lineEntryList.size(); i++) {
            lineEntryList.get(i).avgPriceMap.clear();
            for (Integer state : AVG_STATE) {
                float avg = Utils.formatPrice(getClosePriceAvg(i, state, lineEntryList));
                lineEntryList.get(i).avgPriceMap.put(state, avg);
            }
        }
        return lineEntryList;
    }

    private static float getClosePriceAvg(int start, int state, List<StockDailyEntry> lineEntryList) {
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
