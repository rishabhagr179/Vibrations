package com.vib15.vibrations.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vib15.vibrations.app.data.AlarmReceiver;

import java.util.Calendar;


public class DetailFragment extends Fragment {

	public DetailFragment() {
		// TODO Auto-generated constructor stub


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_details,
				container, false);
		TextView dayT= (TextView) rootView.findViewById(R.id.dayText);
		TextView time = (TextView) rootView.findViewById(R.id.timeText);
		TextView details = (TextView) rootView.findViewById(R.id.detailsText);
		TextView location = (TextView) rootView.findViewById(R.id.locationText);

		final Bundle bundle = this.getArguments();

		dayT.setText("Day: "+bundle.getInt("day"));
		details.setText(bundle.getInt("Detail"));
		time.setText("Time: " + bundle.getString("Time") + "hours");
		location.setText("Location:" + bundle.getString("Venue"));

		final int alarm = bundle.getInt("alarm");
		Button eventBtn = (Button) rootView.findViewById(R.id.reminderBtnF);
		final int id = bundle.getInt("id");
		if (alarm == 0)
			eventBtn.setText("Set Reminder");
		else
			eventBtn.setText("Reminder is set!");


		eventBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR,2015);
                c.set(Calendar.MONTH,Calendar.FEBRUARY);
                c.set(Calendar.DAY_OF_MONTH,bundle.getInt("Date")+ bundle.getInt("day"));
                c.set(Calendar.HOUR_OF_DAY,bundle.getInt("hour"));
                c.set(Calendar.MINUTE, bundle.getInt("min"));
                Calendar x = Calendar.getInstance();
				Button eventBtn = (Button) rootView
						.findViewById(R.id.reminderBtnF);
				if (eventBtn.getText().equals("Set Reminder")) {
                    if(c.getTimeInMillis()>x.getTimeInMillis()){
						Toast.makeText(getActivity(), "Reminder Set!",
								Toast.LENGTH_LONG).show();
						createNotification(bundle.getInt("day"),
								bundle.getInt("hour"), bundle.getInt("min"),
								id, bundle.getString("title"));
						((EventDetails) getActivity()).setAlarm(id, 1);
						eventBtn.setText("Reminder is Set!");
					}
					else
						Toast.makeText(getActivity(), "Event Passed!.....",
								Toast.LENGTH_LONG).show();
						
				}
			}
		});

		ImageView iv = (ImageView) rootView.findViewById(R.id.imageView1);
		iv.setImageResource(bundle.getInt("Image"));
		return rootView;
	}

	public void createNotification(int day, int hour, int min, int id,
			String title) {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal.set(Calendar.YEAR, 2015);
		cal.set(Calendar.DAY_OF_MONTH, 9 + day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, 0);

		Intent alarmintent = new Intent(getActivity(), AlarmReceiver.class);

		alarmintent.putExtra("title", title);
		alarmintent.putExtra("note", "The Event is about to start at " + hour
				+ min);
		alarmintent.putExtra("notificationID", id);
		PendingIntent sender = PendingIntent.getBroadcast(getActivity(), -1,
				alarmintent, PendingIntent.FLAG_UPDATE_CURRENT
						| Intent.FILL_IN_DATA);

		AlarmManager am = (AlarmManager) getActivity().getSystemService(
				Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);

	}
}
