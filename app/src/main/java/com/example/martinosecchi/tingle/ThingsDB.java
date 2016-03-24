package com.example.martinosecchi.tingle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import static com.example.martinosecchi.tingle.TingleDbScheme.*;

public class ThingsDB {

    private static ThingsDB sThingsDB;  //itself, singleton
    private static ThingsDBHelper mHelper;

    // methods that take dbs as parameters do NOT close them, private use preferred

    private boolean tableExists(String tableName, SQLiteDatabase db){
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    private ThingsDB(Context context) {

        mHelper = new ThingsDBHelper(context);

        // Fill database for testing purposes
        SQLiteDatabase db = mHelper.getReadableDatabase();
        if (tableExists(ThingTable.TABLE_NAME, db) && size(db)==0){
            db.close();
            db = mHelper.getWritableDatabase();
            addThing(new Thing("Android Phone", "Desk"), db);
            addThing(new Thing("phone", "desk"), db);
            addThing(new Thing("one", "two"), db);
            addThing(new Thing("Big Nerd book", "Desk"), db);
            addThing(new Thing("me", "here"), db);
        }
        db.close();
    }

    private void addThing(Thing thing, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(ThingTable.WHAT, thing.getWhat());
        values.put(ThingTable.WHERE, thing.getWhere());
        db.insert(ThingTable.TABLE_NAME, null, values);
    }

    private int size(SQLiteDatabase db){
        Cursor c = db.rawQuery("select count(*) from " + ThingTable.TABLE_NAME, null);
        c.moveToFirst();
        int count = c.getInt(0);
        c.close();
        return count;
    }

    public static ThingsDB get(Context context) {
        if (sThingsDB == null)
            sThingsDB= new ThingsDB(context);
        return sThingsDB;
    }

    public void addThing(Thing thing) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        addThing(thing, db);
        db.close();
    }
    public int getId(Thing thing){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        int res = -1;
        if (tableExists(ThingTable.TABLE_NAME, db)){
            String[] projection = { ThingTable._ID};
            Cursor c = db.query(ThingTable.TABLE_NAME,
                    projection,
                    ThingTable.WHAT +"='"+thing.getWhat()+"' and "+
                    ThingTable.WHERE + "='"+thing.getWhere() + "'",
                    null,null,null,null
            );
            c.moveToFirst();
            if (c.getCount()>0)
                res = c.getInt(c.getColumnIndexOrThrow(ThingTable._ID));

            c.close();
        }
        db.close();
        return res;
    }

    @Nullable
    public Cursor getAll(){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        if (tableExists(ThingTable.TABLE_NAME,db)){
            Cursor c = db.rawQuery("select * from " + ThingTable.TABLE_NAME, null);
            c.moveToFirst();
            return c;
        }
        return null;
    }

    @Nullable
    public Thing get(int i ){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String[] projection = {ThingTable.WHAT, ThingTable.WHERE};
        Cursor c = db.query(ThingTable.TABLE_NAME,
                projection,
                ThingTable._ID + "=" + i,
                null, null, null, null);
        c.moveToFirst();
        Thing res = null;
        if (c.getCount()>0)
            res = new Thing(
                c.getString(c.getColumnIndex(ThingTable.WHAT)),
                c.getString(c.getColumnIndex(ThingTable.WHERE))
            );
        c.close();
        db.close();
        return res;
    }
    @Nullable
    public Thing get(String w ){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String[] projection = {ThingTable.WHAT, ThingTable.WHERE};
        Cursor c = db.query(ThingTable.TABLE_NAME,
                projection,
                ThingTable.WHAT + "='" + w + "'",
                null, null, null, null);
        c.moveToFirst();
        Thing res = null;
        if (c.getCount() >0 )
            res = new Thing(
                c.getString(c.getColumnIndex(ThingTable.WHAT)),
                c.getString(c.getColumnIndex(ThingTable.WHERE))
            );
        c.close();
        db.close();
        return res;
    }

    public int size(){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        int count = size(db);
        db.close();
        return count;
    }

    public void removeThing(Thing thing){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String selection = ThingTable.WHAT +"= ? AND "+ ThingTable.WHERE + "= ?";
        String[] selArgs = {thing.getWhat(), thing.getWhere()};
        db.delete(ThingTable.TABLE_NAME, selection, selArgs);
        db.close();
    }

    public void removeAtId(int i){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String selection = ThingTable._ID +"= ?";
        String[] selArgs = {String.valueOf(i)};
        db.delete(ThingTable.TABLE_NAME, selection,selArgs);
        db.close();
    }

    public void dropTable(){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        db.execSQL("drop table if exists " + ThingTable.TABLE_NAME);
    }
    public boolean tableExists(){
        return tableExists(ThingTable.TABLE_NAME);
    }
    public boolean tableExists(String tableName){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        boolean res = tableExists(tableName, db);
        db.close();
        return res;
    }

}

