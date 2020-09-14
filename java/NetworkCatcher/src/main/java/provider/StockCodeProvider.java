package provider;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.List;

public class StockCodeProvider {

    private static StockCodeProvider instance;

    @NotNull
    public static StockCodeProvider i() {
        if (instance == null) {
            synchronized (StockCodeProvider.class) {
                if (instance == null) {
                    instance = new StockCodeProvider();
                }
            }
        }
        return instance;
    }

    public void downloadListFromNet() {

    }

    @Nullable
    public List<String> loadStockCodeList() {
        return null;
    }
}
