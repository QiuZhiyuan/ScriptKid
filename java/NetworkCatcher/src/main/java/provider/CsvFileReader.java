package provider;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import entry.CsvLineEntry;
import entry.StockDailyEntry;
import internet.StockDataDialog;
import statistics.ComputeUtils;
import utils.CollectionUtils;
import utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 从文件中读取每日数据，如果没有则从网络上下载
 * TODO 判断文件创建时间与更新时间，如果有修改历史则重新下载
 * TODO 同一股票代码，不同日期拼接功能，下载新日期数据与旧日期数据拼接
 */
public class CsvFileReader {
    private static final String STORE_PATH = "StockStorage/";

    private final StockDataDialog downloader;

    public CsvFileReader() {
        downloader = new StockDataDialog();
    }

    @Nullable
    public List<StockDailyEntry> getStockDataFromFile(String stockCode, String endDate) {
        File file = getStoreFile(createFileName(stockCode, endDate));
        if (shouldDownloadFile(file)) {
            downloader.getDataFromMoney163(stockCode, Utils.START_DATE, endDate, file);
        } else {
            Utils.log(file.getName() + " is exist");
        }
        List<CsvLineEntry> lineEntryList = parseCsvFile(file);
        if (!CollectionUtils.isNullOrEmptry(lineEntryList)) {
            return doCompute(lineEntryList);
        } else {
            return null;
        }
    }

    private List<StockDailyEntry> doCompute(List<CsvLineEntry> lineEntryList) {
        // 按日期升序排列
        lineEntryList.sort(Comparator.comparing(o -> o.date));
        return ComputeUtils.computeStateAvg(lineEntryList);
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
        return !file.exists();
    }


    @Nullable
    private List<CsvLineEntry> parseCsvFile(@NotNull File file) {
        if (!file.exists()) {
            return null;
        }
        List<CsvLineEntry> entryList = new ArrayList<CsvLineEntry>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GB18030"));
            String line;
            while ((line = br.readLine()) != null) {
                CsvLineEntry entry = CsvLineEntry.fromCsvLine(line);
                if (entry != null) {
                    entryList.add(entry);
                }
            }
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
