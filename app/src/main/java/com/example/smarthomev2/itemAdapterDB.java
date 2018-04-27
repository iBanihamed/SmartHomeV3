package com.example.smarthomev2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import static ismael.banihamed.my.a353.csun.edu.myfirstapp.R.array.devices;

/**
 * Created by Abdu on 11/8/2017.
 */

public class itemAdapterDB extends BaseAdapter {

    LayoutInflater mInflator;
    Map<String, Double> map;
    List<String> device;
    List<Double> powerFactor;
    List<String> time;
    List<String> date;
    List<Integer> power;
    List<Boolean> onOff;

    public itemAdapterDB(Context c, Map m) {
        mInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        map = m;
        device = new ArrayList<String>(map.keySet());
        powerFactor = new ArrayList<Double>(map.values());
        //Still need to figure out how to send data to turn off and on device
    }

    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public Object getItem(int position) {
        return device.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflator.inflate(R.layout.item_layout, null);
        TextView devicesTextView = (TextView) v.findViewById(R.id.devicesTextView);
        TextView pfTextView = (TextView) v.findViewById(R.id.PFTextView);
        // TextView costTextView = (TextView) v.findViewById(R.id.costTextView);

        devicesTextView.setText(device.get(position).toString());
        pfTextView.setText(powerFactor.get(position).toString());

        //stays comented out until we can query data from database
        setRowColor(v, powerFactor.get(position));
        return v;
    }

    private void setRowColor(View v, Double powerFactor) {
        int level = getLevel(powerFactor);
        switch (level) {
            case 1:
                v.setBackgroundResource(R.color.colorLimeGreen);
                break;
            case 2:
                v.setBackgroundResource(R.color.colorYellow);
                break;
            case 3:
                v.setBackgroundResource(R.color.colorOrange);
                break;
            case 4:
                v.setBackgroundResource(R.color.colorRed);
                break;
            default:
                v.setBackgroundResource(R.color.colorPrimary);
                break;
        }
    }

    private int getLevel(Double pf) {
        int level;
        if (pf < 0.3) {   //really bad pf
            level = 4;
        } else if (pf < 0.5) {    //bad pf
            level = 3;
        } else if (pf < 0.7) { //decent pf
            level = 2;
        } else if (pf < 0.9) {  //good pf
            level = 1;
        } else {  //great pf
            level = 1;
        }
        return level;
    }
}