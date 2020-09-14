package entry;

import utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 阶段性平均值，如5日平均，10日平均，100日平均
 */
public class AvgStateEntry extends CsvLineEntry {

    public final Map<Integer, Float> avgMap = new HashMap<>();

    public AvgStateEntry(CsvLineEntry entry) {
        super(entry);
    }

    @Override
    public String toString() {
        return "AvgStateEntry{" +
                "date=" + Utils.dateToString(date) +
                ", avg=" + avgMap.toString() +
                ", price=" + closePrice +
                '}' + "\n";
    }
}
