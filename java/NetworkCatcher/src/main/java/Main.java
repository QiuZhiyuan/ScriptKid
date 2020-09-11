import base.Callback;
import entry.StockDailyEntry;
import internet.NetWorkManager;
import internet.ResponseParser;
import internet.url.UrlProvider;
import utils.Utils;

import java.util.List;

public class Main {


    public static void main(String[] args) {
        Utils.log("Hello World!");

        final String url = UrlProvider.i().getMoney126(UrlProvider.TEST_STOCK_CODE);
        final NetWorkManager netWorkManager = new NetWorkManager();
        netWorkManager.sendGet(url, new Callback<String>() {
            @Override
            public void onCall(String s) {
                List<StockDailyEntry> entryList = ResponseParser.parseMoney126(s);
                Utils.log(entryList == null ? "parse money126 is null" : "stock total:" + entryList.size() + "\n" + entryList.toString());
            }
        });

//        netWorkManager.sendGet("http://quotes.money.163.com/service/chddata.html?code=0601398&start=20140720&end=20050808");
    }
}
