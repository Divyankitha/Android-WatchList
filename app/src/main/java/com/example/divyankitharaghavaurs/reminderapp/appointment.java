package com.example.divyankitharaghavaurs.reminderapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by divyankithaRaghavaUrs on 4/6/17.
 */

public class appointment extends Activity
{
    AlertDialogManager alert = new AlertDialogManager();
    Bundle bundle;
    TextView display,displayT,displayE;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
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
        bundle = getIntent().getExtras();

        ArrayList<String> task = new ArrayList<String>();
        task.addAll(bundle.getStringArrayList("task"));
        System.out.println(task);
        ArrayList<String> date = new ArrayList<String>();
        date.addAll(bundle.getStringArrayList("date"));
        ArrayList<String> time = new ArrayList<String>();
        time.addAll(bundle.getStringArrayList("time"));


        System.out.println("Inside appointment" +bundle.getString("empty") );
        setContentView(R.layout.display_task);
        display = (TextView) findViewById(R.id.task_display);
        displayT = (TextView) findViewById(R.id.time_display);
        displayE = (TextView) findViewById(R.id.noTask);

        if(bundle.getString("empty").equals("no"))
        {
            display.setText("TASK\n--------------------");
            displayT.setText("TIME\n--------------------");
            for (int i = 0; i < task.size(); i++)
            {
                String task_dis = task.get(i);
                System.out.println("inside appointment task:"+task_dis);
                //String date_dis = date.get(i);
                String time_dis = time.get(i);
                //setContentView(R.layout.display_task);
                if (task_dis.equals(" "))
                {

                }
                else
                {

                    display.append(" \n" + task_dis + "\n--------------------");
                    displayT.append(" \n" + time_dis + "\n--------------------");
                }

            }
        }

        else
        {
            displayE.setText("No Tasks Found For Next Hour");
        }
       /* setContentView(R.layout.display_task);
        display = (TextView) findViewById(R.id.task_display);
        display.append(task + " \n");
        displayT = (TextView) findViewById(R.id.time_display);
        displayT.append(time + " \n");*/
    }

    public void ok(View V)
    {

        appointment.this.finish();
    }

    public void delete(View V)
    {

        appointment.this.finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
