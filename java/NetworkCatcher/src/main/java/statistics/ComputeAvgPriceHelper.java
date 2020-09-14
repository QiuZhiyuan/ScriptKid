package statistics;

import com.sun.istack.internal.NotNull;
import entry.AvgStateEntry;
import entry.CsvLineEntry;
import utils.Utils;

import java.util.*;

/**
 * 计算平均价格
 */
public class ComputeAvgPriceHelper {

    private final List<CsvLineEntry> lineEntryList;

    public ComputeAvgPriceHelper(List<CsvLineEntry> lineEntryList) {
        this.lineEntryList = lineEntryList;
    }

    public List<AvgStateEntry> computeStateAvgPrice(int[] avgStates) {
        sortByDate(lineEntryList);
        return doComputeStateAvgPrice(avgStates);
    }

    private void sortByDate(@NotNull List<CsvLineEntry> lineEntryList) {
        lineEntryList.sort(Comparator.comparing(o -> o.date));
    }

    private List<AvgStateEntry> doComputeStateAvgPrice(int[] avgStates) {
        long start = System.currentTimeMillis();
        List<AvgStateEntry> avgStateEntryList = new ArrayList<>();
        for (int i = 0; i < lineEntryList.size(); i++) {
            AvgStateEntry avgStateEntry = new AvgStateEntry(lineEntryList.get(i));
            for (Integer state : avgStates) {
                float avg = getClosePriceAvg(i, state);
                avgStateEntry.avgMap.put(state, avg);
            }
            avgStateEntryList.add(avgStateEntry);
        }
        Utils.log("Compute duration:" + (System.currentTimeMillis() - start));
        return avgStateEntryList;
    }

    private float getClosePriceAvg(int start, int state) {
        if (start - state + 1 < 0) {
            return 0;
        }
        float sum = 0;
        for (int i = start - state + 1; i <= start; i++) {
            sum += lineEntryList.get(i).closePrice;
        }
        return sum / state;
    }
}
