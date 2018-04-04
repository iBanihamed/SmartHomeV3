package com.example.smarthomev2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
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
        graph.setTitle("Power Factor Over Last 10 Hours");
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Power Factor");
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(10);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(1.0);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.addSeries(series);

    }


}
