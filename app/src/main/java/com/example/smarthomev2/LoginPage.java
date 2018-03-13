package com.example.smarthomev2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by Ashley on 2/20/2018.
 */




public class LoginPage extends AppCompatActivity {
/*
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //Example
    static final String DB_URL = "jdbc:mysql://" +
            DB_strings.DATABASE_URL + "/" +
            DB_strings.DATABASE_NAME;
*/
    public LoginPage() throws SQLException{
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void signInAttempt(View v)   //why do you need to put View v? Does it take the xml?
    {
        TextView message = findViewById(R.id.sampleMessage);
        EditText checkUsername = (EditText) findViewById(R.id.userUsername);
        EditText checkPassword = (EditText) findViewById(R.id.userPassword);

        GetData retrieveData = new GetData();
        retrieveData.execute("");

        //if it passes login then input this
        Intent settingsIntent = new Intent(this, settings.class); //in place of this is getApplicationContext()
        startActivity(settingsIntent);
        //else if it DOES NOT pass
        checkUsername.setText("");
        checkPassword.setText("");


        //check if username is found & if password matches found username
        /*
        Boolean valid = validateSignIn(checkUsername.toString(), checkPassword.toString());
        if (valid) message.setText("It works!"); //nothing should happen because they should change pages?
        else {
            message.setTextColor(this.getResources().getColor(R.color.colorRed));
            message.setText("Invalid Credentials");
        }
        */
    }


    /*
    public Boolean validateSignIn(String checkUsername, String checkPassword) {
        //call on database

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(JDBC_DRIVER); //
            conn = DriverManager.getConnection(DB_URL, DB_strings.USERNAME, DB_strings.PASSWORD);

            stmt = conn.createStatement();
            String sql = "SELECT username, password FROM loginData";
            ResultSet rs = stmt.executeQuery(sql);

            boolean exitWhileLoop = false;
            while (rs.next() && exitWhileLoop == false) {
                String username = rs.getString("username");


                String password = rs.getString("password");
                if(username.equals(checkUsername) && password.equals(checkPassword))
                {
                    exitWhileLoop = true;
                    return true;
                }
            }
            //Time timeStamp = rs.getTime("time");
            //timeText = timeStamp;
            //Toast.makeText(getBaseContext(), "Process complete", Toast.LENGTH_SHORT).show();
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException connError) {
            connError.printStackTrace();
        } catch (ClassNotFoundException e) {
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

    */


    private class GetData extends AsyncTask<String, String, String> {

        String msg = "";
        String timeText;
        //JDBC driver name and database URL
        static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        //Example
        static final String DB_URL = "jdbc:mysql://" +
                DB_strings.DATABASE_URL + "/" +
                DB_strings.DATABASE_NAME;
        TextView message = findViewById(R.id.sampleMessage);    //--ashley modified

        @Override
        protected void onPreExecute() {
            message.setText("Connecting to database");
        }

        @Override
        protected String doInBackground(String... params) {

            Connection conn = null;
            Statement stmt = null;

            try {
                Class.forName(JDBC_DRIVER); //
                conn = DriverManager.getConnection(DB_URL, DB_strings.USERNAME, DB_strings.PASSWORD);

                stmt = conn.createStatement();
                String sql = "SELECT username, password FROM logindata";
                ResultSet rs = stmt.executeQuery(sql);

                boolean exitWhileLoop = false;
                while (rs.next() && exitWhileLoop == false) {
                    String username = rs.getString("username");


                    String password = rs.getString("password");
                    if(username.equals("ashley") && password.equals("likestoparty"))    //-ashley modified
                    {
                        message.setText("It's connected! Thank God!");
                        exitWhileLoop = true;
                    }
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

    }


}
