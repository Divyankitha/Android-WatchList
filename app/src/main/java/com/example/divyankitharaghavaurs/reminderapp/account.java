package com.example.divyankitharaghavaurs.reminderapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.util.HashMap;

/**
 * Created by divyankithaRaghavaUrs on 4/9/17.
 */

public class account extends Activity
{

    SessionManager session;
    database mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_details);
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
    protected void onResume()
    {
        super.onResume();

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();

        userDisplayInfo U = new userDisplayInfo();
        U.setUname(user.get(SessionManager.KEY_NAME));

        new AsyncTaskAccount().execute(U);

    }

    public class AsyncTaskAccount extends AsyncTask<userDisplayInfo, String, userDisplayInfo>
    {

        HttpResponse response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected userDisplayInfo doInBackground(userDisplayInfo... params)
        {
            try {

                JSONObject requestBody = new JSONObject();
                requestBody.put("username", params[0].uname);
                String request = requestBody.toString();
                StringEntity request_param = new StringEntity(request);

                String Url= Config.getUser;
                HttpPost post = new HttpPost(Url);
                post.setEntity(request_param);
                HttpClient httpClient = new DefaultHttpClient();
                response = httpClient.execute(post);
                System.out.println("Reached after coming back from Backend API of account");
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Failed: HTTP error code :" + response.getStatusLine().getStatusCode());
                }
                else
                {
                    HttpEntity e = response.getEntity();
                    String body = EntityUtils.toString(e);
                    JSONObject response = new JSONObject(body);
                    params[0].setEmail(response.getString("email"));
                    params[0].setLname(response.getString("lname"));
                    params[0].setFname(response.getString("fname"));
                }

            }
            catch(Exception x)
            {
                throw new RuntimeException("no access",x);
            }


            return params[0];
        }

        @Override
        protected void onPostExecute(userDisplayInfo U)
        {
            super.onPostExecute(U);

            TextView fn = (TextView)findViewById(R.id.fname);
            TextView ln = (TextView)findViewById(R.id.lname);
            TextView un = (TextView)findViewById(R.id.uname);
            TextView em = (TextView)findViewById(R.id.email);

            fn.setText(U.fname);
            ln.setText(U.lname);
            un.setText(U.uname);
            em.setText(U.email);
        }
    }

    public void finishAccount(View V)
    {
        account.this.finish();
    }
}
