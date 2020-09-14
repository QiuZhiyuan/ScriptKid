package entry;

import utils.Utils;

/**
 * 阶段性平均值，如5日平均，10日平均，100日平均
 */
public class AvgStateEntry extends CsvLineEntry {

    public final int state;

    public final float avg;

    public AvgStateEntry(int state, float avg, CsvLineEntry entry) {
        super(entry);
        this.state = state;
        this.avg = avg;
    }

    @Override
    public String toString() {
        return "AvgStateEntry{" +
                "date=" + Utils.dateToString(date) +
                ", state=" + state +
                ", avg=" + avg +
                '}' + "\n";
    }
}
