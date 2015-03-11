package com.vib15.vibrations.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.vib15.vibrations.app.data.EventsDbHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class RegisterActivity extends Activity {
    private ProgressDialog pDialog;
    public static EventsDbHelper databaseHelper;
    public static Cursor c;

    JSONParser jsonParser = new JSONParser();
    EditText na, br, sem, ev, ph, em;
    Spinner events, co;
    String name, college, branch, semester, event, phone, email;
    // url to create new product
    private static String url = "http://vibrations.vib15.com/api/apiRegister";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        na = (EditText) findViewById(R.id.tfName);
        br = (EditText) findViewById(R.id.tfBranch);
        ph = (EditText) findViewById(R.id.tfContact);
        em = (EditText) findViewById(R.id.tfEmail);
        co = (Spinner) findViewById(R.id.spinnerCollege);
        events = (Spinner) findViewById(R.id.spinnerEventU);
        //populate();
    }

	/*void populate() {
		databaseHelper = new EventsDatabaseHelper(this);
		c = databaseHelper.getRegisterableRecords();
		c.moveToFirst();
		String array_spinner[];
		array_spinner = new String[c.getCount()];
		try {
			array_spinner = fillSpinner(c.getCount(), c);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		databaseHelper.close();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, array_spinner);
		events.setAdapter(adapter);
	}*/

    private String trimTitle(String title) {
        int i = 0, l = title.length();
        while (i < l) {
            if (title.charAt(i) == 40) {
                title = title.substring(0, i);
                break;
            }
            i++;

        }
        title = title.trim();
        return (title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void register(View v) {
        name = na.getText().toString();
        college = co.getSelectedItem().toString();
        branch = br.getText().toString();
        phone = ph.getText().toString();
        email = em.getText().toString();
        event = events.getSelectedItem().toString();
        if (name.equals("") || college.equals("") || phone.equals("")
                || event.equals(""))
            Toast.makeText(getApplicationContext(),
                    "Mandatory fields must be filled", Toast.LENGTH_LONG)
                    .show();

        else
            new CreateNewProduct().execute();
    }

    class CreateNewProduct extends AsyncTask<String, String, String> {
        String response;
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Registering...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            // Building Parameters
           /* List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Token", "924aeefc-258c-4201-8048-82db3d1e91c3"));
            params.add(new BasicNameValuePair("CollegeName", college));
            params.add(new BasicNameValuePair("LeaderContactNo", phone));
            params.add(new BasicNameValuePair("LeaderEmailID", email));
            params.add(new BasicNameValuePair("LeaderName", branch));
            params.add(new BasicNameValuePair("TeamName", name));
            params.add(new BasicNameValuePair("Event", event));

            // getting JSON Object
            // Note that create product url accepts POST method
            jsonParser.makeHttpRequest(url, params);
*/
            JSONObject jobj = new JSONObject();
            try {
                jobj.put("Token", "924aeefc-285c-4201-8048-82db3d1e91c3");
                jobj.put("CollegeName", college);
                jobj.put("LeaderContactNo", phone);
                jobj.put("LeaderEmailID", email);
                jobj.put("LeaderName", branch);
                jobj.put("TeamName", name);
                jobj.put("Event", event);
                Log.v("JOBJ: ",jobj.toString());
            } catch (JSONException e) {
                Log.v("JSON: ","parsing error");
            }
            response=jsonParser.makeHttpRequest(url,jobj);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            Toast.makeText(getApplicationContext(),
                    response,
                    Toast.LENGTH_LONG).show();

        }

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    String[] fillSpinner(int length, Cursor c)

            throws ParseException {

        ArrayList<String> array_spinner = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        Date onStageDate = sdf.parse("20/01/2014");
        Date eventDate;
        for (int i = 0; i < length; i++) {

            if ((System.currentTimeMillis() > onStageDate.getTime())
                    && (c.getInt(0) > 31)) {
                Log.v("Yes", c.getInt(0)+"no");
            } else {
                eventDate = null;
                eventDate = sdf.parse((c.getInt(5) + 21) + "/01/2014");

                if ((System.currentTimeMillis() < eventDate.getTime())) {
                    Log.v("Yes", "yes");
                    array_spinner.add(trimTitle(c.getString(1)));

                }
            }
            c.moveToNext();
        }
        String[] array = new String[array_spinner.size()];
        for (int i = 0; i < array_spinner.size(); i++) {
            array[i] = array_spinner.get(i);
        }
        return array;
    }
}
