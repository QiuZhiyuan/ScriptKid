package provider;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import sun.tools.jstat.Literal;
import utils.StockBelongCheck;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class StockCodeProvider {


    public static final String GONG_SHANG_YIN_HANG = "601398";
    public static final String MEI_DI_JI_TUAN = "000333";

    private static final int STOCK_CODE_LENGTH = 6;

    private static StockCodeProvider instance;

    @NotNull
    public static StockCodeProvider i() {
        if (instance == null) {
            synchronized (StockCodeProvider.class) {
                if (instance == null) {
                    instance = new StockCodeProvider();
                }
            }
        }
        return instance;
    }

    public void downloadListFromNet() {

    }

    @Nullable
    public List<String> loadStockCodeList() {
        return null;
    }

    public List<String> createTestStockCodeList() {
        List<String> stockCodeList = new ArrayList<>();
        List<StockBelongCheck.ExchangePlace> exchangePlaceList = filterExcahngePlaces();
        for (StockBelongCheck.ExchangePlace exchangePlace : exchangePlaceList) {
            final String startCode = exchangePlace.startCode;
            final int length = STOCK_CODE_LENGTH - startCode.length();
            for (int i = 0; i < Math.pow(10, length); i++) {
                final String stockCode = createStockCode(startCode, i);
                stockCodeList.add(stockCode);
            }
        }

        return stockCodeList;
    }

    public String createStockCode(String startCode, int code) {
        final String endCode = String.valueOf(code);
        final int fillLength = STOCK_CODE_LENGTH - startCode.length() - endCode.length();
        StringBuilder sb = new StringBuilder();
        sb.append(startCode);
        for (int i = 0; i < fillLength; i++) {
            sb.append(0);
        }
        sb.append(endCode);
        return sb.toString();

    }

    private List<StockBelongCheck.ExchangePlace> filterExcahngePlaces() {
        List<StockBelongCheck.ExchangePlace> exchangePlaces = new ArrayList<>();
        exchangePlaces.add(StockBelongCheck.ExchangePlace.SHEN_ZHEN_A);
        exchangePlaces.add(StockBelongCheck.ExchangePlace.SHEN_ZHEN_B);
        exchangePlaces.add(StockBelongCheck.ExchangePlace.SHANG_HAI_A);
        exchangePlaces.add(StockBelongCheck.ExchangePlace.SHANG_HAI_B);
        return exchangePlaces;
    }
}
