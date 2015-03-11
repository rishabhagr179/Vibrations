package com.vib15.vibrations.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.vib15.vibrations.app.data.EventsDbHelper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Agarwal on 04-02-2015.
 */
public class UpdateLatestNews {
    Context context;
    String news;
    EventsDbHelper edh;
    DefaultHttpClient httpclient = new DefaultHttpClient();
    public UpdateLatestNews(Context context){
        edh = new EventsDbHelper(context);
        news="";
        this.context=context;
    }
    public void UpdateNews(){
        if(isNetworkAvailable()) {
            Log.v("" + isNetworkAvailable(), "" + isNetworkAvailable());
            HttpGet httppost = new HttpGet("http://vib15.freeoda.com/querry/news.txt");
            HttpResponse response = null;
            try {
                response = httpclient.execute(httppost);
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                InputStream is = buf.getContent();
                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = r.readLine()) != null) {
                    news = news + line;
                    Log.v("NEWS: ", line);
                }
                if (news.equals(""))
                    news = "Preparations wait till the 10th!";
                Log.v("NEWS:", news);
                edh.setNews(news);

            } catch (IOException e) {
                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        edh.close();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }
}
