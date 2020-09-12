import com.sun.istack.internal.NotNull;
import internet.NetworkManager;
import internet.StockDataDownloader;
import utils.Utils;

public class Main {

    private Main() {
        this.mNetworkManager = new NetworkManager();
    }

    public static void main(String[] args) {
        Utils.log("Hello World!");
        StockDataDownloader downloader = new StockDataDownloader();
        downloader.getDataFromMoney163();
    }

    @NotNull
    private final NetworkManager mNetworkManager;

}
