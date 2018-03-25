package com.example.gokulkrishnam.stepcounterapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class countdatabase{

    static class countdata extends SQLiteOpenHelper {

        public static final String TABLE_NAME="countdata";
        public static final String COUNTS="countsteps";
        public static final String DATA_BASENAME="counter";
        public static final int DATABASE_VERSION=11;
        public static final String UID="_id";
        public static final String COUNT="counter";
        public static final String CALORIES="calories";
        public static final String DISTANCE="distance";
        public static final String DATE="date";
        public static final String CREATE_TABLE= "CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+DATE+" STRING, "+COUNT+" INTEGER, "+CALORIES +" INTEGER, "+DISTANCE+" INTEGER );";
        public static final String DROP_TABLE= "DROP TABLE IF EXISTS"+TABLE_NAME;
        public Context context;


        private countdata(Context context){
            super(context,DATA_BASENAME,null,DATABASE_VERSION);
            Log.d("hello", "historyactivity: here");
            this.context=context;
            Log.d("vivz", "onCreate: fasdjfokj");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            Log.d("vivz", "onCreate: fasdjfokj");
            try{
                db.execSQL(CREATE_TABLE);
            }catch (SQLException e){
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("vivz", "onUpgrade: jakkkkkas");
            try{
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }


    countdata helper;
    public countdatabase(Context context){

        helper=new countdata(context);
    }
    public long insert(int stepcount,int caloriesvalues,int distancevalue,String datevalue){
        SQLiteDatabase dbi=helper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(countdata.COUNT,stepcount);
        contentValues.put(countdata.CALORIES,caloriesvalues);
        contentValues.put(countdata.DISTANCE,distancevalue);
        contentValues.put(countdata.DATE,datevalue);
        long id=dbi.insert(helper.TABLE_NAME,null,contentValues);
        return id;
    }

    public String getAlldata()
    {
        SQLiteDatabase db= helper.getWritableDatabase();
        String[] coloumns={countdata.UID,countdata.COUNT,countdata.CALORIES,countdata.DISTANCE,countdata.DATE};
        Cursor cursor=db.query(helper.TABLE_NAME,coloumns,null,null,null,null,null);
        StringBuffer reader=new StringBuffer();

        while (cursor.moveToNext())
        {
            int cid=cursor.getInt(0);
            int counter=cursor.getInt(1);
            int calorievalue=cursor.getInt(2);
            int distancevalue=cursor.getInt(3);
            String datevalue=cursor.getString(4);

            reader.append("Input "+ cid +"\nSteps "+counter+"\nCalories"+calorievalue+"\nDistance "+distancevalue+"\n date  "+datevalue+",");

        }
        String returndata=reader.toString();

        return returndata;

    }


}
