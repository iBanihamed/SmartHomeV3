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

        int position = pos;
        //going to have to use queue to implement

        GraphView graph = (GraphView) findViewById(R.id.powerGraph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
            new DataPoint(0,powerFactorArray[pos][1])
           /* new DataPoint(1, powerFactorArray[pos][2]), //cant graph the rest of the values because they null button needs to be clicked on more
            new DataPoint(2, powerFactorArray[pos][3]),
            new DataPoint(3, powerFactorArray[pos][4]),
                new DataPoint(4, powerFactorArray[pos][5]),
                new DataPoint(1, powerFactorArray[pos][6]),
                new DataPoint(1, powerFactorArray[pos][7]),
                new DataPoint(1, powerFactorArray[pos][8]),
                new DataPoint(1, powerFactorArray[pos][9]),
                new DataPoint(1, powerFactorArray[pos][10]) */
        });
        graph.addSeries(series);
    }


}
