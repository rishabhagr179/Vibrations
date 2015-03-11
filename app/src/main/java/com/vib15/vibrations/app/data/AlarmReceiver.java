package com.vib15.vibrations.app.data;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.vib15.vibrations.app.R;

public class AlarmReceiver extends BroadcastReceiver {

	private NotificationManager mNotificationManager;
	
	
	@Override
	public void onReceive(Context context, Intent intent) {

		Bundle extras = intent.getExtras();
		String title = extras.getString("title");
		String note = extras.getString("note");
		int notificationId=extras.getInt("notificationID");
		
		
		mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		// Create the pending intent, which is basically NOW.
		PendingIntent contentIntent = PendingIntent.getActivity(context,
				0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
		// Create notification
		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
				.setContentIntent(contentIntent)
				.setSmallIcon(R.drawable.icon_launcher)
				// required for launch
				.setTicker(title+" about to begin!")
				// required for launch
				.setContentTitle(title)
				.setContentText(note)
				.setWhen(System.currentTimeMillis()); // should be set for now.
				
		mNotificationManager.notify(notificationId, notificationBuilder.build());

	}

	public void cancel(int id, Context context)
	{
		mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancel(id);
	}
}