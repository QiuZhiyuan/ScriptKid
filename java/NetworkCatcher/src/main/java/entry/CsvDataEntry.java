package entry;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import utils.Utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Parsed from csv file lines which downloaded from http://quotes.money.163.com/service/chddata.html?
 */
public class CsvDataEntry {

    private static final String TAG = CsvDataEntry.class.getSimpleName();

    // 日期
    private final Date mDate;

    // 股票代码
    private final String mCode;

    // 股票名称
    private final String mName;

    // 收盘价
    private final float mClosePrice;

    // 最高价
    private final float mHighPrice;

    // 最低价
    private final float mLowPrice;

    // 开盘价
    private final float mOpenPrice;

    private final float mBeforeClosePrice;

    // 涨跌额
    private final float mUpAndDownPrice;

    // 涨跌幅度
    private final float mUpAndDownPercent;

    // 换手率
    private final float mTurnoverRate;

    // 成交量
    private final float mDoneVolume;

    // 成交额
    private final float mDonePrice;

    // 总市值（The total market capitalization）
    private final float mTotalMarketCap;

    // 流通市值（Circulation market value）
    private final float mCirculationMarketValue;

    public CsvDataEntry(Date date, String code, String name, float closePrice, float highPrice, float lowPrice,
                        float openPrice, float beforeClosePrice, float upAndDownPrice, float upAndDownPercent, float turnoverRate,
                        float doneVolume, float donePrice, float totalMarketCap, float circulationMarketValue) {
        this.mDate = date;
        this.mCode = code;
        this.mName = name;
        this.mClosePrice = closePrice;

        this.mHighPrice = highPrice;
        this.mLowPrice = lowPrice;
        this.mOpenPrice = openPrice;
        this.mBeforeClosePrice = beforeClosePrice;
        this.mUpAndDownPrice = upAndDownPrice;
        this.mUpAndDownPercent = upAndDownPercent;
        this.mTurnoverRate = turnoverRate;
        this.mDoneVolume = doneVolume;
        this.mDonePrice = donePrice;
        this.mTotalMarketCap = totalMarketCap;
        this.mCirculationMarketValue = circulationMarketValue;
    }

    @NotNull
    @Override
    public String toString() {
        return "date:" + mDate + " code:" + mCode + " name:" + mName + " closePrice:" + mClosePrice + " highPrice:" +
                mHighPrice + " lowPrice:" + mLowPrice + " openPrice:" + mOpenPrice + "beforeClosePrice:" +
                mBeforeClosePrice + " upAndDownPrice:" + mUpAndDownPrice + " upAndDownPercent:" + mUpAndDownPercent +
                " turnoverRate:" + mTurnoverRate + " doneVolume:" + mDoneVolume + " donePrice:" + mDonePrice +
                " totalMarketCap:" + mTotalMarketCap + " circulationMarketValue:" + mCirculationMarketValue;
    }

    @Nullable
    public static CsvDataEntry fromCsvLine(@NotNull String line) {
        String[] elements = line.split(",");
        try {
            Date date = Utils.parseFromStr(elements[0]);
            String code = elements[1].substring(1);
            String name = elements[2];
            float closePrice = Float.parseFloat(elements[3]);
            float highPrice = Float.parseFloat(elements[4]);
            float lowPrice = Float.parseFloat(elements[5]);
            float openPrice = Float.parseFloat(elements[6]);
            float beforeClosePrice = Float.parseFloat(elements[7]);
            float upAndDownPrice = Float.parseFloat(elements[8]);
            float upAndDownPercent = Float.parseFloat(elements[9]);
            float turnoverRate = Float.parseFloat(elements[10]);
            float doneVolume = Float.parseFloat(elements[11]);
            float donePrice = Float.parseFloat(elements[12]);
            float totalMarketCap = Float.parseFloat(elements[13]);
            float circulationMarketValue = Float.parseFloat(elements[14]);
            return new CsvDataEntry(date, code, name, closePrice, highPrice, lowPrice, openPrice, beforeClosePrice,
                    upAndDownPrice, upAndDownPercent, turnoverRate, doneVolume, donePrice, totalMarketCap,
                    circulationMarketValue);

        } catch (Exception e) {
//            Utils.log(TAG + ":" + e);
            return null;
        }
    }


}
