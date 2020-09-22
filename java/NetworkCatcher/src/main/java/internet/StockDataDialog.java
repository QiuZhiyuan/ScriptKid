package internet;

import base.Callback;
import com.sun.istack.internal.NotNull;
import entry.StockDailyEntry;
import provider.UrlProvider;
import utils.Utils;

import java.io.File;
import java.util.List;

public class StockDataDialog {

    @NotNull
    private final NetworkManager networkManager;

    public StockDataDialog() {
        this.networkManager = new NetworkManager();
    }


    public void getDataFromMoney126(Callback<List<StockDailyEntry>> callback, String... stockCode) {
        final String path = UrlProvider.i().getMoney126(stockCode);
        networkManager.sendGet(path, s -> {
            List<StockDailyEntry> entryList = ResponseParser.parseMoney126(s);
            callback.onCall(entryList);
        });
    }

    public void getDataFromMoney163(@NotNull String stockCode, @NotNull String startDate, @NotNull String endDate, @NotNull File targetFile) {
        final String path = UrlProvider.i().getMoney163(stockCode, startDate, endDate);
        networkManager.download(path, targetFile);
    }
}
