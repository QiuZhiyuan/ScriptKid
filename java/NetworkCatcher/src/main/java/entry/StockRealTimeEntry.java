package entry;

import com.sun.istack.internal.NotNull;

import java.util.Date;

public class StockRealTimeEntry {

    @NotNull
    public final String code;
    @NotNull
    public final String name;
    @NotNull
    public final String time;
    @NotNull
    public final float price;

    public StockRealTimeEntry(@NotNull String code, @NotNull String name, @NotNull String time, @NotNull float price) {
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
