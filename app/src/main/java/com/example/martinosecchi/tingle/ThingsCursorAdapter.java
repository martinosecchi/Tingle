package com.example.martinosecchi.tingle;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by martinosecchi on 20/03/16.
 */
public class ThingsCursorAdapter extends CursorAdapter {
    public ThingsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_thing, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
            // Find fields to populate in inflated template
            TextView whatPlace = (TextView) view.findViewById(R.id.item_what);
            TextView wherePlace = (TextView) view.findViewById(R.id.item_where);
            // Extract properties from cursor
            String what = cursor.getString(cursor.getColumnIndexOrThrow(TingleDbScheme.ThingTable.WHAT));
            String  where = cursor.getString(cursor.getColumnIndexOrThrow(TingleDbScheme.ThingTable.WHERE));
            // Populate fields with extracted properties
            whatPlace.setText(what);
            wherePlace.setText(String.valueOf(where));
    }
}
