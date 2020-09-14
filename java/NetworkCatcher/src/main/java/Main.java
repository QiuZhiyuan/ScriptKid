import com.sun.istack.internal.NotNull;
import utils.Utils;

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
        csvHandler.handleStockByCode("601398");
    }

}
