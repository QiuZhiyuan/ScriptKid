package internet;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import entry.StockDailyEntry;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import utils.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ResponseParser {
    private ResponseParser() {
    }

    @Nullable
    public static List<StockDailyEntry> parseMoney126(@NotNull String response) {
        String source = null;
        if (response.length() > 24) {
            source = response.substring(21, response.length() - 2);
            Utils.log(source);
        }
        if (source != null) {
            JSONObject jsonObject = new JSONObject(source);
            Iterator<String> keyInterable = jsonObject.keySet().iterator();

            List<StockDailyEntry> entryList = new ArrayList<StockDailyEntry>();
            String code, name, time;
            float price;
            while (keyInterable.hasNext()) {
                String itemKey = keyInterable.next();
                JSONObject itemObj = jsonObject.getJSONObject(itemKey);
                code = itemObj.optString("symbol");
                name = itemObj.optString("name");
                time = itemObj.optString("time");
                price = itemObj.optFloat("price");
                entryList.add(new StockDailyEntry(code, name, time, price));
            }
            return entryList;
        }
        return null;
    }
}
