package com.example.divyankitharaghavaurs.reminderapp;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by divyankithaRaghavaUrs on 4/9/17.
 */

public class displayInactiveTasks extends Activity
{
    ListView TaskListView;
    database mydb;
    private ArrayAdapter<String> mAdapter;
    private ArrayAdapter<String> dAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inactive_tasks);
        mydb = new database(this);
        TaskListView = (ListView) findViewById(R.id.intaskList);
        ArrayList<String> taskList = new ArrayList<>();
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<String> timeList = new ArrayList<>();
        ArrayList<String> amList = new ArrayList<>();

        Cursor rs = mydb.getInactiveTasks();
        if (rs.getCount() > 0)
        {

            rs.moveToFirst();
            do {

                String task = rs.getString(rs.getColumnIndex("task"));
                taskList.add(rs.getString(rs.getColumnIndex("task")));
                String date = rs.getString(rs.getColumnIndex("date"));
                dateList.add(rs.getString(rs.getColumnIndex("date")));
                String time = rs.getString(rs.getColumnIndex("time"));
                timeList.add(rs.getString(rs.getColumnIndex("time")));
                String check = rs.getString(rs.getColumnIndex("AMorPM"));
                amList.add(rs.getString(rs.getColumnIndex("AMorPM")));
                System.out.println("task: " + task);
                System.out.println("date: " + date);
                System.out.println("time: " + time);
                System.out.println("AMorPM: " + check);
            }
            while (rs.moveToNext());
        }
        else
        {
            Log.d("Debug -->", "error out of bound");
        }

        if (mAdapter == null)
        {
            mAdapter = new ArrayAdapter<>(this, R.layout.inactive_list, R.id.intask_title, taskList);
            TaskListView.setAdapter(mAdapter);
        }
        else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }


    }

    public void deleteTask(View v)
    {
        TextView title = (TextView)findViewById(R.id.task_title);
        String str = title.getText().toString();
        int res = mydb.deleteTask(str);
    }

    public void finishDisplayActiveTask(View V)
    {
        displayInactiveTasks.this.finish();
    }
}
