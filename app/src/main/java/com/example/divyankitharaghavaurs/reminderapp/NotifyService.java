package com.example.divyankitharaghavaurs.reminderapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.sql.Time;
import java.util.Calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by divyankithaRaghavaUrs on 4/6/17.
 */

public class NotifyService extends Service implements SensorEventListener
{
    private SensorManager msensorManager;
    private Sensor proximity;
    database mydb;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mydb = new database(this);
        msensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        proximity=msensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d("Debug -->", "Inside service onStart");
        super.onStartCommand(intent, flags, startId);
        msensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);

        return START_STICKY;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        float val = event.values[0];
        String check = null;

        if(event.values[0]== 0)
        {
            //code to display appointment
            SimpleDateFormat sysDate = new SimpleDateFormat("MM/dd/yyyy");
            String sysDateFormat = sysDate.format(new Date());
            System.out.println("date from system = "+sysDateFormat);

            SimpleDateFormat sysTime = new SimpleDateFormat("HH:mm:ss");
            String sysTimeFormat = sysTime.format(new Date());
            System.out.println("time from system = "+sysTimeFormat);


            Date dateNext = new Date();
            dateNext.setTime(System.currentTimeMillis()+(60*60*1000));
            String dateNextFormat = sysTime.format(dateNext);
            System.out.println("date from system = "+dateNextFormat);


            DisplayTaskInfo D = new DisplayTaskInfo();
            D.setTime1(sysTimeFormat);
            D.setTime2(dateNextFormat);
            D.setDate(sysDateFormat);

            new AsyncTaskNotifyService().execute(D);


            /*intent.putStringArrayListExtra("task",task);
            intent.putStringArrayListExtra("date",date);
            intent.putStringArrayListExtra("time",time);
            intent.putStringArrayListExtra("status",status);
            intent.putStringArrayListExtra("check",checkAM);
            startActivity(intent);*/
        }

    }

    public class AsyncTaskNotifyService extends AsyncTask<DisplayTaskInfo, String, DisplayTaskInfo>
    {

        HttpResponse response;
        SessionManager session;
        Intent intent = new Intent(NotifyService.this,appointment.class);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected DisplayTaskInfo doInBackground(DisplayTaskInfo... params)
        {
            ArrayList<String> taskList = new ArrayList<>();
            ArrayList<String> dateList = new ArrayList<>();
            ArrayList<String> timeList = new ArrayList<>();
            ArrayList<String> amList = new ArrayList<>();

            try {
                session = new SessionManager(getApplicationContext());

                JSONObject requestBody = new JSONObject();
                session = new SessionManager(getApplicationContext());
                HashMap<String, String> user = session.getUserDetails();
                if (!session.isLoggedIn())
                {
                    params[0].setLog(false);
                }
                else
                {
                params[0].setLog(true);
                requestBody.put("username", user.get(SessionManager.KEY_NAME));
                requestBody.put("date", params[0].date);
                requestBody.put("time1", params[0].time1);
                requestBody.put("time2", params[0].time2);
                    System.out.println("time" +params[0].time1+ " " +params[0].time2);
                String request = requestBody.toString();
                StringEntity request_param = new StringEntity(request);

                String Url = Config.Task;
                HttpPost post = new HttpPost(Url);
                post.setEntity(request_param);
                HttpClient httpClient = new DefaultHttpClient();
                response = httpClient.execute(post);
                System.out.println("Reached after coming back from Backend API of NotifyService");
                if (response.getStatusLine().getStatusCode() != 200)
                {
                    throw new RuntimeException("Failed: HTTP error code :" + response.getStatusLine().getStatusCode());
                }
                else
                {

                    HttpEntity e = response.getEntity();
                    String body = EntityUtils.toString(e);
                    System.out.println("notify :" + body);
                    if (body.equals("Empty"))
                    {
                        intent.putExtra("empty", "yes");
                    }
                    else
                    {
                        intent.putExtra("empty", "no");
                        JSONObject response = new JSONObject(body);
                        for (int i = 1; i <= response.length(); i++)
                        {
                            JSONObject j = response.getJSONObject("task" + i);
                            taskList.add(j.getString("task"));
                            timeList.add(j.getString("time"));

                        }
                        params[0].setTaskList(taskList);
                        params[0].setTimeList(timeList);
                        params[0].setDateList(dateList);
                        params[0].setAmList(amList);
                    }
                }

            }
            }
            catch(Exception x)
            {
                throw new RuntimeException("no access",x);
            }


            return params[0];
        }

        @Override
        protected void onPostExecute(DisplayTaskInfo D)
        {
            super.onPostExecute(D);
            if(D.log) {
                System.out.println("post exec of notify service");
                System.out.println("Notify service:"+D.taskList);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putStringArrayListExtra("task", D.taskList);
                intent.putStringArrayListExtra("date", D.dateList);
                intent.putStringArrayListExtra("time", D.timeList);
                startActivity(intent);
            }
            else
            {
                System.out.println("Working");
                Intent intent = new Intent(NotifyService.this,loginAgain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

        }
    }

}
