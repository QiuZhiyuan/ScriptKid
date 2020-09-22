package internet;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import entry.StockRealTimeEntry;
import org.json.JSONObject;
import utils.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResponseParser {
    private ResponseParser() {
    }

    @Nullable
    public static List<StockRealTimeEntry> parseMoney126(@NotNull String response) {
        String source = null;
        if (response.length() > 24) {
            source = response.substring(21, response.length() - 2);
            Utils.log(source);
        }
        if (source != null) {
            JSONObject jsonObject = new JSONObject(source);
            Iterator<String> keyInterable = jsonObject.keySet().iterator();

            List<StockRealTimeEntry> entryList = new ArrayList<StockRealTimeEntry>();
            String code, name, time;
            float price;
            while (keyInterable.hasNext()) {
                String itemKey = keyInterable.next();
                JSONObject itemObj = jsonObject.getJSONObject(itemKey);
                code = itemObj.optString("symbol");
                name = itemObj.optString("name");
                time = itemObj.optString("time");
                price = itemObj.optFloat("price");
                entryList.add(new StockRealTimeEntry(code, name, time, price));
            }
            return entryList;
        }
        return null;
    }
}
