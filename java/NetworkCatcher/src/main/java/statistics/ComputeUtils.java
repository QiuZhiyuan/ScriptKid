package statistics;

import entry.ComputeLineEntry;
import entry.CsvLineEntry;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ComputeUtils {

    // 平均阶段，5日、100日、350日
    public static final int[] AVG_STATE = new int[]{5, 100, 350};

    /**
     * 计算日平均
     */
    public static List<ComputeLineEntry> computeAvgs(List<CsvLineEntry> lineEntryList) {
        long start = System.currentTimeMillis();
        List<ComputeLineEntry> computeLineEntryList = new ArrayList<>();
        for (int i = 0; i < lineEntryList.size(); i++) {
            ComputeLineEntry computeLineEntry = new ComputeLineEntry(lineEntryList.get(i));
            for (Integer state : AVG_STATE) {
                float avg = Utils.formatPrice(getClosePriceAvg(i, state, lineEntryList));
                computeLineEntry.avgPriceMap.put(state, avg);
            }
            computeLineEntryList.add(computeLineEntry);
        }
        Utils.log("Compute duration:" + (System.currentTimeMillis() - start));
        return computeLineEntryList;
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
