import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import entry.CsvDataEntry;
import internet.StockDataDialog;
import utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvDataHandler {

    private static final String STORE_PATH = "StockStorage/";
    private static final String START_DATE = "19000101";
    private static final String END_DATE = "20200912";

    private final StockDataDialog mDownloader;

    public CsvDataHandler() {
        mDownloader = new StockDataDialog();
    }

    @NotNull
    public void handleStockByCode(@NotNull final String stockCode) {
        Utils.log("Start handle stock code" + stockCode);
        File file = getStoreFile(createFileName(stockCode, END_DATE));
        if (shouldDownloadFile(file)) {
            mDownloader.getDataFromMoney163(stockCode, START_DATE, END_DATE, file);
        } else {
            Utils.log(file.getName() + " is exist");
        }

        List<CsvDataEntry> entryList = parseCsvFile(file);
        if (entryList != null) {
            Utils.log("Parsed:" + entryList.size());
        } else {
            Utils.log("Parsed result is null");
        }
        Utils.log(entryList.get(0).toString());
    }

    @NotNull
    private File getStoreFile(@NotNull String storeFileName) {
        File pathFile = new File(STORE_PATH);
        if (!pathFile.exists()) {
            pathFile.mkdir();
        }
        return new File(STORE_PATH + storeFileName);
    }

    private String createFileName(@NotNull String stockCode, @NotNull String endDate) {
        return stockCode + "-" + endDate + ".csv";
    }

    private boolean shouldDownloadFile(@NotNull File file) {
        // TODO 判断文件创建时间与更新时间，如果有修改历史则重新下载
        return !file.exists();
    }

    @Nullable
    private List<CsvDataEntry> parseCsvFile(@NotNull File file) {
        if (!file.exists()) {
            return null;
        }
        List<CsvDataEntry> entryList = new ArrayList<CsvDataEntry>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GB18030"));
            String line;
            while ((line = br.readLine()) != null) {
                CsvDataEntry entry = CsvDataEntry.fromCsvLine(line);
                if (entry != null) {
                    entryList.add(entry);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return entryList;
    }
}
