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

import static com.example.smarthomev2.database_test.GetData.DB_URL;
import static com.example.smarthomev2.database_test.GetData.JDBC_DRIVER;


/**
 * Created by Ashley on 2/20/2018.
 */


public class LoginPage extends AppCompatActivity {

    Boolean isSuccess;
    //Declare layout functionalities
    Button login;
    EditText username, password;

    //Connection variables
    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.signIn);
        username = (EditText) findViewById(R.id.userUsername);
        password = (EditText) findViewById(R.id.userPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute("");
            }
        });
    }

    public class CheckLogin extends AsyncTask<String, String, String> {
        String msg = "";
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            String userName = username.getText().toString();
            String passWord = password.getText().toString();
            if(userName.equals("") || password.equals("")) {
                msg = "Please enter a Username and Password";
            }
            else {
                try {
                    Class.forName(JDBC_DRIVER); //
                    con = DriverManager.getConnection(DB_URL, DB_strings.USERNAME, DB_strings.PASSWORD);

                    if(con == null) {
                        msg = "Check Internet Access";
                    } else {
                        String query = "select * from logindata where username = '" + userName + " and password = '" + passWord;
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(rs.next()) {
                            msg = "Login Successful";
                            isSuccess = true;
                            con.close();
                        } else {
                            msg = "Invalid Credentials";
                            isSuccess = false;
                        }
                    }
                } catch (ClassNotFoundException e) {
                    msg = "Class not found";
                    e.printStackTrace();
                } catch (SQLException e) {
                    msg = "SQL Exception";
                    e.printStackTrace();
                }
            }
            return msg;
        }

        @Override
        protected void onPostExecute() {
            progressBar.setVisibility(View.GONE);

        }
    }
}