package com.jedabero.sunshine;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * FetchWeatherTask
 * Created by jedabero on 6/03/16.
 */
public class FetchWeatherTask extends AsyncTask<Pair<String, String>, Number, JSONObject> {

    public static final String TAG = "FetchWeatherTask";

    private String url;

    public FetchWeatherTask(Context context, String city, int days, String units) {
        String baseUrl = context.getString(R.string.url_owm_api);
        String resource = context.getString(R.string.owm_resource);
        String appid = context.getString(R.string.owm_appid);
        url = context.getString(R.string.url_format, baseUrl, resource, city, appid, days, units);
    }

    @Override
    protected JSONObject doInBackground(Pair<String, String>... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String response = null;

        try {
            URL owmApi = new URL(url);
            urlConnection = (HttpURLConnection) owmApi.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder  builder = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null){
                builder.append(line).append("\n");
            }
            if (builder.length() == 0) {
                return null;
            }
            response = builder.toString();

            return new JSONObject(response);
        } catch (MalformedURLException e) {
            if (BuildConfig.DEBUG) Log.d(TAG, "doInBackground: url: " + url);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            if (BuildConfig.DEBUG) Log.d(TAG, "doInBackground: response: " + response);
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

}
