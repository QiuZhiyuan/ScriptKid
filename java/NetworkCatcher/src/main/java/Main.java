import com.sun.istack.internal.NotNull;
import utils.Utils;

public class Main {

    @NotNull
    private final CsvDataHandler mCsvHandler;

    private Main() {
        mCsvHandler = new CsvDataHandler();
    }

    public static void main(String[] args) {
        Utils.log("Hello World!");
        Main main = new Main();
        main.start();
    }

    private void start() {
        mCsvHandler.handleStockByCode("601398");
    }

}
