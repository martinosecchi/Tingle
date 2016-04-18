package com.example.martinosecchi.tingle;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class TingleActivity extends AppCompatActivity {

    private static ThingsDB thingsDB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tingle);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container_tingle);
        if (fragment == null) {
            fragment = new TingleFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_tingle, fragment)
                    .commit(); }

        Fragment fragmentList = fm.findFragmentById(R.id.fragment_container_list);
        if (fragmentList == null) {
            fragmentList = new ListFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_list, fragmentList)
                    .commit();
        }
        //this approach so I can refresh the fragments by detaching/attaching
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            fm.beginTransaction().show(fragmentList).commit();
        } else {
            fm.beginTransaction().hide(fragmentList).commit();
        }
    }

}

