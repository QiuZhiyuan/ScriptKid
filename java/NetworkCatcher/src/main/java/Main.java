import base.Callback;
import internet.NetWorkManager;
import internet.ResponseParser;
import sun.nio.ch.Net;
import utils.Utils;

public class Main {


    public static void main(String[] args) {
        Utils.log("Hello World!");

        NetWorkManager netWorkManager = new NetWorkManager();
        netWorkManager.sendGet("http://api.money.126.net/data/feed/0601398,money.api", new Callback<String>() {
            @Override
            public void onCall(String s) {
                ResponseParser.parseMoney126(s);
            }
        });
//        netWorkManager.sendGet("http://quotes.money.163.com/service/chddata.html?code=0601398&start=20140720&end=20050808");
    }
}
