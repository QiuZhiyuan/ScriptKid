package entry;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import utils.Utils;

import java.util.Date;

/**
 * Parsed from csv file lines which downloaded from http://quotes.money.163.com/service/chddata.html?
 */
public abstract class CsvLineEntry {

    private static final String TAG = CsvLineEntry.class.getSimpleName();

    // 日期
    public final Date date;

    // 股票代码
    public final String code;

    // 股票名称
    public final String name;

    // 收盘价
    public final float closePrice;

    // 最高价
    public final float highPrice;

    // 最低价
    public final float lowPrice;

    // 开盘价
    public final float openPrice;

    // 前收价
    public final float beforeClosePrice;

    // 涨跌额
    public final float upAndDownPrice;

    // 涨跌幅度
    public final float upAndDownPercent;

    // 换手率
    public final float turnoverRate;

    // 成交量
    public final float doneVolume;

    // 成交额
    public final float donePrice;

    // 总市值（The total market capitalization）
    public final float totalMarketCap;

    // 流通市值（Circulation market value）
    public final float circulationMarketValue;

    public CsvLineEntry(CsvLineEntry entry) {
        this.date = entry.date;
        this.code = entry.code;
        this.name = entry.name;
        this.closePrice = entry.closePrice;

        this.highPrice = entry.highPrice;
        this.lowPrice = entry.lowPrice;
        this.openPrice = entry.openPrice;
        this.beforeClosePrice = entry.beforeClosePrice;
        this.upAndDownPrice = entry.upAndDownPrice;
        this.upAndDownPercent = entry.upAndDownPercent;
        this.turnoverRate = entry.turnoverRate;
        this.doneVolume = entry.doneVolume;
        this.donePrice = entry.donePrice;
        this.totalMarketCap = entry.totalMarketCap;
        this.circulationMarketValue = entry.circulationMarketValue;
    }

    public CsvLineEntry(Date date, String code, String name, float closePrice, float highPrice, float lowPrice,
                        float openPrice, float beforeClosePrice, float upAndDownPrice, float upAndDownPercent, float turnoverRate,
                        float doneVolume, float donePrice, float totalMarketCap, float circulationMarketValue) {
        this.date = date;
        this.code = code;
        this.name = name;
        this.closePrice = closePrice;

        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.openPrice = openPrice;
        this.beforeClosePrice = beforeClosePrice;
        this.upAndDownPrice = upAndDownPrice;
        this.upAndDownPercent = upAndDownPercent;
        this.turnoverRate = turnoverRate;
        this.doneVolume = doneVolume;
        this.donePrice = donePrice;
        this.totalMarketCap = totalMarketCap;
        this.circulationMarketValue = circulationMarketValue;
    }

    @Override
    public String toString() {
        return "CsvLineEntry{" +
                "date=" + date +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", closePrice=" + closePrice +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                ", openPrice=" + openPrice +
                ", beforeClosePrice=" + beforeClosePrice +
                ", upAndDownPrice=" + upAndDownPrice +
                ", upAndDownPercent=" + upAndDownPercent +
                ", turnoverRate=" + turnoverRate +
                ", doneVolume=" + doneVolume +
                ", donePrice=" + donePrice +
                ", totalMarketCap=" + totalMarketCap +
                ", circulationMarketValue=" + circulationMarketValue +
                '}';
    }

    @Nullable
    public static StockDailyEntry fromCsvLine(@NotNull String line) {
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
            if (closePrice < 0.001f) {
//                Utils.log(line);
                return null;
            }
            return new StockDailyEntry(date, code, name, closePrice, highPrice, lowPrice, openPrice, beforeClosePrice,
                    upAndDownPrice, upAndDownPercent, turnoverRate, doneVolume, donePrice, totalMarketCap,
                    circulationMarketValue);

        } catch (Exception e) {
//            Utils.log(TAG + ":" + e);
            return null;
        }
    }


}
