package internet;

import base.Callback;
import base.ThreadManager;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import utils.Utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NetworkManager {

    //TODO 做个builder，依次填入信息，最后send或者build

    public void sendGet(@NotNull final String path, @NotNull final Callback<String> callback) {
        Utils.log("send get:" + path);
        callback.onCall(doSendGet(path));
    }

    @Nullable
    private String doSendGet(@NotNull String path) {
        BufferedReader reader = null;
        try {
            URL realUrl = new URL(path);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            final StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @NotNull
    public File download(@NotNull String path, @NotNull File file) {
        Utils.log("download from:" + path);
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(path);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(Utils.REQUEST_TIME_OUT);
            connection.setReadTimeout(Utils.REQUEST_TIME_OUT);
            inputStream = connection.getInputStream();
            fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int byteRead, byteSum = 0;
            while ((byteRead = inputStream.read(buffer)) != -1) {
                byteSum += byteRead;
                fileOutputStream.write(buffer, 0, byteRead);
            }
            Utils.log("Total download:" + (byteSum / 1024) + "kb");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}