package com.example.smarthomev2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.smarthomev2.database_test.myListView;
import static com.example.smarthomev2.database_test.pos;
import static com.example.smarthomev2.database_test.powerFactorArray;

public class moreInfoActivity extends AppCompatActivity {
    //Switch switcher= (Switch) findViewById(R.id.switchA);
    static boolean on;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        int i = 0;
        int j = 1;

        Integer xi;
        Double yi;
        DataPoint[] values = new DataPoint[9];

        Switch switcher= (Switch) findViewById(R.id.switchA);

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
        graph.setTitle("Power Factor Over Last 8 Hours");
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Power Factor");
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(8);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(1.0);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.addSeries(series);

        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SendData sendData = new SendData();
                if(isChecked) {
                    on = true;
                    sendData.execute("");
                } else {
                    on = false;
                    sendData.execute("");
                }
            }
        });
    }

    static class SendData extends AsyncTask<String, String, String> {
        String msg = "";
        String timeText;
        //JDBC driver name and database URL
        static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        //Example
        static final String DB_URL = "jdbc:mysql://" +
                DB_strings.DATABASE_URL + "/" +
                DB_strings.DATABASE_NAME;

        @Override
        protected void onPreExecute() {
           // progressTextView.setText("Connecting to database");
        }

        @Override
        protected String doInBackground(String... params) {

            Connection conn = null;
            Statement stmt = null;

            try {
                Class.forName(JDBC_DRIVER); //
                conn = DriverManager.getConnection(DB_URL, DB_strings.USERNAME, DB_strings.PASSWORD);

                stmt = conn.createStatement();
                String sql = "UPDATE devicedata SET OnOff =" + on + " WHERE ID =" +(pos + 1)+";"; //added 1 to position to align with database ID
                stmt.executeUpdate(sql);

                //Time timeStamp = rs.getTime("time");
                msg = "Process complete";
                //timeText = timeStamp;
                //Toast.makeText(getBaseContext(), "Process complete", Toast.LENGTH_SHORT).show();
                stmt.close();
                conn.close();

            } catch (SQLException connError) {
                msg = "An exception was thrown for JDBC.";
                connError.printStackTrace();
            } catch (ClassNotFoundException e) {
                msg = "A class not found exception was thrown";
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String msg) {


        }
    }

}
