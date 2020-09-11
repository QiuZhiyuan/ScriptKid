package internet;

import base.Callback;
import base.ThreadManager;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class NetWorkManager {

    // 做个builder，依次填入信息，最后send或者build

    public void sendGet(@NotNull final String url, @NotNull final Callback<String> callback) {
        Utils.log("send get:" + url);
        ThreadManager.i().post(new Runnable() {
            @Override
            public void run() {
                callback.onCall(doSendGet(url));
            }
        });

    }

    @Nullable
    private String doSendGet(@NotNull String url) {
        BufferedReader reader = null;
        try {
            URL realUrl = new URL(url);
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
}