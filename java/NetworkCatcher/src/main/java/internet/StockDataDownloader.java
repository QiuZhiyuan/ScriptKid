package internet;

import base.Callback;
import com.sun.istack.internal.NotNull;
import entry.StockDailyEntry;
import internet.url.UrlProvider;
import utils.Utils;

import java.io.File;
import java.util.List;

public class StockDataDownloader {

    @NotNull
    private final NetworkManager mNetworkManager;

    public StockDataDownloader() {
        this.mNetworkManager = new NetworkManager();
    }


    public void getDataFromMoney126() {
        final String path = UrlProvider.i().getMoney126(UrlProvider.GONG_SHANG_YIN_HANG);
        mNetworkManager.sendGet(path, new Callback<String>() {
            @Override
            public void onCall(String s) {
                List<StockDailyEntry> entryList = ResponseParser.parseMoney126(s);
                Utils.log(entryList == null ? "parse money126 is null" : "stock total:" + entryList.size() + "\n" + entryList.toString());
            }
        });
    }

    public void getDataFromMoney163() {
        final String path = UrlProvider.i().getMoney163("601398", "19000101", "20200911");
        mNetworkManager.download(path, new File("StockStorage/601398.csv"));
    }
}
