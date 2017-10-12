package com.dev.kunal.newsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kunal on 10/12/2017.
 */

public class NewsTaskLoader extends AsyncTaskLoader<NewsItem[]>{

    public static final String REQUEST_URL = "http://content.guardianapis.com/search?q=debates&api-key=test&show-fields=all";

    public NewsTaskLoader(Context context) {
        super(context);
    }

    @Override
    public NewsItem[] loadInBackground() {

        URL url  = null;
        String jsonResponse = "";
        try {
            url = new URL(REQUEST_URL);
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return extractFromJsonString(jsonResponse);
    }

    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection connection = null;
        InputStream inStream = null;

        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        if(connection.getResponseCode() == 200){
            inStream = connection.getInputStream();
            jsonResponse = readFromStream(inStream);
        }

        if(connection != null) connection.disconnect();
        if(inStream != null) inStream.close();

        return jsonResponse;
    }

    private String readFromStream(InputStream inStream) throws IOException {
        StringBuilder response = new StringBuilder();
        if(inStream == null) return response.toString();

        InputStreamReader reader = new InputStreamReader(inStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        while (line != null){
            response.append(line);
            line = bufferedReader.readLine();
        }

        return response.toString();
    }

    private NewsItem[] extractFromJsonString(String jsonString){
        if(TextUtils.isEmpty(jsonString)) return null;

        List<NewsItem> newsItems = new ArrayList<>();

        JSONArray results = null;
        try{
            JSONObject baseObject = new JSONObject(jsonString);
            results = baseObject.getJSONObject("response").getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(results != null){
            for(int i=0; i < results.length(); i++) {
                String title = "";
                String section = "";
                String byline = "";
                String dateString = "";
                String webUrl = "https://www.theguardian.com/";
                try{
                    JSONObject result = results.getJSONObject(i);
                    JSONObject fields = result.getJSONObject("fields");
                    title = result.getString("webTitle");
                    section = result.getString("sectionName");
                    byline = fields.getString("byline");
                    webUrl = result.getString("webUrl");
                    dateString = result.getString("webPublicationDate").substring(0, 10);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                newsItems.add(new NewsItem(title, byline, dateString, section, webUrl));
            }
        }

        return newsItems.toArray(new NewsItem[newsItems.size()]);
    }

}
