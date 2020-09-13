package utils;

import com.sun.istack.internal.Nullable;

public class TextUtils {

    private TextUtils() {
    }

    public static boolean isNullOrEmpty(@Nullable String content) {
        return content == null || content.equals("");
    }
}
