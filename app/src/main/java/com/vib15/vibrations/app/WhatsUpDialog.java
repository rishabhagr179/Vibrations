package com.vib15.vibrations.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.vib15.vibrations.app.data.EventsDbHelper;

import java.util.Date;

public class WhatsUpDialog extends DialogFragment {
	@SuppressWarnings("deprecation")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View rootView = inflater.inflate(R.layout.activity_whats_up_dialog, null);
		builder.setView(rootView);
        EventsDbHelper ndh=new EventsDbHelper(getActivity());
        EventsDbHelper ddh = new EventsDbHelper(getActivity());
        int da=ddh.getDay();
		EventsDbHelper dbHelper=new EventsDbHelper(getActivity());
		Date d=new Date();
		Cursor c=dbHelper.getHotEvents((d.getHours()*100)+d.getMinutes(), d.getDate()-10);
		TextView whatsuptv=(TextView)rootView.findViewById(R.id.whatsupText);
		String s=ndh.getNews();
        whatsuptv.setText(s);
		dbHelper.close();
        ndh.close();
		return builder.create();
		}
	}
