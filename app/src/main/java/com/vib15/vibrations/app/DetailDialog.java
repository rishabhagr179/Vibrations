package com.vib15.vibrations.app;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.vib15.vibrations.app.data.AlarmReceiver;
import java.util.Calendar;
import java.util.Date;


public class DetailDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View rootView = inflater.inflate(R.layout.dialog_details, null);
		builder.setView(rootView);
		TextView time = (TextView) rootView.findViewById(R.id.timeText);
		TextView details = (TextView) rootView.findViewById(R.id.detailsText);
		TextView location = (TextView) rootView.findViewById(R.id.locationText);
		TextView title = (TextView) rootView.findViewById(R.id.titleText);

		final Bundle bundle = this.getArguments();
		title.setText(bundle.getString("title"));
		details.setText(bundle.getInt("Detail"));
		time.setText("Time:" + bundle.getString("Time"));
		location.setText("Venue:" + bundle.getString("Venue"));


		int alarm = bundle.getInt("alarm");
		Log.v("Alarm state", alarm + "");
		Button eventBtn = (Button) rootView.findViewById(R.id.reminderBtnD);
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
                Date d = new Date();
                Button eventBtn = (Button) rootView
                        .findViewById(R.id.reminderBtnD);
               /* if (eventBtn.getText().equals("Set Reminder")) {
                    if ((d.getDate() < 9 + bundle.getInt("day"))
                            || ((d.getDate() == 9 + bundle.getInt("day")) && (d
                            .getHours() < bundle.getInt("hour")))
                            || ((d.getDate() == 9 + bundle.getInt("day"))
                            && (d.getHours() == bundle.getInt("hour")) && (d
                            .getMinutes() < bundle.getInt("min"))))*/
                if (eventBtn.getText().equals("Set Reminder")) {
                    if(c.getTimeInMillis()>x.getTimeInMillis()){
                        Toast.makeText(getActivity(), "Reminder Set!",
                                Toast.LENGTH_LONG).show();
                        createNotification(bundle.getInt("day"),
                                bundle.getInt("hour"), bundle.getInt("min"),
                                id, bundle.getString("title"));
                        ((Schedule) getActivity()).setAlarm(id, 1);
                        eventBtn.setText("Reminder is Set!");
                    } else
                        Toast.makeText(getActivity(), "Cannot set reminder",
                                Toast.LENGTH_LONG).show();

                }
			}
		});

		return builder.create();
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
