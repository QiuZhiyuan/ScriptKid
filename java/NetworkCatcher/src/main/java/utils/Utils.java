package utils;

import com.sun.istack.internal.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Utils {
    private static SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Utils() {
    }

    public static void log(@NotNull String msg) {
        System.out.println(msg);
    }

    public static Date parseFromStr(@NotNull String str) throws ParseException {
        return sSimpleDateFormat.parse(str);
    }
}
