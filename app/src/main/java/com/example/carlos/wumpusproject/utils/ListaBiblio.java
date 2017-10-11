package com.example.carlos.wumpusproject.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.carlos.wumpusproject.R;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by b43834 on 11/10/2017.
 */

public class ListaBiblio extends ArrayAdapter<String>{

    private ArrayList<String> nombres = new ArrayList<>();
    private Activity activity;

    public ListaBiblio(Activity activity, ArrayList<String> mensajes){
        super(activity, R.layout.biblioteca);
        this.activity = activity;
        this.nombres = mensajes;
    }

    static class ViewHolder{
        protected TextView nameTextView;
    }

    public int getCount(){
        return nombres.size();
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(final int position, View convertView, final ViewGroup parent){
       View view = null;
        LayoutInflater inflator = activity.getLayoutInflater();
        view = inflator.inflate(R.layout.biblioteca, null);
        final ViewHolder viewHolder = new ViewHolder();

        viewHolder.nameTextView = (TextView) view.findViewById(R.id.nameTextView);
        viewHolder.nameTextView.setText(nombres.get(position));
        return view;
    }

}
