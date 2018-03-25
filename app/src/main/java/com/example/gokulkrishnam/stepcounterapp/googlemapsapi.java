package com.example.gokulkrishnam.stepcounterapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class googlemapsapi extends AppCompatActivity {

    private final int REQUEST_CODE_PLACEPICKER = 1;
    TextView textView;
    double lat,lng,lat1,lng1;
    ProgressDialog dialog;
    int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemapsapi);
    }

    public void mapsvieworigin(View view) {
        startPlacePickerActivity();
        i=0;
    }

    public void mapsviewdest(View view)
    {
        startPlacePickerActivity();
        i=1;
    }

    public void startPlacePickerActivity() {
        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
        Toast.makeText(getApplicationContext(),"here",Toast.LENGTH_LONG).show();
        try {
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, REQUEST_CODE_PLACEPICKER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displaySelectedPlaceFromPlacePicker(Intent data) {
        Place placeSelected = PlacePicker.getPlace(data, this);

       if(i==0)
       {
           String name = placeSelected.getName().toString();
           String address = placeSelected.getAddress().toString();
           String coord=placeSelected.getLatLng().toString();
           LatLng coordorigin=placeSelected.getLatLng();

           lat=coordorigin.latitude;
           lng=coordorigin.longitude;

           TextView enterCurrentLocation = (TextView) findViewById(R.id.textView);
           enterCurrentLocation.setText(name + "\n " + address+"\n"+coord+"\n");
       }
       else
       {
           String name = placeSelected.getName().toString();
           String address = placeSelected.getAddress().toString();
           String coord=placeSelected.getLatLng().toString();

           LatLng coordorigin=placeSelected.getLatLng();

           lat1=coordorigin.latitude;
           lng1=coordorigin.longitude;

           TextView enterCurrentLocation = (TextView) findViewById(R.id.textViewdestination);
           enterCurrentLocation.setText(name + "\n " + address+"\n"+coord+"\n");
       }


    }

    public void setpath(View view)
    {
        final Intent intent = new
                Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" +
                "saddr=" + lat + "," + lng + "&daddr=" + lat1 + "," +
                lng1));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);

    }

    public void healthdata(View view)
    {
        dialog= new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        new Jsonmethod().execute("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+lat+","+lng+"&destinations="+lat1+","+lng1+"&key=AIzaSyCyLX6JqFFayu1M-6PvL8xSBZLufJiy8Vg");
    }

    public void trackme(View view) {
         Intent i=new Intent(this,iTrack.class);
         startActivity(i);
    }


    public class Jsonmethod extends AsyncTask<String,String,String> {


        @Override
        protected String doInBackground(String... params)
        {
            HttpURLConnection connection = null;
            BufferedReader reader=null;

            try {
                URL url= new URL(params[0]);
                connection=(HttpURLConnection)url.openConnection();

                InputStream stream= connection.getInputStream();

                reader= new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer=new StringBuffer();


                String line="";

                while((line=reader.readLine())!=null)
                {
                    buffer.append(line);
                }

                String finaljson= buffer.toString();
                try {
                    JSONObject parentobject=new JSONObject(finaljson);
                    JSONArray row=parentobject.getJSONArray("rows");
                    JSONObject rowobject= row.getJSONObject(0);
                    JSONArray elements=rowobject.getJSONArray("elements");
                    JSONObject elearray=elements.getJSONObject(0);
                    JSONObject distance=elearray.getJSONObject("distance");
                    String distancebtwpoints=distance.getString("text");
                    return distancebtwpoints;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(connection!=null)
                    connection.disconnect();
                try {
                    if(reader!=null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.hide();

            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            changeactivity(s);
        }


    }
    public void changeactivity(String s)
    {
        Intent i=new Intent(getApplicationContext(),healthdata_map.class);
        i.putExtra("distance",s);
        startActivity(i);

    }


    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PLACEPICKER && resultCode == RESULT_OK) {
            displaySelectedPlaceFromPlacePicker(data);
        }
    }

}
