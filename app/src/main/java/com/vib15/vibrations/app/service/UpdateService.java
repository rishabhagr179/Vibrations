package com.vib15.vibrations.app.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.vib15.vibrations.app.gallery.ImageDownload;

/**
 * Created by Agarwal on 11-03-2015.
 */
public class UpdateService extends IntentService {
    public UpdateService() {
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
            Log.v("Service: ", "Starting Download");
                ImageDownload id = new ImageDownload(getApplicationContext());
                id.downloadFiles();
                Toast.makeText(getApplicationContext(),"Gallery Updated!...",Toast.LENGTH_SHORT).show();
            }
}
