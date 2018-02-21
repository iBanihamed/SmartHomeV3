package com.example.smarthomev2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.LinkedHashMap;
import java.util.Map;

//import static ismael.banihamed.my.a353.csun.edu.myfirstapp.R.id.progressTextView;

public class database_test extends AppCompatActivity {

    itemAdapterDB itemAdapter;
    Context thisContext;
    ListView myListView;
    TextView progressTextView;
   // TextView timeTextView;
    Map<String, Double> devicesMap = new LinkedHashMap<String, Double>();

    public database_test() throws SQLException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_test);

        Resources res = getResources();
        myListView = (ListView) findViewById(R.id.DBListView);
        progressTextView = (TextView) findViewById(R.id.progressTextView);
    //    timeTextView = (TextView) findViewById(R.id.timeTextView);
        thisContext = this;

        progressTextView.setText("");
        Button btn = (Button) findViewById(R.id.getDataButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                GetData retrieveData = new GetData();
                retrieveData.execute("");
            }
        });
        //Code in progress*********************
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent moreInfoActivity = new Intent(getApplicationContext(), moreInfoActivity.class);
                moreInfoActivity.putExtra("com.example.smarthomev2.ITEM_INDEX", i);
                startActivity(moreInfoActivity);

            }
        });

        //**************************************
    }

    private class GetData extends AsyncTask<String, String, String> {

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
                Class.forName(JDBC_DRIVER); //
                conn = DriverManager.getConnection(DB_URL, DB_strings.USERNAME, DB_strings.PASSWORD);

                stmt = conn.createStatement();
                String sql = "SELECT device, powerfactor FROM devicedata";
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    String device = rs.getString("device");
                    //int power = rs.getInt("power");

                    double powerFactor = rs.getDouble("powerfactor");
                    //     double pricing = rs.getFloat("price");

                    devicesMap.put(device, powerFactor);
                }
                //Time timeStamp = rs.getTime("time");
                msg = "Process complete";
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