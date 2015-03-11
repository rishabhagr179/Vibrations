package com.vib15.vibrations.app.gallery;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.vib15.vibrations.app.R;
import com.vib15.vibrations.app.data.EventsDbHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class ImageDownload {

	Context context;
	Bitmap bmImg;
	EventsDbHelper dbHelper;

	public ImageDownload(Context context) {
		dbHelper = new EventsDbHelper(context);
		this.context=context;
	}

	public void downloadFiles() {
        Log.v("ImageDownload: ","Downloading images");
		int i = 0;
		Bitmap img;
		Cursor c = dbHelper.getAllGalleryRecords();
		if (c.moveToFirst()) {

			do {
				i++;
			} while (c.moveToNext());

		}
		if (i==0)
        {
                Bitmap b=BitmapFactory.decodeResource(context.getResources(), R.drawable.vblogo);
                dbHelper.insertImage(1, b);
                i++;
        }
		Log.v("size",""+c.getCount());
		
		
		Log.v(""+isNetworkAvailable(),""+isNetworkAvailable());
		

		
		while(isNetworkAvailable()) {
			i++;
			img=downloadfile(i);
			if(img == null)
				break;
			dbHelper.insertImage(i,img);
			Log.v("Download", "image" + i);
		}
		dbHelper.close();
	}

	private Bitmap downloadfile(int fileNumber) {
		String fileurl = "http://vib15.freeoda.com/images/" + fileNumber
				+ ".jpg";
		URL myfileurl = null;
		try {
			myfileurl = new URL(fileurl);

		} catch (MalformedURLException e) {

			e.printStackTrace();
		}

		try {
			HttpURLConnection conn = (HttpURLConnection) myfileurl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			int length = conn.getContentLength();
			if (length == -1)
				return (null);

			InputStream is = conn.getInputStream();
			BitmapFactory.Options options = new BitmapFactory.Options();

			bmImg = BitmapFactory.decodeStream(is, null, options);

			return (bmImg);

			// dialog.dismiss();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// Toast.makeText(PhotoRating.this,
			// "Connection Problem. Try Again.", Toast.LENGTH_SHORT).show();
			return null;
		}

	}

	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
}
