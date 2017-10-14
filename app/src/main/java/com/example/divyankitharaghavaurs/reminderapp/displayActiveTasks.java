package com.example.divyankitharaghavaurs.reminderapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by divyankithaRaghavaUrs on 4/9/17.
 */

public class displayActiveTasks extends Activity
{

    ListView TaskListView;
    //database mydb;
    private ArrayAdapter<String> mAdapter;
    private ArrayAdapter<String> dAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_tasks);
        //mydb = new database(this);

        //TaskListView = (ListView) findViewById(R.id.intaskList);

       /* ArrayList<String> taskList = new ArrayList<>();
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<String> timeList = new ArrayList<>();
        ArrayList<String> amList = new ArrayList<>();*/

        AllTaskInfo T = new AllTaskInfo();

        new AsyncTaskDisplayActiveTask().execute(T);

        /*if (mAdapter == null)
        {
            mAdapter = new ArrayAdapter<>(this, R.layout.active_list, R.id.task_title, taskList);
            TaskListView.setAdapter(mAdapter);
        }
        else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }*/


    }

    public class AsyncTaskDisplayActiveTask extends AsyncTask<AllTaskInfo, String, AllTaskInfo>
    {

        HttpResponse response;
        SessionManager session;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected AllTaskInfo doInBackground(AllTaskInfo... params)
        {
            ArrayList<String> taskList = new ArrayList<>();
            ArrayList<String> dateList = new ArrayList<>();
            ArrayList<String> timeList = new ArrayList<>();
            ArrayList<String> amList = new ArrayList<>();
            session = new SessionManager(getApplicationContext());

            try {

                JSONObject requestBody = new JSONObject();

                HashMap<String, String> user = session.getUserDetails();
                requestBody.put("username", user.get(SessionManager.KEY_NAME));

                String request = requestBody.toString();
                StringEntity request_param = new StringEntity(request);

                String Url= Config.getAllTask;
                HttpPost post = new HttpPost(Url);
                post.setEntity(request_param);
                HttpClient httpClient = new DefaultHttpClient();
                response = httpClient.execute(post);
                System.out.println("Reached after coming back from Backend API of display all task");
                if (response.getStatusLine().getStatusCode() != 200)
                {
                    throw new RuntimeException("Failed: HTTP error code :" + response.getStatusLine().getStatusCode());
                }
                else
                {

                    HttpEntity e = response.getEntity();
                    String body = EntityUtils.toString(e);

                    if(!body.equals("Empty"))
                    {
                        params[0].setEm(false);
                        JSONObject response = new JSONObject(body);
                        for (int i = 1; i <= response.length(); i++)
                        {
                            JSONObject j = response.getJSONObject("task" + i);
                            taskList.add(j.getString("task").concat(" |\n").concat(j.getString("date")).concat("\n").concat(j.getString("time")));
                            dateList.add(j.getString("date"));
                            timeList.add(j.getString("time"));
                        }
                        params[0].setTaskList(taskList);
                        params[0].setTimeList(timeList);
                        params[0].setDateList(dateList);
                    }
                    else
                    {
                       params[0].setEm(true);
                    }
                }

            }
            catch(Exception x)
            {
                throw new RuntimeException("no access from displayActiveTask",x);
            }


            return params[0];
        }

        @Override
        protected void onPostExecute(AllTaskInfo T)
        {
            super.onPostExecute(T);
            TaskListView = (ListView) findViewById(R.id.intaskList);
            TextView heading = (TextView) findViewById(R.id.TaskListHeading);
            if(!T.em)
            {

                heading.setText("List Of Tasks");
                if (mAdapter == null) {
                    mAdapter = new ArrayAdapter<>(displayActiveTasks.this, R.layout.active_list, R.id.task_title, T.taskList);
                    TaskListView.setAdapter(mAdapter);
                } else {
                    mAdapter.clear();
                    mAdapter.addAll(T.taskList);
                    mAdapter.notifyDataSetChanged();
                }
            }
            else
            {
                heading.setText("No Task To Display");
            }
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

     public void deleteTask(View v)
    {
        View parent = (View) v.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String t = String.valueOf(taskTextView.getText());
        int index = t.indexOf(" |");
        String task = null;
        if(index != -1)
        {
           task = t.substring(0,index);
        }

        new AsyncTaskDelete().execute(task);

    }

    public class AsyncTaskDelete extends AsyncTask<String, String, String>
    {

        HttpResponse response;
        SessionManager session;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params)
        {
            try {

                session = new SessionManager(getApplicationContext());
                JSONObject requestBody = new JSONObject();
                HashMap<String, String> user = session.getUserDetails();
                requestBody.put("username",user.get(SessionManager.KEY_NAME));
                requestBody.put("task",params[0]);
                String request = requestBody.toString();
                StringEntity request_param = new StringEntity(request);

                String Url= Config.deleteTask;
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
        protected void onPostExecute(String task)
        {
            super.onPostExecute(task);
            Intent intent = new Intent(displayActiveTasks.this,displayActiveTasks.class);
            startActivity(intent);

        }
    }

    public void finishDisplayActiveTask(View V)
    {
        Intent i = new Intent(displayActiveTasks.this,home.class);
        startActivity(i);
    }
}
