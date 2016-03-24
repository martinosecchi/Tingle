package com.example.martinosecchi.tingle;

import android.provider.BaseColumns;

/**
 * Created by martinosecchi on 16/03/16.
 */
public class TingleDbScheme {
    public TingleDbScheme(){} // empty constructor, the class is not meant to be instantiated

    public static abstract class ThingTable implements BaseColumns {
        //implementing BaseColumns I also get _ID column
        public static final String TABLE_NAME = "things";
        public static final String WHERE = "whereis"; //using name "where" gave problems in sql
        public static final String WHAT = "what";
    }
}
