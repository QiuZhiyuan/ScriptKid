package utils;

import com.sun.istack.internal.Nullable;

public class TextUtils {

    private TextUtils() {
    }

    public static boolean isNullOrEmpty(@Nullable String content) {
        return content == null || content.equals("");
    }

    public static boolean isEqual(@Nullable String str1, @Nullable String str2) {
        if (str1 != null) {
            return str1.equals(str2);
        } else return str2 == null;
    }
}
