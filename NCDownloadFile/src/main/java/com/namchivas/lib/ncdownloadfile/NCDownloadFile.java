package com.namchivas.lib.ncdownloadfile;

import android.app.Activity;
import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by NamChivas on 06/02/2021.
 */
public abstract class NCDownloadFile {
    Context context;
    Activity activity;

    public NCDownloadFile(Activity activity) {
        this.context = this.activity = activity;
    }

    protected abstract void onProgress(long percent);

    protected abstract void onFinsh(Boolean isSuccess, String message);

    public void start(String url, String target) {

        String[] sUrl = new String[2];
        sUrl[0] = url;
        sUrl[1] = target;

        final Boolean[] isSuccess = {false};
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream input = null;
                OutputStream output = null;
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(sUrl[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                    connection.connect();

                    // expect HTTP 200 OK, so we don't mistakenly save error report
                    // instead of the file
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        activity.runOnUiThread(() -> onFinsh(false, "Server returned HTTP ..."));
                    }
                    int fileLength = connection.getContentLength();

                    // download the file
                    input = connection.getInputStream();
                    output = new FileOutputStream(sUrl[1]);

                    byte[] data = new byte[4096];
                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        if (fileLength > 0) {
                            long percent = (int) (total * 100 / fileLength);
                            if ((int) percent >= 100) {
                                isSuccess[0] = true;
                            }
                            activity.runOnUiThread(() -> onProgress(percent));
                        }
                        output.write(data, 0, count);
                    }
                } catch (Exception e) {
                    activity.runOnUiThread(() -> onFinsh(false, e.toString()));

                } finally {
                    try {
                        if (output != null)
                            output.close();
                        if (input != null)
                            input.close();
                    } catch (IOException ignored) {
                        activity.runOnUiThread(() -> onFinsh(false, ignored.toString()));
                    }

                    if (connection != null)
                        connection.disconnect();
                }

                activity.runOnUiThread(() -> {
                    if (isSuccess[0]) {
                        onFinsh(true, "File is downloaded !");
                    }
                });
            }
        }).start();
    }


}

