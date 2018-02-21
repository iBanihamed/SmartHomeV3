package com.example.smarthomev2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class moreInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        int i = 0;
        int j = 0;

        int powerArray[];
        powerArray = new int[9];
        powerArray[1] = 4;

        GraphView graph = (GraphView) findViewById(R.id.powerGraph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
            new DataPoint(0, powerArray[1]),
            new DataPoint(1,5),
            new DataPoint(2,3),
            new DataPoint(3,2)
        });
        graph.addSeries(series);
    }


}
