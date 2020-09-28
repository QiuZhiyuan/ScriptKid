package provider;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
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
 */
public class LocalFileReader {
    private static final String STORE_PATH = "StockStorage/";

    private final StockDataDialog downloader;

    public LocalFileReader() {
        downloader = new StockDataDialog();
    }

    @Nullable
    public List<StockDailyEntry> getStockDataFromFile(String stockCode, String endDate) {
        File file = getStoreFile(createFileName(stockCode, endDate));
        if (shouldDownloadFile(stockCode, file)) {
            downloader.getDataFromMoney163(stockCode, Utils.START_DATE, endDate, file);
        } else {
            Utils.log(file.getName() + " is exist");
        }
        List<StockDailyEntry> lineEntryList = parseCsvFile(file);
        if (!CollectionUtils.isNullOrEmptry(lineEntryList)) {
            return doCompute(lineEntryList);
        } else {
            return null;
        }
    }

    /**
     * 对数据进行初步计算
     *
     * @param lineEntryList
     * @return
     */
    private List<StockDailyEntry> doCompute(List<StockDailyEntry> lineEntryList) {
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

    private boolean shouldDownloadFile(final @NotNull String stockCode, @NotNull File file) {
        if (file.exists()) {
            return false;
        }
        File parentFile = file.getParentFile();
        if (parentFile != null && parentFile.isDirectory()) {
            File[] fileList = parentFile.listFiles((dir, name) -> name.startsWith(stockCode));
            if (fileList != null && fileList.length > 0) {
                for (File item : fileList) {
                    item.delete();
                    Utils.log("Delete file:" + item.getName());
                }
            }
        }
        return true;
    }

    @Nullable
    private List<StockDailyEntry> parseCsvFile(@NotNull File file) {
        if (!file.exists()) {
            return null;
        }
        List<StockDailyEntry> entryList = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GB18030"));
            String line;
            while ((line = br.readLine()) != null) {
                StockDailyEntry entry = StockDailyEntry.fromCsvLine(line);
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
