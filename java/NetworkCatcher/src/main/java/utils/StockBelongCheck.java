package utils;

import com.sun.istack.internal.NotNull;

public final class StockBelongCheck {

    public enum ExchangePlace {
        SHEN_ZHEN_A("00"),
        SHEN_ZHEN_B("200"),
        SHEN_ZHEN_FENG_BI_FUND("184"),
        CHUANG_YE("300"),
        SHANG_HAI_A("60"),
        SHANG_HAI_B("900"),
        SHANG_HAI_FENG_BI_FUND("500"),
        UNKNOWN("Unknown"),
        ;
        public String startCode;

        ExchangePlace(@NotNull String startCode) {
            this.startCode = startCode;
        }

        boolean belong(@NotNull String stockCode) {
            return stockCode.startsWith(startCode);
        }
    }

    private StockBelongCheck() {
    }

    /**
     * 检查股票代码合法性
     *
     * @param stockCode
     * @return
     */
    public static boolean checkLegality(String stockCode) {
        switch (check(stockCode)) {
            case SHEN_ZHEN_A:
            case SHEN_ZHEN_B:
            case SHANG_HAI_A:
            case SHANG_HAI_B:
                return true;
            default:
                return false;
        }
    }

    public static ExchangePlace check(@NotNull String stockCode) {
        if (stockCode.length() == 6) {
            for (ExchangePlace place : ExchangePlace.values()) {
                if (place.belong(stockCode)) {
                    return place;
                }
            }
        }
        return ExchangePlace.UNKNOWN;
    }
}
