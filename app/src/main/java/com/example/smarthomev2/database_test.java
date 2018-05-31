package com.example.smarthomev2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public class database_test extends AppCompatActivity {
    public AlarmManager alarmMgr;
    public PendingIntent alarmIntent;

    static itemAdapterDB itemAdapter;
    static Context thisContext;
    public static ListView myListView;
    static TextView progressTextView;

    // TextView timeTextView;
    // int variables for 2D arrays of data;
    static int  i = 0;
    static int  j = 0;

    public static int pos;
    public static String[][] devicesArray = new String[10][10];
    public static Double[][] powerFactorArray = new Double[10][10];
    static Map<String, Double> devicesMap = new LinkedHashMap<String, Double>();

    public database_test() throws SQLException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_test);

        Intent intent = new Intent(database_test.this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(database_test.this, 0, intent, 0);


        Resources res = getResources();
        myListView = findViewById(R.id.DBListView);
        progressTextView = findViewById(R.id.progressTextView);

        //    timeTextView = (TextView) findViewById(R.id.timeTextView);
        thisContext = this;
        progressTextView.setText("");

        Button btn = findViewById(R.id.getDataButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                //Move to GetData
                //traverse through array to save previous data;
                // i = 0;
                // if(j < 9) {
                //     j++;
                //} else {
                //    j = 0;
                //}
                //execute getting data from database
                GetData retrieveData = new GetData();
                retrieveData.execute("");
                //////////////////////////////////////////////////////////////////////////////////////
                //Alarm SET and repeats every 1min
                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                int interval = 1000 * 60;
                manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+interval,
                        interval, alarmIntent);
                Toast.makeText(database_test.this,"ALARM SET",Toast.LENGTH_SHORT).show();
                ///////////////////////////////////////////////////////////////////////////////////////
            }
        });

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int r, long d) {
                pos = r;
                Intent moreInfoActivity = new Intent(getApplicationContext(), moreInfoActivity.class);
                moreInfoActivity.putExtra("com.example.smarthomev2.ITEM_INDEX", r);
                startActivity(moreInfoActivity);
            }
        });



    }


    static class GetData extends AsyncTask<String, String, String> {
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
            progressTextView.setText("Connecting to database");
        }

        @Override
        protected String doInBackground(String... params) {

            Connection conn = null;
            Statement stmt = null;

            try {
                i = 0;
                if( j < 9) {
                    j++;
                } else {
                    j = 0;
                }
                Class.forName(JDBC_DRIVER); //
                conn = DriverManager.getConnection(DB_URL, DB_strings.USERNAME, DB_strings.PASSWORD);

                stmt = conn.createStatement();
                String sql = "SELECT device, powerfactor FROM devicedata";
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    String device = rs.getString("device");
                    devicesArray[i][j] = rs.getString("device");

                    double powerFactor = rs.getDouble("powerfactor");
                    powerFactorArray[i][j] = rs.getDouble("powerfactor");
                    //double pricing = rs.getFloat("price");
                    //int power = rs.getInt("power");

                    //put the data in the map to display
                    //devicesMap.put(device, powerFactor);
                    devicesMap.put(devicesArray[i][j],powerFactorArray[i][j]);
                    i++;
                }
                //Time timeStamp = rs.getTime("time");
                //msg = "Process complete";
                //timeText = timeStamp;
                //Toast.makeText(getBaseContext(), "Process complete", Toast.LENGTH_SHORT).show();
                rs.close();
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

            progressTextView.setText(this.msg);
            //timeTextView.setText((String) this.timeText);
                if (devicesMap.size() > 0) {

                    itemAdapter = new itemAdapterDB(thisContext, devicesMap);
                    myListView.setAdapter(itemAdapter);
                }

        }
    }


}