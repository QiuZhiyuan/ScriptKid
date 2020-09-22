package provider;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import entry.StockDailyEntry;
import entry.CsvLineEntry;
import statistics.ComputeUtils;
import utils.Utils;

import java.util.*;

public class DataProvider {


    private volatile static DataProvider instance;

    public static DataProvider i() {
        if (instance == null) {
            synchronized (DataProvider.class) {
                if (instance == null) {
                    instance = new DataProvider();
                }
            }
        }
        return instance;
    }

    private Map<String, List<StockDailyEntry>> computeResultMap = new HashMap<>();

    public void startCompute() {
    }

    public synchronized void setCsvLines(@NotNull String stockCode, @NotNull List<CsvLineEntry> csvLines) {
        // 按日期升序排列
        csvLines.sort(Comparator.comparing(o -> o.date));
        computeResultMap.put(stockCode, ComputeUtils.computeStateAvg(csvLines));
        for (StockDailyEntry entry : computeResultMap.get(stockCode)) {
            Utils.log(entry.toString());
        }
    }

    @Nullable
    public List<StockDailyEntry> getComputeLines(String stockCode) {
        return new ArrayList<>(computeResultMap.get(stockCode));
    }

}
