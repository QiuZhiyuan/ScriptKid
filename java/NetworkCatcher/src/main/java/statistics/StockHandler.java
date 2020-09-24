package statistics;

import com.sun.istack.internal.NotNull;
import entry.StockDailyEntry;
import provider.StockDataProvider;
import transaction.AvgRuleTransactionHelper;
import utils.Utils;

import java.util.List;

public class StockHandler {

    public StockHandler() {
    }

    @NotNull
    public void handleStockByCode(@NotNull final String stockCode) {
        Utils.log("Start handle stock code:" + stockCode);


        List<StockDailyEntry> dailyEntryList = StockDataProvider.i().getStockDailyEntry(stockCode);
        if (dailyEntryList != null) {
            Utils.log("Get from provider:" + dailyEntryList.size());
//            Utils.log(dailyEntryList.get(0).toString());
//            new WeekTransactionHelper(stockCode).start();
            new AvgRuleTransactionHelper(stockCode).start();
        } else {
            Utils.log("Parsed result is null");
        }
    }
}
