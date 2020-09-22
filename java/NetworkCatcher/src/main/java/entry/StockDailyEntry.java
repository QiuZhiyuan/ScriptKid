package entry;

import utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 计算结果
 */
public class StockDailyEntry extends CsvLineEntry {

    // 阶段性平均值，如5日平均，10日平均，100日平均
    public final Map<Integer, Float> avgPriceMap = new HashMap<>();

    public StockDailyEntry(CsvLineEntry entry) {
        super(entry);
    }

    public StockDailyEntry(StockDailyEntry entry) {
        super(entry);
        avgPriceMap.putAll(entry.avgPriceMap);
    }

    @Override
    public String toString() {
        return "ComputeLineEntry{" +
                "date=" + Utils.dateToString(date) + " " + date.getDay() +
                ", avgPrice=" + avgPriceMap.toString() +
                ", price=" + closePrice +
                '}';
    }
}
