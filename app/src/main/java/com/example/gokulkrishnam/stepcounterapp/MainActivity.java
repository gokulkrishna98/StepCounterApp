package com.example.gokulkrishnam.stepcounterapp;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements SensorEventListener,ListView.OnItemClickListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 10;
    TextView count;
    TextView distancevalue;
    TextView calorievalue;
    SensorManager sensorManager;
    Sensor countSensor;
    float stepcount= (float) 5.0;
    float distance= (float) 6.0;
    float caloris=(float)7.0;
    boolean activityRunning;
    countdatabase counting;
    ListView listview;


    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private int JoB_ID=100;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        count= (TextView) findViewById(R.id.textView2);
        distancevalue=(TextView)findViewById(R.id.textViewdistancevalues);
        calorievalue=(TextView)findViewById(R.id.calorieeee);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        activityRunning = true;
        countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        counting=new countdatabase(this);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
            Toast.makeText(this, "Count sensor  available!", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            }
        }


        listview= (ListView) findViewById(R.id.left_menu);
        listview.setOnItemClickListener(this);

    }




    @Override
    public void onSensorChanged(SensorEvent event) {

        if (activityRunning) {
            stepcount=event.values[0];
            count.setText(String.valueOf(event.values[0]));
            distance= (float) (event.values[0]*0.762);
            distance=Math.round(distance);
            distancevalue.setText(String.valueOf(distance)+" m");
            caloris=(float)(distance*0.06);
            caloris=Math.round(caloris);
            calorievalue.setText(String.valueOf(caloris)+" cal");


            NotificationCompat.Builder mBuilder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_history_black_24dp)
                            .setContentTitle("No of Steps")
                            .setContentText(String.valueOf(event.values[0]));

            NotificationManager mNotificationManager =

                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.notify(001, mBuilder.build());



        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, countSensor,
                SensorManager.SENSOR_DELAY_NORMAL);

    }


    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityRunning = false;
        sensorManager.unregisterListener(this);
    }

    public void historyactivity(View view)
    {   long id;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
            String strDate = "Current Date : " + mdformat.format(calendar.getTime());
             id=counting.insert(Math.round(stepcount),Math.round(distance),Math.round(caloris),strDate);

        }
        else
        {
             id=counting.insert(Math.round(stepcount),Math.round(distance),Math.round(caloris),"api not 24");
        }


        Log.d("hello", "historyactivity: here");

        if(id<0)
        {
           Toast.makeText(getApplicationContext(),"Unsucess",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"sucess",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       if(position==1)
       {
           Intent i =new Intent(this,historyview.class);
           startActivity(i);
       }

       else if(position==3)
       {
           Intent i= new Intent(this,googlemapsapi.class);
           startActivity(i);
       }
    }


}
