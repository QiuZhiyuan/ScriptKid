package internet;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import jdk.nashorn.internal.parser.JSONParser;
import utils.Utils;

public class ResponseParser {
    private ResponseParser() {
    }

    @Nullable
    public static JSONParser parseMoney126(@NotNull String response) {
        String result;
        if (response.length() > 24) {
            result = response.substring(21, response.length() - 2);
            Utils.log(result);
        }
        return null;
    }
}
