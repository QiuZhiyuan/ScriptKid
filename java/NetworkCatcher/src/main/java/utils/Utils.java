package utils;

import com.sun.istack.internal.NotNull;

public final class Utils {
    private Utils() {
    }

    public static void log(@NotNull String msg) {
        System.out.println(msg);
    }
}
