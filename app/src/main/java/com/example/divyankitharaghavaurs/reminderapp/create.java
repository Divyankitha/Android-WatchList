package com.example.divyankitharaghavaurs.reminderapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import static java.lang.Integer.parseInt;

/**
 * Created by divyankithaRaghavaUrs on 4/6/17.
 */

public class create extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener
{
    database mydb;
    NotifyService nService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new);
        mydb = new database(this);
    }

    public void insertAppointment(View V) throws Exception
    {
        Log.d("Debug -->", "inside insert function of insert activity");

        EditText ta =(EditText)findViewById(R.id.task);
        EditText d = (EditText)findViewById(R.id.date);
        EditText ti = (EditText)findViewById(R.id.time);

        TaskInfo T = new TaskInfo();

        T.setTask(ta.getText().toString());
        T.setDate(d.getText().toString());
        T.setTime(ti.getText().toString());

        new AsyncTaskCreateTask().execute(T);

        /*Intent intentService = new Intent(create.this,NotifyService.class);
        startService(intentService);*/

    }



    public class AsyncTaskCreateTask extends AsyncTask<TaskInfo, String, TaskInfo>
    {

        HttpResponse response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected TaskInfo doInBackground(TaskInfo... params)
        {
            SessionManager session;
            session = new SessionManager(getApplicationContext());
            HashMap<String, String> user = session.getUserDetails();

            try {

                JSONObject requestBody = new JSONObject();
                requestBody.put("task",params[0].task);
                requestBody.put("date",params[0].date);
                System.out.println("date got from UI :" +params[0].date);
                requestBody.put("time", params[0].time);
                //requestBody.put("check", params[0].AMorPM);
                requestBody.put("username", user.get(SessionManager.KEY_NAME));
                String request = requestBody.toString();
                StringEntity request_param = new StringEntity(request);

                String Url= Config.insertTask;
                HttpPost post = new HttpPost(Url);
                post.setEntity(request_param);
                HttpClient httpClient = new DefaultHttpClient();
                response = httpClient.execute(post);
                System.out.println("Reached after coming back from Backend API");
                if (response.getStatusLine().getStatusCode() != 200)
                {
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
        protected void onPostExecute(TaskInfo U)
        {
            super.onPostExecute(U);

            Intent intent = new Intent(create.this, successDialog.class);
            startActivity(intent);

            /*Intent intentService = new Intent(create.this,NotifyService.class);
            startService(intentService);*/

        }
    }



    public void showDatePickerDialog(View v)
    {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
        EditText dob = (EditText)findViewById(R.id.time);
        dob.setText(hourOfDay + ":" + minute + ":00");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        EditText dob = (EditText)findViewById(R.id.date);
        dob.setText(month+1 + "/" +dayOfMonth+ "/" +year);

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
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

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void finishCreate(View V)
    {
        create.this.finish();
    }
}

