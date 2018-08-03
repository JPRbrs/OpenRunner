package com.openrunner.jprapps.openrunner;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "racesDB";

    private static final String TABLE_INSTANTS = "instants";

    private static final String KEY_ID = "id";
    private static final String KEY_TIME = "time";
    private static final String KEY_LAT = "latitude";
    private static final String KEY_LONG = "longitude";
    private static final String KEY_RACE = "race";

    private static final String[] COLUMNS = {KEY_ID, KEY_TIME, KEY_LAT, KEY_LONG, KEY_RACE};

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_INSTANTS_TABLE = "CREATE TABLE instants ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "time STRING, " +
                "latitude REAL, " +
                "longitude REAL, " +
                "race INTEGER )";
        db.execSQL(CREATE_INSTANTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS instants");
        this.onCreate(db);
    }

    public void addInstant(Instant instant, Context context) {
        Log.d("Add instant", instant.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LAT, instant.getLatitude());
        values.put(KEY_LONG, instant.getLongitude());
        values.put(KEY_TIME, instant.getTime());
        values.put(KEY_RACE, instant.getRace());

        db.insert(TABLE_INSTANTS, null, values);
        Toast.makeText(context,"Hello Javatpoint",Toast.LENGTH_SHORT).show();

        db.close();
    }

}
