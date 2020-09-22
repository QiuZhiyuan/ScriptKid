package provider;

import com.sun.istack.internal.NotNull;
import utils.StockBelongCheck;
import utils.TextUtils;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class UrlProvider {

    private static final String TAG = UrlProvider.class.getSimpleName();

    private static final String REPLACE_PART_1 = "#replace_part_1#";
    private static final String REPLACE_PART_2 = "#replace_part_2#";
    private static final String REPLACE_PART_3 = "#replace_part_3#";
    private static final String BASE_URL_MONEY_126 = "http://api.money.126.net/data/feed/" + REPLACE_PART_1 + "money.api";
    private static final String BASE_URL_MONRY_163 = "http://quotes.money.163.com/service/chddata.html?code=" +
            REPLACE_PART_1 + "&start=" + REPLACE_PART_2 + "&end=" + REPLACE_PART_3;

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

    public String getMoney126(@NotNull String... stockCodes) {
        StringBuilder replacePart = new StringBuilder();
        for (String code : stockCodes) {
            if (TextUtils.isNullOrEmpty(code)) {
                continue;
            }
            String wyCode = getWangYiStockCode(code);
            replacePart.append(wyCode).append(",");
        }
        return BASE_URL_MONEY_126.replace(REPLACE_PART_1, replacePart.toString());
    }

    @NotNull
    public String getMoney163(@NotNull String stockCode, @NotNull String startDate, @NotNull String endDate) {
        String wyCode = getWangYiStockCode(stockCode);
        return BASE_URL_MONRY_163.replace(REPLACE_PART_1, wyCode)
                .replace(REPLACE_PART_2, startDate)
                .replace(REPLACE_PART_3, endDate);
    }

    @NotNull
    private String getWangYiStockCode(@NotNull String stockCode) {
        StockBelongCheck.ExchangePlace place = StockBelongCheck.check(stockCode);
        String start;
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
                throw new RuntimeException(TAG + ":Unsupported stock code in wang yi:" + stockCode);
        }
        return start + stockCode;
    }


}
