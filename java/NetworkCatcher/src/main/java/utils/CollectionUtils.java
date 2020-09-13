package utils;

import com.sun.istack.internal.Nullable;

import java.util.Collection;

public class CollectionUtils {
    private CollectionUtils() {
    }

    public static boolean isNullOrEmptry(@Nullable Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
