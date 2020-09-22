package provider;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import entry.ComputeLineEntry;
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

    private Map<String, List<ComputeLineEntry>> computeResultMap = new HashMap<>();

    public void startCompute() {
    }

    public synchronized void setCsvLines(@NotNull String stockCode, @NotNull List<CsvLineEntry> csvLines) {
        // 按日期升序排列
        csvLines.sort(Comparator.comparing(o -> o.date));
        computeResultMap.put(stockCode, ComputeUtils.computeAvgs(csvLines));
        for (ComputeLineEntry entry : computeResultMap.get(stockCode)) {
            Utils.log(entry.toString());
        }
    }

    @Nullable
    public List<ComputeLineEntry> getComputeLines(String stockCode) {
        return new ArrayList<>(computeResultMap.get(stockCode));
    }

}
