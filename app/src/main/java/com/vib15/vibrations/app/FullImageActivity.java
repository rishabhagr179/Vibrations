package com.vib15.vibrations.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.vib15.vibrations.app.data.EventsDbHelper;
import com.vib15.vibrations.app.gallery.TouchImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


@SuppressLint("ShowToast")
public class FullImageActivity extends Activity {
		
	int position;
    EventsDbHelper gdh;
	Bitmap img;
	Uri imgUri;
	ArrayList<Bitmap> images=new ArrayList<Bitmap>();
		
	public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.singleview);
        gdh=new EventsDbHelper(this);
        Log.d("onCreate>>","OnCreate");
        Intent i = getIntent();
        position = i.getExtras().getInt("id");
        img=gdh.getImageByPosition(position);
		TouchImageView imageViewGallery = (TouchImageView) findViewById(R.id.image);
		imageViewGallery.setImageBitmap(img);
		imageViewGallery.setMaxZoom(4f);

	}
	
	public void saveClick(View v) {
		Log.e("takes","listener");
		String loc=Environment.getExternalStorageDirectory().getPath()+"/Vibrations_images";
		File myDir=new File(loc);
        myDir.mkdirs();
        String fname = "Image-"+ position+".jpg";
        File file = new File (myDir, fname);
        imgUri=Uri.fromFile(file);
        if (file.exists ()) file.delete (); 
        try {
        	file.createNewFile();
               FileOutputStream out = new FileOutputStream(file);               
               img.compress(Bitmap.CompressFormat.JPEG, 100, out);
               out.flush();
               out.close();
               CharSequence a ="Image saved successfully at "+loc;
               Toast.makeText(this, a, Toast.LENGTH_LONG).show();

        } catch (Exception ex) {
               ex.printStackTrace();
               }			
	}
    public void share(View v){
        saveClick(v);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, "Share Via...."));

    }
}
