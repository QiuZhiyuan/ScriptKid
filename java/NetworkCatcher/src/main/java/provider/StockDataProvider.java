package provider;

import com.sun.istack.internal.Nullable;
import entry.StockDailyEntry;

import java.util.*;

public class StockDataProvider {

    private static int CACHE_MAX_SIZE = 100;

    private volatile static StockDataProvider instance;

    public static StockDataProvider i() {
        if (instance == null) {
            synchronized (StockDataProvider.class) {
                if (instance == null) {
                    instance = new StockDataProvider();
                }
            }
        }
        return instance;
    }

    private final DailyDataLruCache lruCache = new DailyDataLruCache(CACHE_MAX_SIZE);

    @Nullable
    public List<StockDailyEntry> getStockDailyEntry(String stockCode) {
        return lruCache.fromKey(stockCode);
    }
}
