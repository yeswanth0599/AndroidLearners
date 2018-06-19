package com.mykapps.androidlearners.data;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonLoader extends AsyncTaskLoader<String> {
    private HttpURLConnection httpURLConnection;
    private BufferedReader bufferedReader;

    private String urlText;

    public JsonLoader(Context context, String urlText) {
        super(context);
        this.urlText = urlText;
    }

    @Override
    public String loadInBackground() {

        try {
            URL url = new URL(urlText);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }
            String jsonUrl = buffer.toString();
            return jsonUrl;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }

        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

}
