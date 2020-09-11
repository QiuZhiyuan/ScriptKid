package entry;

import com.sun.istack.internal.NotNull;

public class StockDailyEntry {

    @NotNull
    private final String mCode;
    @NotNull
    private final String mName;
    @NotNull
    private final String mTime;
    @NotNull
    private final float mPrice;

    public StockDailyEntry(@NotNull String code, @NotNull String name, @NotNull String time, @NotNull float price) {
        this.mCode = code;
        this.mName = name;
        this.mTime = time;
        this.mPrice = price;
    }


    @Override
    public String toString() {
        return "code:" + mCode + " name:" + mName + " time:" + mTime + " price:" + mPrice;
    }
}
