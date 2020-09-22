package utils;

import com.sun.istack.internal.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Utils {

    /**
     * 相等误差（在误差范围内即认为相等）
     */
    public static final float EQUAL_TOLERANCE = 0.0001f;

    public static final int REQUEST_TIME_OUT = 30000;

    private static SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Utils() {
    }

    public static void log(@NotNull String msg) {
        System.out.println(msg);
    }

    public static Date parseFromStr(@NotNull String str) throws ParseException {
        return sSimpleDateFormat.parse(str);
    }

    public static String dateToString(@NotNull Date date) {
        return sSimpleDateFormat.format(date);
    }

    public static float formatPrice(float price) {
        return Math.round(price * 100) / 100f;
    }

    public static boolean isEqualByTolerance(float num1, float num2, float tolerance) {
        return (Math.abs(num2 - num1) / Math.abs(num1)) < tolerance;
    }

    public static boolean isEqualByTolerance(float num1, float num2) {
        return isEqualByTolerance(num1, num2, EQUAL_TOLERANCE);
    }
}
