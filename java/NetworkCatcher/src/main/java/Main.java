import com.sun.istack.internal.NotNull;
import provider.StockCodeProvider;
import statistics.CsvDataHandler;
import utils.Utils;

import java.util.List;

public class Main {

    @NotNull
    private final CsvDataHandler csvHandler;

    private Main() {
        csvHandler = new CsvDataHandler();
    }

    public static void main(String[] args) {
        Utils.log("Hello World!");
        Main main = new Main();
        main.start();
    }

    private void start() {
        List<String> stockCodeList = StockCodeProvider.i().loadStockCodeList();
//        Utils.log(stockCodeList.toString());
//        Utils.log(stockCodeList.size() + "");
        for (String stockCode : stockCodeList) {
            csvHandler.handleStockByCode(stockCode);
        }
    }
}
