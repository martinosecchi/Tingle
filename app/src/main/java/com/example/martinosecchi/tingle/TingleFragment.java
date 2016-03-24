package com.example.martinosecchi.tingle;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telecom.Conference;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;


public class TingleFragment extends Fragment {

    public TingleFragment() {
        // Required empty public constructor
    }

    // GUI variables
    private Button searchThing;
    private Button addThing;
    private Button listBtn;
    private TextView lastAdded;
    private TextView newWhat, newWhere;
    private static ThingsDB thingsDB;

    private void updateUI(){
        int s= thingsDB.size();
        if (s>0) {
            lastAdded.setText(thingsDB.get(s-1).toString());
        }
        if( getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            Fragment frg =  (ListFragment) getFragmentManager().findFragmentById(R.id.fragment_container_list);
            if( frg != null) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();
            }
        }
    }

    private void goToListActivity(){
        startActivity( new Intent( getContext(), ListActivity.class));
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tingle, container, false);

        thingsDB = ThingsDB.get(getContext());

        //Accessing GUI element
        lastAdded= (TextView) v.findViewById(R.id.last_thing_text);
        updateUI();
        // Button
        addThing = (Button) v.findViewById(R.id.new_thing_button);
        searchThing = (Button) v.findViewById(R.id.search_button);
        listBtn = (Button) v.findViewById(R.id.listButton);
        // Textfields for describing a thing
        newWhat= (TextView) v.findViewById(R.id.what_text);
        newWhere= (TextView) v.findViewById(R.id.where_text);

        addThing.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if ((newWhat.getText().length() >0) && (newWhere.getText().length() >0)){
                    thingsDB.addThing(
                            new Thing(newWhat.getText().toString(),
                                    newWhere.getText().toString()));
                    newWhat.setText("");
                    newWhere.setText("");
                    updateUI();
                }
            }
        });

        searchThing.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(newWhat.getText().length() > 0){
                    String where = "Not found";
                    Thing thing = thingsDB.get(newWhat.getText().toString());
                    if (thing!= null && thing.getWhere().length() > 0)
                        where=thing.getWhere();
                    Toast toast = Toast.makeText( getContext() , where, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 100);
                    toast.show();
                }
            }
        });

        listBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                goToListActivity();
            }
        });

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
