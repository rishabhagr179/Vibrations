package com.vib15.vibrations.app;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import com.vib15.vibrations.app.data.Constants;
import com.vib15.vibrations.app.data.EventsDbHelper;

import java.util.ArrayList;


public class Schedule extends FragmentActivity {
    private EventsDbHelper dHelper;
	Cursor c;
	EventsDbHelper dbHelper;
	ArrayList<Button> buttons;
	int day = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		dbHelper = new EventsDbHelper(this);
        dHelper=new EventsDbHelper(this);

		setContentView(R.layout.activity_schedule);

		setScheduleTable(day);
	}

	

	private void setScheduleTable(int day) {

		this.day = day;

		buttons = new ArrayList<Button>();
		buttons.add((Button) findViewById(R.id.day1button));
		buttons.add((Button) findViewById(R.id.day2button));
		buttons.add((Button) findViewById(R.id.day3button));

		int i = 0;

		while (i < 3) {
            if (day == (i + 1)){
                    buttons.get(i).setSelected(true);
                    buttons.get(i).setTextColor(Color.parseColor("#814715"));
            }
            else
            {
                    buttons.get(i).setSelected(false);
                    buttons.get(i).setTextColor(Color.parseColor("#F7E8BD"));
                    }
            i++;

    }
		setSemHallRow();
		setC8_9_10Row();
		setC11_12_13Row();
		setIqRow();
		setStageAreaRow();

	}

	private void setSemHallRow() {
		buttons = new ArrayList<Button>();
		buttons.add((Button) findViewById(R.id.SemHall3));
		buttons.add((Button) findViewById(R.id.SemHall4));
		buttons.add((Button) findViewById(R.id.SemHall5));
		buttons.add((Button) findViewById(R.id.SemHall6));
		buttons.add((Button) findViewById(R.id.SemHall7));
		buttons.add((Button) findViewById(R.id.SemHall8));
		buttons.add((Button) findViewById(R.id.SemHall9));
		buttons.add((Button) findViewById(R.id.SemHall10));

		c = dbHelper.getRecordsSortedByTimeOfDay(day,
				Constants.Location.SEMINAR_HALL);

		c.moveToFirst();
		int sTime, eTime, cTime = 1130;
		int i = 0;
		TableRow.LayoutParams params;
		c.moveToPrevious();
		while (c.moveToNext()) {

			sTime = c.getInt(6);
			eTime = c.getInt(7);

			while (true) {
				if (sTime <= cTime)
					break;
				
				i++;
				if ((cTime % 100) == 30)
					cTime = cTime + 70;
				else
					cTime = cTime + 30;

			}

			if (((eTime - sTime) == 30) || ((eTime - sTime) == 70)) {
				buttons.get(i).setText(c.getString(1).trim());
				if (cTime % 100 == 30)
					cTime = cTime + 70;
				else
					cTime = cTime + 30;
				i++;
			} else if ((eTime - sTime) == 100) {

				buttons.get(i + 1).setVisibility(View.VISIBLE);
				params = (TableRow.LayoutParams) buttons.get(i)
						.getLayoutParams();
				params.span = 2;
                params = (TableRow.LayoutParams) buttons.get(i+1)
                        .getLayoutParams();
                params.span = 0;
				buttons.get(i+1).setLayoutParams(params);

				buttons.get(i).setText(c.getString(1));
				cTime = cTime + 100;

				i += 2;
			} else if (((eTime - sTime) == 130) || ((eTime - sTime) == 170)) {

				params = (TableRow.LayoutParams) buttons.get(i)
						.getLayoutParams();
				params.span = 3;
				buttons.get(i).setLayoutParams(params);
                params = (TableRow.LayoutParams) buttons.get(i+1)
                        .getLayoutParams();
                params.span = 0;
                buttons.get(i+1).setLayoutParams(params);
                params = (TableRow.LayoutParams) buttons.get(i+2)
                        .getLayoutParams();
                params.span = 0;
                buttons.get(i+2).setLayoutParams(params);
				buttons.get(i).setText(c.getString(1));
				if (cTime % 100 == 30)
					cTime = cTime + 170;
				else
					cTime = cTime + 130;
				buttons.get(i + 1).setVisibility(View.VISIBLE);
				buttons.get(i + 2).setVisibility(View.VISIBLE);
				i += 3;
			}

		}

	}

	private void setC8_9_10Row() {
		buttons = new ArrayList<Button>();
		buttons.add((Button) findViewById(R.id.c8_9_10_3));
		buttons.add((Button) findViewById(R.id.c8_9_10_4));
		buttons.add((Button) findViewById(R.id.c8_9_10_5));
		buttons.add((Button) findViewById(R.id.c8_9_10_6));
		buttons.add((Button) findViewById(R.id.c8_9_10_7));
		buttons.add((Button) findViewById(R.id.c8_9_10_8));
		buttons.add((Button) findViewById(R.id.c8_9_10_9));
		buttons.add((Button) findViewById(R.id.c8_9_10_10));

		c = dbHelper.getRecordsSortedByTimeOfDay(day,
				Constants.Location.C8_9_10);

		c.moveToFirst();
		int sTime, eTime, cTime = 1130;
		int i = 0;
		TableRow.LayoutParams params;
		c.moveToPrevious();
		while (c.moveToNext()) {

			sTime = c.getInt(6);
			eTime = c.getInt(7);

			while (true) {
				if (sTime <= cTime)
					break;
				
				i++;
				if ((cTime % 100) == 30)
					cTime = cTime + 70;
				else
					cTime = cTime + 30;

			}

			if (((eTime - sTime) == 30) || ((eTime - sTime) == 70)) {
				buttons.get(i).setText(c.getString(1).trim());
				if (cTime % 100 == 30)
					cTime = cTime + 70;
				else
					cTime = cTime + 30;
				i++;
			} else if ((eTime - sTime) == 100) {

				buttons.get(i + 1).setVisibility(View.VISIBLE);
				params = (TableRow.LayoutParams) buttons.get(i)
						.getLayoutParams();
				params.span = 2;
				buttons.get(i).setLayoutParams(params);
                params = (TableRow.LayoutParams) buttons.get(i+1)
                        .getLayoutParams();
                params.span = 0;
                buttons.get(i+1).setLayoutParams(params);
				buttons.get(i).setText(c.getString(1));
				cTime = cTime + 100;

				i += 2;
			} else if (((eTime - sTime) == 130) || ((eTime - sTime) == 170)) {

				params = (TableRow.LayoutParams) buttons.get(i)
						.getLayoutParams();
				params.span = 3;
				buttons.get(i).setLayoutParams(params);
                params = (TableRow.LayoutParams) buttons.get(i+1)
                        .getLayoutParams();
                params.span = 0;
                buttons.get(i+1).setLayoutParams(params);
                params = (TableRow.LayoutParams) buttons.get(i+2)
                        .getLayoutParams();
                params.span = 0;
                buttons.get(i+2).setLayoutParams(params);
				buttons.get(i).setText(c.getString(1));
				if (cTime % 100 == 30)
					cTime = cTime + 170;
				else
					cTime = cTime + 130;
				buttons.get(i + 1).setVisibility(View.VISIBLE);
				buttons.get(i + 2).setVisibility(View.VISIBLE);
				i += 3;
			}

		}
	}

	private void setC11_12_13Row() {
		buttons = new ArrayList<Button>();
		buttons.add((Button) findViewById(R.id.c11_12_13_3));
		buttons.add((Button) findViewById(R.id.c11_12_13_4));
		buttons.add((Button) findViewById(R.id.c11_12_13_5));
		buttons.add((Button) findViewById(R.id.c11_12_13_6));
		buttons.add((Button) findViewById(R.id.c11_12_13_7));
		buttons.add((Button) findViewById(R.id.c11_12_13_8));
		buttons.add((Button) findViewById(R.id.c11_12_13_9));
		buttons.add((Button) findViewById(R.id.c11_12_13_10));

		c = dbHelper.getRecordsSortedByTimeOfDay(day,
				Constants.Location.C11_12_13);

		c.moveToFirst();
		int sTime, eTime, cTime = 1130;
		int i = 0;
		TableRow.LayoutParams params;
		c.moveToPrevious();
		while (c.moveToNext()) {

			sTime = c.getInt(6);
			eTime = c.getInt(7);

			while (true) {
				if (sTime <= cTime)
					break;
				
				i++;
				if ((cTime % 100) == 30)
					cTime = cTime + 70;
				else
					cTime = cTime + 30;

			}

			if (((eTime - sTime) == 30) || ((eTime - sTime) == 70)) {
				buttons.get(i).setText(c.getString(1).trim());
				if (cTime % 100 == 30)
					cTime = cTime + 70;
				else
					cTime = cTime + 30;
				i++;
			} else if ((eTime - sTime) == 100) {

				buttons.get(i + 1).setVisibility(View.VISIBLE);

				params = (TableRow.LayoutParams) buttons.get(i)
						.getLayoutParams();
				params.span = 2;
				buttons.get(i).setLayoutParams(params);
                params = (TableRow.LayoutParams) buttons.get(i+1)
                        .getLayoutParams();
                params.span = 0;
                buttons.get(i+1).setLayoutParams(params);
				buttons.get(i).setText(c.getString(1));
				cTime = cTime + 100;

				i += 2;
			} else if (((eTime - sTime) == 130) || ((eTime - sTime) == 170)) {

				params = (TableRow.LayoutParams) buttons.get(i)
						.getLayoutParams();
				params.span = 3;
				buttons.get(i).setLayoutParams(params);
                params = (TableRow.LayoutParams) buttons.get(i+1)
                        .getLayoutParams();
                params.span = 0;
                buttons.get(i+1).setLayoutParams(params);
                params = (TableRow.LayoutParams) buttons.get(i+2)
                        .getLayoutParams();
                params.span = 0;
                buttons.get(i+2).setLayoutParams(params);
				buttons.get(i).setText(c.getString(1));
				if (cTime % 100 == 30)
					cTime = cTime + 170;
				else
					cTime = cTime + 130;
				buttons.get(i + 1).setVisibility(View.VISIBLE);
				buttons.get(i + 2).setVisibility(View.VISIBLE);
				i += 3;
			}

			}
	}

	private void setIqRow() {
		buttons = new ArrayList<Button>();
		buttons.add((Button) findViewById(R.id.IQ3));
		buttons.add((Button) findViewById(R.id.IQ4));
		buttons.add((Button) findViewById(R.id.IQ5));
		buttons.add((Button) findViewById(R.id.IQ6));
		buttons.add((Button) findViewById(R.id.IQ7));
		buttons.add((Button) findViewById(R.id.IQ8));
		buttons.add((Button) findViewById(R.id.IQ9));
		buttons.add((Button) findViewById(R.id.IQ10));

		c = dbHelper.getRecordsSortedByTimeOfDay(day,
				Constants.Location.IQ);


		c.moveToFirst();
		int sTime, eTime, cTime = 1130;
		int i = 0;
		TableRow.LayoutParams params;
		c.moveToPrevious();
		while (c.moveToNext()) {

			sTime = c.getInt(6);
			eTime = c.getInt(7);

			while (true) {
				if (sTime <= cTime)
					break;
				
				i++;
				if ((cTime % 100) == 30)
					cTime = cTime + 70;
				else
					cTime = cTime + 30;

			}

			if (((eTime - sTime) == 30) || ((eTime - sTime) == 70)) {
				buttons.get(i).setText(c.getString(1).trim());
				if (cTime % 100 == 30)
					cTime = cTime + 70;
				else
					cTime = cTime + 30;
				i++;
			} else if ((eTime - sTime) == 100) {
				Log.v("",c.getString(1)+cTime+" "+eTime+" "+i);
				buttons.get(i + 1).setVisibility(View.VISIBLE);
				params = (TableRow.LayoutParams) buttons.get(i)
						.getLayoutParams();
				params.span = 2;
				buttons.get(i).setLayoutParams(params);
                params = (TableRow.LayoutParams) buttons.get(i+1)
                        .getLayoutParams();
                params.span = 0;
                buttons.get(i+1).setLayoutParams(params);
				buttons.get(i).setText(c.getString(1));
				cTime = cTime + 100;

				i += 2;
			} else if (((eTime - sTime) == 130) || ((eTime - sTime) == 170)) {

				params = (TableRow.LayoutParams) buttons.get(i)
						.getLayoutParams();
				params.span = 3;
				buttons.get(i).setLayoutParams(params);
                params = (TableRow.LayoutParams) buttons.get(i+1)
                        .getLayoutParams();
                params.span = 0;
                buttons.get(i+1).setLayoutParams(params);
                params = (TableRow.LayoutParams) buttons.get(i+2)
                        .getLayoutParams();
                params.span = 0;
                buttons.get(i+2).setLayoutParams(params);
				buttons.get(i).setText(c.getString(1));
				if (cTime % 100 == 30)
					cTime = cTime + 170;
				else
					cTime = cTime + 130;
				buttons.get(i + 1).setVisibility(View.VISIBLE);
				buttons.get(i + 2).setVisibility(View.VISIBLE);
				i += 3;
			}

		}	}

	private void setStageAreaRow() {

		Log.v("Where", "here");
		buttons = new ArrayList<Button>();
		buttons.add((Button) findViewById(R.id.stageArea12));
		buttons.add((Button) findViewById(R.id.stageArea13));
		buttons.add((Button) findViewById(R.id.stageArea14));
		buttons.add((Button) findViewById(R.id.stageArea15));
		buttons.add((Button) findViewById(R.id.stageArea16));

		c = dbHelper.getRecordsSortedByTimeOfDay(day,
				Constants.Location.STAGE_AREA);
		Log.v("Count", c.getCount() + "");
		int i = 0;
		while (c.moveToNext()) {
			Log.v("Where", "here");
			buttons.get(i).setText(c.getString(1));
			if (c.isLast() && (i < 4)) {
				buttons.get(i + 1).setText(c.getString(1));
				break;
			}
			i++;

		}

	}

	public void onClickDay1(View view) {
		setContentView(R.layout.activity_schedule);
		setScheduleTable(1);

	}

	public void onClickDay2(View view) {
		setContentView(R.layout.activity_schedule);
		setScheduleTable(2);

	}

	public void onClickDay3(View view) {
		setContentView(R.layout.activity_schedule);
		setScheduleTable(3);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		dbHelper.close();
		super.onDestroy();
	}

	public void onClickAnyButton(View view) {
		DetailDialog dialog=new DetailDialog();
		Button button=(Button)view;
		String clickedOn= button.getText().toString();
		if ((!clickedOn.equals(""))&&(!clickedOn.equals("Inauguration")))
		{int date = dHelper.getDay();
			Cursor c1;
			c1=dbHelper.getDetailsofEvent(clickedOn);
			c1.moveToFirst();
			int detail=c1.getInt(2);
			String time=c1.getString(6);
			String venue=c1.getString(4);
			int ImageID=c1.getInt(8);
			int day=c1.getInt(5);
			int hour=c1.getInt(6)/100;
			int min=c1.getInt(6)%100;
			int id=c1.getInt(0);
			int alarmstate=c1.getInt(9);
			String title=c1.getString(1);
			Bundle bundle=new Bundle();
            bundle.putInt("Date",date);
			bundle.putInt("Detail", detail);
			bundle.putString("Time", time);
			bundle.putString("Venue", venue);
			bundle.putInt("Image", ImageID);
			bundle.putInt("day", day);
			bundle.putInt("hour", hour);
			bundle.putInt("min", min);
			bundle.putInt("id", id);
			bundle.putInt("alarm", alarmstate);
			bundle.putString("title", title);	
			dialog.setArguments(bundle);
			dialog.show(getSupportFragmentManager(), "Details");
		}
	}

	public void setAlarm(int id, int val)
	{
		dbHelper.setAlarm(id, val);
	}
}
