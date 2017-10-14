package com.example.divyankitharaghavaurs.reminderapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Created by divyankithaRaghavaUrs on 4/6/17.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.util.HashMap;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class home extends Activity {


    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        SharedPreferences settings=getSharedPreferences("prefs",0);
        boolean firstRun=settings.getBoolean("firstRun",false);
        if(firstRun==false)//if running for first time

        {
            SharedPreferences.Editor editor=settings.edit();
            editor.putBoolean("firstRun",true);
            editor.commit();
            Intent i=new Intent(home.this,register.class);
            startActivity(i);
            finish();
        }
        else
        {

            setContentView(R.layout.home);
            session = new SessionManager(getApplicationContext());
            Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

            session.checkLogin();

            HashMap<String, String> user = session.getUserDetails();
            String name = user.get(SessionManager.KEY_NAME);
            String email = user.get(SessionManager.KEY_EMAIL);

            System.out.println("username:" +name);
            System.out.println("email:" +email);

        }

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
    }



    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void onClicklogout(View arg0)
    {

            session.logoutUser();
    }

    public void gotoCreate(View V)
    {
        Intent intent = new Intent(home.this, create.class);
        startActivity(intent);
    }

    public void gotoActiveTasks(View V)
    {
        Intent intent = new Intent(home.this, displayActiveTasks.class);
        startActivity(intent);
    }

    public void gotoAccount(View V)
    {
        Intent intent = new Intent(home.this, account.class);
        startActivity(intent);
    }


}
