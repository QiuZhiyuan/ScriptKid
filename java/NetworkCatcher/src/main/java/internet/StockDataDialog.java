package internet;

import base.Callback;
import com.sun.istack.internal.NotNull;
import entry.StockRealTimeEntry;
import provider.UrlProvider;

import java.io.File;
import java.util.List;

public class StockDataDialog {

    @NotNull
    private final NetworkManager networkManager;

    public StockDataDialog() {
        this.networkManager = new NetworkManager();
    }


    public void getDataFromMoney126(Callback<List<StockRealTimeEntry>> callback, String... stockCode) {
        final String path = UrlProvider.i().getMoney126(stockCode);
        networkManager.sendGet(path, s -> {
            List<StockRealTimeEntry> entryList = ResponseParser.parseMoney126(s);
            callback.onCall(entryList);
        });
    }

    public void getDataFromMoney163(@NotNull String stockCode, @NotNull String startDate, @NotNull String endDate, @NotNull File targetFile) {
        final String path = UrlProvider.i().getMoney163(stockCode, startDate, endDate);
        networkManager.download(path, targetFile);
    }
}
