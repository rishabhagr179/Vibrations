/**
 * 
 */
package com.vib15.vibrations.app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.vib15.vibrations.app.data.EventsDbHelper;
import com.vib15.vibrations.app.service.UpdateService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HomeScreen extends FragmentActivity {
    EventsDbHelper dbHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen);
        dbHelper = new EventsDbHelper(this);

		ImageButton eventBtn = (ImageButton) findViewById(R.id.btnEvent);
		eventBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeScreen.this);
                // set title
                alertDialogBuilder.setTitle("Events");
                // set dialog message
                alertDialogBuilder
                        .setMessage("Click yes to update!")
                        .setCancelable(true)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                final ProgressDialog pDialog = new ProgressDialog(
                                        HomeScreen.this);
                                pDialog.setCancelable(true);
                                pDialog.setTitle("");
                                pDialog.setMessage("Updating Events....");
                                pDialog.show();
                                dialog.dismiss();
                                Thread t = new Thread() {
                                    String s;
                                  public void  run() {
                                      UpdateEvents evupd = new UpdateEvents(HomeScreen.this);
                                     s = evupd.updateEvents();
                                      if (pDialog.isShowing()) {
                                          Intent myIntent = new Intent(HomeScreen.this,
                                                  EventDetails.class);
                                          myIntent.putExtra("updated",s);
                                          startActivityForResult(myIntent, 0);
                                      }
                                      pDialog.dismiss();
                                      Log.v("status update: ",s);
                                   // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                                  }

                                };
                                t.start();

                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                Intent myIntent = new Intent(HomeScreen.this,
                                        EventDetails.class);
                                myIntent.putExtra("updated","Not Updated!....");
                                startActivityForResult(myIntent, 0);
                                dialog.dismiss();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
			}
		});

		ImageButton scheduleBtn = (ImageButton) findViewById(R.id.btnSchedule);
		scheduleBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(), Schedule.class);
				startActivityForResult(myIntent, 0);
			}
		});

		ImageButton galleryBtn = (ImageButton) findViewById(R.id.btnGallery);
		galleryBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
                int i = 0;
                Cursor c = dbHelper.getAllGalleryRecords();
                if (c.moveToFirst()) {

                    do {
                        i++;
                    } while (c.moveToNext());

                }
                if (i==0)
                {
                    Bitmap b= BitmapFactory.decodeResource(getResources(), R.drawable.vblogo);
                    dbHelper.insertImage(1, b);
                    i++;
                }
							Intent myIntent = new Intent(HomeScreen.this,
									Gallery.class);
							startActivityForResult(myIntent, 0);
			}
		});

		ImageButton registerBtn = (ImageButton) findViewById(R.id.btnRegister);
		registerBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (isNetworkAvailable()) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
					Date lastDate;
					try {
						lastDate = sdf.parse("10/01/2016");

						if (System.currentTimeMillis() < lastDate.getTime()) {
							Intent myIntent = new Intent(view.getContext(),
									RegisterActivity.class);
							startActivityForResult(myIntent, 0);
						} else {
							Toast.makeText(HomeScreen.this,
									"Registrations have been closed!",
									Toast.LENGTH_LONG).show();
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
					Toast.makeText(HomeScreen.this, "No network available", Toast.LENGTH_SHORT).show();
			}
		});

		ImageButton whatsup = (ImageButton) findViewById(R.id.whatsupButton);
		whatsup.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
                final ProgressDialog pDialog = new ProgressDialog(
                        HomeScreen.this);
                pDialog.setCancelable(true);
                pDialog.setTitle("");
                pDialog.setMessage("Updating News....");
                pDialog.show();
                Thread t = new Thread() {
                    public void  run() {
                        UpdateLatestNews evupd = new UpdateLatestNews(HomeScreen.this);
                        evupd.UpdateNews();
                        if (pDialog.isShowing()) {
                            WhatsUpDialog wud = new WhatsUpDialog();
                            wud.show(getSupportFragmentManager(), "Whats Up");
                        }
                        pDialog.dismiss();
                        // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                    }

                };
                t.start();

			}
		});

		ImageButton knowmore = (ImageButton) findViewById(R.id.knowmoreButton);
        knowmore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                KnowMoreDialog kmd = new KnowMoreDialog();
                kmd.show(getSupportFragmentManager(), "Whats Up");
            }
        });

        ImageButton sponsors = (ImageButton) findViewById(R.id.sponsorsButton);
        sponsors.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent myIntent = new Intent(HomeScreen.this,
                        Sponsor.class);
                startActivityForResult(myIntent, 0);
            }
        });
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homescreen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            Intent i = new Intent(this, UpdateService.class);
            startService(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
