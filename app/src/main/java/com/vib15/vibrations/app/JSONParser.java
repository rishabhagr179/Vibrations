package com.vib15.vibrations.app;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class JSONParser {

	 InputStream is ;
	    static JSONObject jObj = null;
	    static String json = "";
	 
	    // constructor
	    public JSONParser() {
	 
	    }
	 
	    // function get json from url
	    // by making HTTP POST or GET mehtod
	    public String makeHttpRequest(String url,
	            JSONObject params) {
	 
	        // Making HTTP request
	        try {
	 
	            // check for request method
	        
	                // request method is POST
	                // defaultHttpClient
	                DefaultHttpClient httpClient = new DefaultHttpClient();
	                HttpPost httpPost = new HttpPost(url);
                //x-www-form-urlencoded
                    httpPost.setHeader("Content-type","application/json");
	                httpPost.setEntity(new StringEntity(params.toString()));
	                Log.v("URL: ",httpPost.toString());
	                HttpResponse httpResponse = httpClient.execute(httpPost);
	                HttpEntity httpEntity = httpResponse.getEntity();
	                is = httpEntity.getContent();
                String s="Failed";
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                if(br!=null)
                s = br.readLine();
	                Log.v("",s);
                JSONObject jobj = new JSONObject(s);
                String a = jobj.getString("Message");
                Log.v("",a);
                return a;
	 
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (JSONException e) {
                e.printStackTrace();
            }

return "Please try again Later!...";
        }
}
