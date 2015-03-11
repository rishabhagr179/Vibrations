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
 * Created by Agarwal on 22-01-2015.
 */
public class UpdateEvents {
    Context context;
    String querry;
    EventsDbHelper edHelper;
    DefaultHttpClient httpclient = new DefaultHttpClient();
    public UpdateEvents(Context context){
        edHelper=new EventsDbHelper(context);
        this.context=context;
    }

    public String updateEvents(){
        Log.v(""+isNetworkAvailable(),""+isNetworkAvailable());
        int c = edHelper.getEventUpdateNo();
        int t=c;
        HttpGet httppost = new HttpGet("http://vib15.freeoda.com/querry/date.txt");
        HttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        if(response==null){
            edHelper.close();
            edHelper.close();
            edHelper.close();
            return "Updates not available!.....";
        }
        do{
            HttpEntity ht = response.getEntity();
            BufferedHttpEntity buf = new BufferedHttpEntity(ht);
            InputStream is = buf.getContent();
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
             String line;
            if(t==c)
                edHelper.updateDetails(r.readLine());
            while ((line = r.readLine()) != null) {
                edHelper.updateDetails(line);
                Log.v("Querry: ", line);
            }
            edHelper.setUpdateNo(c);
            c++;
            httppost = new HttpGet("http://vib15.freeoda.com/querry/"+c+".txt");
            response = httpclient.execute(httppost);
            if(response==null){
                edHelper.close();
                edHelper.close();
                edHelper.close();
                return "Updated sccessfully!.....";
            }
            Log.v("value of c:",""+c);
        }while(true);
        } catch (IOException e) {
            e.printStackTrace();
            edHelper.close();
            edHelper.close();
            edHelper.close();
            return "Update unsuccessful!.....";
        } catch (Exception e) {
            e.printStackTrace();
            edHelper.close();
            edHelper.close();
            edHelper.close();
            return "Updated sccessfully!.....";
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }
}
