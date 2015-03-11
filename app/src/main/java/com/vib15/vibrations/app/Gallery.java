package com.vib15.vibrations.app;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;
import com.vib15.vibrations.app.data.EventsDbHelper;
import com.vib15.vibrations.app.gallery.CoverFlow;
import java.util.ArrayList;

public class Gallery extends Activity {
    ArrayList<Bitmap> images=new ArrayList<Bitmap>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		EventsDbHelper gdh=new EventsDbHelper(this);
		images=gdh.getImages();
		gdh.close();
		CoverFlow coverFlow;
		coverFlow = (CoverFlow) findViewById(R.id.gallery);
		ImageAdapter imga=new ImageAdapter(this);
		coverFlow.setAdapter(imga);
		coverFlow.setSelection(0);
		
		coverFlow.setOnItemClickListener(new OnItemClickListener(){
				public void onItemClick(AdapterView<?> parent, View v,int position, long id)
		        {       	
		            Intent i = new Intent(Gallery.this, FullImageActivity.class);
		            i.putExtra("id", position);
		            startActivity(i);

		             }
				});
		if(!isNetworkAvailable())
			Toast.makeText(this, "Cannot download images\nNo internet connection", Toast.LENGTH_LONG).show();
	}
	public class ImageAdapter extends BaseAdapter {
		int mGalleryItemBackground;
		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return images.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressWarnings("deprecation")
		public View getView(int position, View convertView, ViewGroup parent) {

			// Use this code if you want to load from resources
			ImageView i = new ImageView(mContext);
			i.setImageBitmap(images.get(position));
			i.setLayoutParams(new CoverFlow.LayoutParams(300, 200));
			i.setScaleType(ImageView.ScaleType.FIT_CENTER);

			// Make sure we set anti-aliasing otherwise we get jaggies
			BitmapDrawable drawable = (BitmapDrawable) i.getDrawable();
			drawable.setAntiAlias(true);
			return i;

		}

		/**
		 * Returns the size (0.0f to 1.0f) of the views depending on the
		 * 'offset' to the center.
		 */
		public float getScale(boolean focused, int offset) {
			/* Formula: 1 / (2 ^ offset) */
			return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
		}

	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
