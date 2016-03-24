package com.example.martinosecchi.tingle;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by martinosecchi on 16/03/16.
 */
public class ThingsDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "ThingsDBHelper";
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "thingsDB.db";

    private static final String TEXT_TYPE = "text";

    public ThingsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "create table if not exists " + TingleDbScheme.ThingTable.TABLE_NAME + "(" +
                TingleDbScheme.ThingTable._ID + " integer primary key autoincrement, " +
                TingleDbScheme.ThingTable.WHAT + " " + TEXT_TYPE + ", " +
                TingleDbScheme.ThingTable.WHERE + " " + TEXT_TYPE + ")";
        Log.i("DB creation", create);
        db.execSQL(
                create
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
