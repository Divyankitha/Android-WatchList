package com.example.divyankitharaghavaurs.reminderapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Created by divyankithaRaghavaUrs on 4/6/17.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class login extends Activity {


    EditText txtUsername, txtPassword;
    Button btnLogin;
    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;

    database mydb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mydb = new database(this);


        session = new SessionManager(getApplicationContext());
        txtUsername = (EditText) findViewById(R.id.username);
        txtPassword = (EditText) findViewById(R.id.password);

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();


        btnLogin = (Button) findViewById(R.id.login);



        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                if(username.trim().length() > 0 && password.trim().length() > 0)
                {
                    LoginInfo L = new LoginInfo();
                    L.setUname(username);
                    L.setPassword(password);
                    new AsyncTaskLogin().execute(L);
                }
                else
                {
                    alert.showAlertDialog(login.this, "Login failed..", "Username/Password is incorrect", false);
                }

            }
        });
    }

    public class AsyncTaskLogin extends AsyncTask<LoginInfo, String, LoginInfo>
    {

        HttpResponse response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected LoginInfo doInBackground(LoginInfo... params)
        {
            String passcode = null;
            try {
                    System.out.println("username from login:"+params[0].uname);
                    JSONObject requestBody = new JSONObject();
                    requestBody.put("username", params[0].uname);
                    String request = requestBody.toString();
                    StringEntity request_param = new StringEntity(request);

                    String Url = Config.getUser;
                    HttpPost post = new HttpPost(Url);
                    post.setEntity(request_param);
                    HttpClient httpClient = new DefaultHttpClient();
                    response = httpClient.execute(post);
                    System.out.println("Reached after coming back from Backend API of account");
                    if (response.getStatusLine().getStatusCode() != 200)
                    {
                        throw new RuntimeException("Failed: HTTP error code :" + response.getStatusLine().getStatusCode());
                    }
                    else
                    {
                        HttpEntity e = response.getEntity();
                        String body = EntityUtils.toString(e);
                        System.out.println(body);
                        if(body.equals("Empty"))
                        {
                            params[0].setEmpty("Empty");
                        }
                        else
                        {
                            JSONObject response = new JSONObject(body);
                            params[0].setEmail(response.getString("email"));
                            params[0].setUsername(response.getString("username"));
                            params[0].setLname(response.getString("lname"));
                            params[0].setFname(response.getString("fname"));
                            params[0].setPasscode(response.getString("password"));
                        }
                    }

            }
            catch(Exception x)
            {
                throw new RuntimeException("no access from login",x);
            }


            return params[0];
        }

        @Override
        protected void onPostExecute(LoginInfo L)
        {
            super.onPostExecute(L);

            if(L.empty.equals("Empty"))
            {
                alert.showAlertDialog(login.this, "Login failed..", "Username/Password is incorrect", false);
            }
            else
            {
                if (L.uname.trim().length() > 0 && L.passcode.trim().length() > 0) {
                    System.out.println("uname:" + L.uname);
                    System.out.println("username:" + L.username);
                    System.out.println("passcode:" + L.passcode);
                    System.out.println("password:" + L.password);

                    if (L.uname.equals(L.username) && L.password.equals(L.passcode)) {

                        // Creating user login session
                        session.createLoginSession(L.uname, L.email);
                        session = new SessionManager(getApplicationContext());
                        HashMap<String, String> user = session.getUserDetails();
                        System.out.println("Session name after login:" + user.get(SessionManager.KEY_NAME));


                        Intent intentService = new Intent(login.this,NotifyService.class);
                        startService(intentService);

                        // Staring MainActivity
                        Intent i = new Intent(getApplicationContext(), home.class);
                        startActivity(i);
                        finish();



                    }
                    else
                    {

                        alert.showAlertDialog(login.this, "Login failed..", "Username/Password is incorrect", false);
                    }
                }
                else
                {

                    alert.showAlertDialog(login.this, "Login failed..", "Please enter username and password", false);
                }
            }


        }
    }

    public void gotoRegister(View V)
    {
        Intent intent = new Intent(login.this,register.class);
        startActivity(intent);
    }

    public void finishLogin(View v)
    {
        login.this.finish();
    }
}

