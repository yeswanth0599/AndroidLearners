package com.mykapps.androidlearners.data;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DoUpdateJsonLikes extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... params) {
        String jsonData = params[0];


        try {
            URL url = new URL(Contract.JAVA_JSON_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("PUT");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();

            //send Data

            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(jsonData.getBytes());

            //receive and read data

            InputStream inputStream = httpURLConnection.getInputStream();
            String result = "";
            int byteCharecter;
            while ((byteCharecter = inputStream.read()) != -1) {
                result += (char) byteCharecter;
            }

            inputStream.close();
            outputStream.close();
            httpURLConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
