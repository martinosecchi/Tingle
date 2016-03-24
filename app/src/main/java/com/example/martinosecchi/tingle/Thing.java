package com.example.martinosecchi.tingle;

/**
 * Created by martinosecchi on 05/02/16.
 */
public class Thing {
    private String mWhat = null;
    private String mWhere = null;
    public Thing(String what, String where) {
        mWhat = what;
        mWhere = where;
    }
    @Override
    public String toString() {
        return oneLine("Item: ","is here: ");
    }
    public String getWhat() {
        return mWhat;
    }
    public void setWhat(String what) {
        mWhat = what;
    }
    public String getWhere() {
        return mWhere;
    }
    public void setWhere(String where) {
        mWhere = where;
    }
    public String oneLine(String pre, String post) {
        return pre+mWhat + " "+post + mWhere;
    }
}
