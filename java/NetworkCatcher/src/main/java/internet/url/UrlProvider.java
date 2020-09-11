package internet.url;

import com.sun.istack.internal.NotNull;
import utils.StockBelongCheck;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class UrlProvider {

    private static final String REPLACE_PART = "#replace_part#";

    private static final String BASE_URL_MONEY_126 = "http://api.money.126.net/data/feed/" + REPLACE_PART + "money.api";

    public static final String GONG_SHANG_YIN_HANG = "601398";

    public static final String MEI_DI_JI_TUAN = "000333";

    public static final List<String> TEST_STOCK_CODE = new ArrayList<String>();

    static {
        for (int i = 3; i <= 4; i++) {
            for (int j = 0; j <= 9; j++) {
                for (int k = 0; k <= 9; k++) {
                    TEST_STOCK_CODE.add("601" + i + j + k);
                }
            }
        }
    }

    private volatile static UrlProvider sInstance;

    @NotNull
    public static UrlProvider i() {
        if (sInstance == null) {
            synchronized (UrlProvider.class) {
                if (sInstance == null) {
                    sInstance = new UrlProvider();
                }
            }
        }
        return sInstance;
    }

    private UrlProvider() {
    }

    public String getMoney126(@NotNull List<String> stockCodes) {
        StringBuilder replacePart = new StringBuilder();
        for (String code : stockCodes) {
            StockBelongCheck.ExchangePlace place = StockBelongCheck.check(code);
            String start = null;
            switch (place) {
                case SHANG_HAI_A:
                case SHANG_HAI_B:
                    start = "0";
                    break;
                case SHEN_ZHEN_A:
                case SHEN_ZHEN_B:
                    start = "1";
                    break;
                default:
                    break;
            }
            if (start != null) {
                replacePart.append(start).append(code).append(",");
            }
        }
        return BASE_URL_MONEY_126.replace(REPLACE_PART, replacePart.toString());
    }
}
