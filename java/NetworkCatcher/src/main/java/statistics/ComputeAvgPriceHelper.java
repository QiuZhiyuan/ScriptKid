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

    // 平均阶段，5日、100日、350日
    private static final int[] AVG_STATE = new int[]{5, 100, 350};

    private Map<Integer, List<AvgStateEntry>> avgStateMap = new HashMap<>();

    public ComputeAvgPriceHelper(List<CsvLineEntry> lineEntry) {
        sortByDate(lineEntry);
        for (int state : AVG_STATE) {
            computeStateAvg(state, lineEntry);
        }
        Utils.log(avgStateMap.get(AVG_STATE[1]).toString());
    }

    private void sortByDate(@NotNull List<CsvLineEntry> lineEntryList) {
        lineEntryList.sort(Comparator.comparing(o -> o.date));
    }

    private void computeStateAvg(int state, @NotNull List<CsvLineEntry> lineEntryList) {
        List<AvgStateEntry> avgStateEntryList = new ArrayList<>();
        for (int i = state - 1; i < lineEntryList.size(); i++) {
            float avg;
            int size = avgStateEntryList.size();
            if (size > 0) {
                avg = avgStateEntryList.get(size - 1).avg + (lineEntryList.get(i).closePrice -
                        lineEntryList.get(i - state).closePrice) / state;
            } else {
                float sum = 0;
                for (int j = i; j > i - state; j--) {
                    sum += lineEntryList.get(j).closePrice;
                }
                avg = sum / state;
            }
            avgStateEntryList.add(new AvgStateEntry(state, avg, lineEntryList.get(i)));
        }
        avgStateMap.put(state, avgStateEntryList);
    }
}
