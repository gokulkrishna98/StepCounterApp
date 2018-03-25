package com.example.gokulkrishnam.stepcounterapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class healthdata_map extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthdata_map);
        Intent intent=getIntent();
        String distance=intent.getStringExtra("distance");
        String[] update=new String[5];
        update=distance.split("m");
        float distanceaccu= (float) (Float.valueOf(update[0])*(1609.34));
        float steps= Math.round(distanceaccu*1.315);
        float calories=Math.round(distanceaccu*0.06);

        TextView textView=(TextView)findViewById(R.id.helthdatamaps);

        textView.setText("Distance:   "+distanceaccu+"mi\n\nCalories:   "+calories+"cal\n\nSteps:   "+steps);

    }
}
