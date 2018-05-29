package com.example.smarthomev2;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import static com.example.smarthomev2.database_test.myListView;
import static com.example.smarthomev2.database_test.pos;
import static com.example.smarthomev2.database_test.powerFactorArray;

public class moreInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        Switch tg;
        int position = pos;
        //going to have to use queue to implement
        double[] pfArray = new double[0];
        int i = 0;
        int j = 1;
        Integer xi;
        Double yi;

        DataPoint[] values = new DataPoint[9];
        while(j <= 9) {
            xi = i;
            if(powerFactorArray[pos][j] != null){
                yi = powerFactorArray[pos][j];
            } else {
                yi = 0.0;
            }
            values[i] = new DataPoint(xi, yi);
            i++;
            j++;
        }

        GraphView graph = (GraphView) findViewById(R.id.powerGraph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(values);
        graph.addSeries(series);


        final SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean tgpref = preferences.getBoolean(Integer.toString(pos), true);  //default is true

        tg = (Switch) findViewById(R.id.toggle1);
        tg.setChecked(pos==1);


        tg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(Integer.toString(pos), tg.isChecked()); // value to store
                editor.commit();
            }
        });
       /* LineGraphSeries<DataPoint> series  = new LineGraphSeries<>(new DataPoint[] {
            new DataPoint(0,powerFactorArray[pos][1]),
            new DataPoint(1, powerFactorArray[pos][2]), //cant graph the rest of the values because they null button needs to be clicked on more
            new DataPoint(2, powerFactorArray[pos][3]),
            new DataPoint(3, powerFactorArray[pos][4]),
                new DataPoint(4, powerFactorArray[pos][5]),
                new DataPoint(1, powerFactorArray[pos][6]),
                new DataPoint(1, powerFactorArray[pos][7]),
                new DataPoint(1, powerFactorArray[pos][8]),
                new DataPoint(1, powerFactorArray[pos][9]),
                new DataPoint(1, powerFactorArray[pos][10])
        });
        graph.addSeries(series); */
    }
}



