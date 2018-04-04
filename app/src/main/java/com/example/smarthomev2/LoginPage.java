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
 import android.widget.ProgressBar;
 import android.widget.TextView;
 import android.widget.Toast;
 import static com.example.smarthomev2.database_test.GetData.DB_URL;
 import static com.example.smarthomev2.database_test.GetData.JDBC_DRIVER;
         
         
 /**
  * Created by Ashley on 2/20/2018.
  */
         
         
 public class LoginPage extends AppCompatActivity {
     //Declare layout functionalities
     Button login;
     Button signup;
     EditText username, password;
     ProgressBar progressBar;
     //Connection variables
     Connection con;
 
     @Override
     protected void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_login);
         
         login = (Button) findViewById(R.id.signIn);
         signup = (Button) findViewById(R.id.signUp);
         username = (EditText) findViewById(R.id.userUsername);
         password = (EditText) findViewById(R.id.userPassword);
         progressBar = (ProgressBar) findViewById(R.id.progressBar);
         
         progressBar.setVisibility(View.GONE);

         login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick( View v) {
                                 CheckLogin checkLogin = new CheckLogin();
                                 checkLogin.execute("");
             }});

         signup.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick( View v) {
                 SignUp newUser = new SignUp();
                 newUser.execute("");
             }});
     }
 
     public class CheckLogin extends AsyncTask<String, String, String> {
         String msg = "";
         Boolean isSuccess;

         @Override
         protected void onPreExecute() {
             progressBar.setVisibility(View.VISIBLE);
                     }

         @Override
         protected String doInBackground(String... params) {
             String userName = username.getText().toString();
             String passWord = password.getText().toString();
             if(userName.equals("") || passWord.equals("")) {
                 Toast.makeText(LoginPage.this , "Login Successful", Toast.LENGTH_LONG).show();
                 msg = "Please enter a Username and Password";
             }
             else {
                 try {
                     Class.forName(JDBC_DRIVER); //
                     con = DriverManager.getConnection(DB_URL, DB_strings.USERNAME, DB_strings.PASSWORD);
                     if(con == null) {
                         msg = "Check Internet Access";
                     } else {
                         String query = "select * from logindata where username = '" + userName + "' and password = '" + passWord + "';";
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
                     return null;
                 }
 
         @Override
         protected void onPostExecute(String r) {
             progressBar.setVisibility(View.GONE);
             Toast.makeText(LoginPage.this , r, Toast.LENGTH_SHORT).show();
             if(isSuccess) {
                 Toast.makeText(LoginPage.this , "Login Successful", Toast.LENGTH_LONG).show();
             }
         }
     }

     public class SignUp extends AsyncTask<String, String, String> {
         String msg = "";
         Boolean isSuccess;

         @Override
         protected void onPreExecute() {
             progressBar.setVisibility(View.VISIBLE);
         }

         @Override
         protected String doInBackground(String... params) {
             String userName = username.getText().toString();
             String passWord = password.getText().toString();
             if(userName.equals("") || passWord.equals("")) {
                 Toast.makeText(LoginPage.this , "Login Successful", Toast.LENGTH_LONG).show();
                 msg = "Please enter a Username and Password";
             }
             else {
                 try {
                     Class.forName(JDBC_DRIVER); //
                     con = DriverManager.getConnection(DB_URL, DB_strings.USERNAME, DB_strings.PASSWORD);
                     if(con == null) {
                         msg = "Check Internet Access";
                     } else {
                         String query = "INSERT INTO logindata (username, password) VALUES ('" + userName + "', '" + passWord + "');";
                         Statement stmt = con.createStatement();
                         ResultSet rs = stmt.executeQuery(query);
                         if(rs.next()) {
                             msg = "Sign Up Successful";
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
             return null;
         }

         @Override
         protected void onPostExecute(String r) {
             progressBar.setVisibility(View.GONE);
             Toast.makeText(LoginPage.this , r, Toast.LENGTH_SHORT).show();
             if(isSuccess) {
                 Toast.makeText(LoginPage.this , "Login Successful", Toast.LENGTH_LONG).show();
             }
         }
     }
 }