package entry;

import com.sun.istack.internal.NotNull;

import java.util.Date;

public class StockDailyEntry {

    @NotNull
    private final String code;
    @NotNull
    private final String name;
    @NotNull
    private final String time;
    @NotNull
    private final float price;

    public StockDailyEntry(@NotNull String code, @NotNull String name, @NotNull String time, @NotNull float price) {
        this.code = code;
        this.name = name;
        this.time = time;
        this.price = price;
    }


    @Override
    public String toString() {
        return "code:" + code + " name:" + name + " time:" + time + " price:" + price;
    }
}
