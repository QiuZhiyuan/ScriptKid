package statistics;

import com.sun.istack.internal.Nullable;
import utils.CollectionUtils;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 趋势判断，获取数列的上升趋势子序列和下降趋势子序列
 */
public class TrendHolder {

    public enum TrendType {
        UPWARD,
        DOWNWARD,
    }

    public static class NumsTrend {
        public final int start;

        public final int end;

        public final float startValue;

        public final float endValue;

        public final TrendType type;

        public NumsTrend(int start, int end, float startValue, float endValue, TrendType type) {
            this.start = start;
            this.end = end;
            this.startValue = startValue;
            this.endValue = endValue;
            this.type = type;
        }

        @Override
        public String toString() {
            return "NumsTrend{" +
                    "start=" + start +
                    ", end=" + end +
                    ", startValue=" + startValue +
                    ", endValue=" + endValue +
                    ", type=" + type +
                    '}';
        }
    }

    private final float[] nums;

    /**
     * 忽略的逆向趋势的长度，也是最少
     * 比如取值 2 以下面数列为例
     * 1 2 3 4 -1 -2 8 9 -3 -4 -5 -6
     * 忽略中间 -3 -1 2个数字
     * 1～9为一个上升趋势
     * -1～-6为一个下降趋势
     * 同类型的趋势不能重合
     */
//    private final int maxIgnoreLength;

    private final List<NumsTrend> numsTrendList = new ArrayList<>();

    public TrendHolder(float[] nums) {
        this.nums = nums;
    }

    @Nullable
    public TrendType getTrendByIndex(int index) {
        List<NumsTrend> numsTrendList = getNumsTrendList();
        for (NumsTrend trend : numsTrendList) {
            if (trend.start <= index && trend.end >= index) {
                return trend.type;
            }
        }
        return null;
    }

    public List<NumsTrend> getNumsTrendList() {
        if (CollectionUtils.isNullOrEmptry(numsTrendList)) {
            numsTrendList.addAll(doCompute());
        }
        return numsTrendList;
    }

    public void printTrendList() {
        List<NumsTrend> trendList = getNumsTrendList();
        int maxTrend = 0;
        int minTrend = Integer.MAX_VALUE;
        for (NumsTrend trend : trendList) {
            Utils.log(trend.toString());
            int d = trend.end - trend.start;
            if (maxTrend < d) {
                maxTrend = d;
            }
            if (minTrend > d) {
                minTrend = d;
            }
        }
        Utils.log("Nums trend list count:" + trendList.size());
        Utils.log("max trend:" + maxTrend);
        Utils.log("min trend:" + minTrend);
    }

    private List<NumsTrend> doCompute() {
        List<NumsTrend> trendList = new ArrayList<>();
        for (int i = 0; i < nums.length - 1; ) {
            int j = i;
            if (isTrend(i, TrendType.UPWARD)) {
                int p = i;
                while (isTrend(i, TrendType.UPWARD)) {
                    i++;
                }
                trendList.add(new NumsTrend(p, i, nums[p], nums[i], TrendType.UPWARD));
            }
            if (isTrend(i, TrendType.DOWNWARD)) {
                int p = i;
                while (isTrend(i, TrendType.DOWNWARD)) {
                    i++;
                }
                trendList.add(new NumsTrend(p, i, nums[p], nums[i], TrendType.DOWNWARD));
            }
            if (j == i) {
                i++;
            }
        }
        return trendList;
    }

    private boolean isTrend(int i, TrendType trendType) {
        if (i + 1 < nums.length) {
            boolean flag = trendType == TrendType.UPWARD ? nums[i] < nums[i + 1] : nums[i] > nums[i + 1];
            return i + 1 < nums.length && (flag || Utils.isEqualByTolerance(nums[i], nums[i + 1]));
        } else {
            return false;
        }
    }
}
