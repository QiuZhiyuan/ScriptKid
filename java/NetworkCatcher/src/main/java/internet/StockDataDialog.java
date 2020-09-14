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


    public void getDataFromMoney126() {
        final String path = UrlProvider.i().getMoney126(UrlProvider.GONG_SHANG_YIN_HANG);
        networkManager.sendGet(path, new Callback<String>() {
            @Override
            public void onCall(String s) {
                List<StockDailyEntry> entryList = ResponseParser.parseMoney126(s);
                Utils.log(entryList == null ? "parse money126 is null" : "stock total:" + entryList.size() + "\n" + entryList.toString());
            }
        });
    }

    public void getDataFromMoney163(@NotNull String stockCode, @NotNull String startDate, @NotNull String endDate, @NotNull File targetFile) {
        final String path = UrlProvider.i().getMoney163(stockCode, startDate, endDate);
        networkManager.download(path, targetFile);
    }
}
