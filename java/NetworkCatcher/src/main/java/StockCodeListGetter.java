import entry.StockRealTimeEntry;
import internet.StockDataDialog;
import provider.StockCodeProvider;
import utils.TextUtils;
import utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StockCodeListGetter {

    private static final int REQUEST_CODE_SIZE = 20;

    private final StockDataDialog stockDataDialog;

    public static void main(String[] args) {
        final StockCodeListGetter getter = new StockCodeListGetter();
        getter.start();
    }

    public StockCodeListGetter() {
        stockDataDialog = new StockDataDialog();
    }

    private void start() {
        final List<StockRealTimeEntry> identifiedStockList = new ArrayList<>();
        List<String> stockCodeList = StockCodeProvider.i().createTestStockCodeList();
        for (int i = 0; i < stockCodeList.size(); ) {
            int p = i;
            int q = p + REQUEST_CODE_SIZE;
            q = q <= stockCodeList.size() ? q : stockCodeList.size();
            List<String> subList = stockCodeList.subList(p, q);
            i = q;
            stockDataDialog.getDataFromMoney126(entryList -> {
                for (StockRealTimeEntry entry : entryList) {
                    if (entry != null && !TextUtils.isNullOrEmpty(entry.code)) {
                        identifiedStockList.add(entry);
                    }
                }
            }, subList.toArray(new String[REQUEST_CODE_SIZE]));
        }
        Utils.log(identifiedStockList.toString());
        Utils.log(identifiedStockList.size() + "");
        outputStockCode(identifiedStockList);
    }

    private void outputStockCode(List<StockRealTimeEntry> stockDailyEntries) {
        BufferedWriter bw = null;
        try {
            File file = new File(StockCodeProvider.FILE_NAME_STOCK_LIST);
            bw = new BufferedWriter(new FileWriter(file, file.exists()));
            for (StockRealTimeEntry stock : stockDailyEntries) {
                bw.write(stock.code + "," + stock.name + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
