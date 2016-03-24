package com.example.martinosecchi.tingle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

public class ListFragment extends Fragment {
    private static ThingsDB thingsDB;
    CursorAdapter adapter;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        thingsDB = ThingsDB.get(getContext());

        View v = inflater.inflate(R.layout.fragment_list, container, false);

        final ListView listview = (ListView) v.findViewById(R.id.listview);
        Cursor c = thingsDB.getAll();
        adapter = new ThingsCursorAdapter(getContext(), c);
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listview.setOnItemLongClickListener(mMessageClickedHandler);
        //c.close();
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

    private AdapterView.OnItemLongClickListener mMessageClickedHandler = new AdapterView.OnItemLongClickListener() {
        public boolean onItemLongClick(AdapterView parent, View v, int position, final long id) {
            AlertDialog.Builder alert = new AlertDialog.Builder( getContext() );
            alert.setTitle("Delete");
            alert.setMessage("Are you sure you want to delete record?");
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    thingsDB.removeAtId((int) id);
                    dialog.dismiss();
                    updateUI();
                }
            });
            alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();

            return true;
        }
    };

    //needs to be attached to the manager, not put in the layout for this to work
    private void updateUI() {
        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(this);
        ft.attach(this);
        ft.commit();
    }

}
