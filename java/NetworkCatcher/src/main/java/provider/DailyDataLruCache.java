package provider;

import base.ArrLruCache;
import entry.StockDailyEntry;
import utils.Utils;

import java.util.List;

public class DailyDataLruCache extends ArrLruCache<String, List<StockDailyEntry>> {
    private final LocalFileReader fileReader;

    public DailyDataLruCache(int i) {
        super(i);
        fileReader = new LocalFileReader();
    }

    @Override
    protected List<StockDailyEntry> createEntry(String stockCode) {
        return fileReader.getStockDataFromFile(stockCode, Utils.END_DATE);
    }

    @Override
    protected void recycleEntry(String stockCode, List<StockDailyEntry> stockDailyEntries) {

    }
}
