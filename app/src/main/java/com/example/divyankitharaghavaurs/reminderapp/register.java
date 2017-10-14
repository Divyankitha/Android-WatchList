package com.example.divyankitharaghavaurs.reminderapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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
import java.sql.Time;

/**
 * Created by divyankithaRaghavaUrs on 4/6/17.
 */

public class register extends Activity
{
    database mydb;
    AlertDialogManager alert = new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mydb = new database(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void gotoLogin(View V) throws  Exception
    {

        EditText fn =(EditText)findViewById(R.id.fname);
        EditText ln = (EditText)findViewById(R.id.lname);
        EditText eid = (EditText)findViewById(R.id.eid);
        EditText un =(EditText)findViewById(R.id.uname);
        EditText pass = (EditText)findViewById(R.id.pass);

        userInfo U = new userInfo();
        U.setFname(fn.getText().toString());
        U.setLname(ln.getText().toString());
        U.setEmail(eid.getText().toString());
        U.setUname(un.getText().toString());
        U.setPassword(pass.getText().toString());

        new AsyncTaskRegister().execute(U);


       /* Intent intent = new Intent(register.this,login.class);
        intent.putExtra("username",uname);
        intent.putExtra("password", password);
        intent.putExtra("email", email);
        intent.putExtra("fname", fname);
        startActivity(intent);*/
    }

    public class AsyncTaskRegister extends AsyncTask<userInfo, String, userInfo>
    {

        HttpResponse response;
        SessionManager session;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected userInfo doInBackground(userInfo... params)
        {
            try {

                JSONObject requestBody = new JSONObject();
                requestBody.put("fname",params[0].fname);
                requestBody.put("lname",params[0].lname);
                requestBody.put("email", params[0].email);
                requestBody.put("username", params[0].uname);
                requestBody.put("password", params[0].password);
                String request = requestBody.toString();
                StringEntity request_param = new StringEntity(request);

                String Url= Config.insertUser;
                HttpPost post = new HttpPost(Url);
                post.setEntity(request_param);
                HttpClient httpClient = new DefaultHttpClient();
                response = httpClient.execute(post);
                System.out.println("Reached after coming back from Backend API");
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Failed: HTTP error code :" + response.getStatusLine().getStatusCode());
                }

            }
            catch(Exception x)
            {
                throw new RuntimeException("no access",x);
            }


            return params[0];
        }

        @Override
        protected void onPostExecute(userInfo U)
        {
            super.onPostExecute(U);
            session = new SessionManager(getApplicationContext());

            alert.showAlertDialog(register.this, "Registration", "Successfull", false);

            session.createLoginSession(U.uname,U.email);
            System.out.println("Session name after register:" +SessionManager.KEY_NAME);
            Intent i = new Intent(getApplicationContext(), home.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public void finishRegister(View V)
    {
        register.this.finish();
    }
}
