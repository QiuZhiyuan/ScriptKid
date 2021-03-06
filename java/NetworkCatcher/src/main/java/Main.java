import com.sun.istack.internal.NotNull;
import provider.StockCodeProvider;
import statistics.StockHandler;
import utils.Utils;

public class Main {

    @NotNull
    private final StockHandler csvHandler;

    private Main() {
        csvHandler = new StockHandler();
    }

    public static void main(String[] args) {
        Utils.log("Hello World!");
        Main main = new Main();
        main.start();
    }

    private void start() {
//        List<String> stockCodeList = StockCodeProvider.i().loadStockCodeList();
//        Utils.log(stockCodeList.size() + "");
//        for (String stockCode : stockCodeList) {
//            csvHandler.handleStockByCode(stockCode);
//        }
        long startTime = System.currentTimeMillis();
        csvHandler.handleStockByCode(StockCodeProvider.MEI_DI_JI_TUAN);
        Utils.log("Total cost time:" + (System.currentTimeMillis() - startTime));
    }
}
