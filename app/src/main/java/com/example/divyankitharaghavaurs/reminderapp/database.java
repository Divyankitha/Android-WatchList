package com.example.divyankitharaghavaurs.reminderapp;

/**
 * Created by divyankithaRaghavaUrs on 4/6/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;
import android.view.View;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class database extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "UserDB.db";
    public static final String TABLE_NAME = "Appointments";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_task = "task";
    public static final String COLUMN_date= "date";
    public static final String COLUMN_time = "time";
    public static final String COLUMN_status = "status";
    public static final String COLUMN_check = "check";



    public database(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        //db.execSQL("create table UserDetails " + "(id integer primary key, FName text,LName text,Email text,Uname text,Description text,Price integer, Review text)");
        //String query =  "CREATE TABLE " + TABLE_NAME + "("+ COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_task +" TEXT," + COLUMN_date +" TEXT," + COLUMN_time +" TEXT," + COLUMN_status +" TEXT" + ");";
        //db.execSQL(query);
        db.execSQL("create table Appointments " + "(ID integer primary key autoincrement,task text,date date,time text,AMorPM text,status text)");
        db.execSQL("create table User " + "(fname text,lname text,email text,uname text primary key, password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS Appointments");
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }

    public boolean insertAppointment (String task, String DOB, String time, String check, String status) throws Exception
    {

        Log.d("Debug -->", "inside insert product of insert database class");

        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        java.util.Date date = sdf.parse(DOB);
        java.sql.Date newDate = new java.sql.Date(date.getTime());


        ContentValues contentValues = new ContentValues();
        contentValues.put("task", task);
        contentValues.put("date", DOB);
        contentValues.put("time", time);
        contentValues.put("AMorPM", check);
        contentValues.put("status", status);

        db.insert("Appointments", null, contentValues);
        return true;
    }

    public boolean insertUser (String fname, String lname,String email, String uname, String password)
    {
        boolean result;
        Log.d("Debug -->", "inside insert product of insert database class");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("fname", fname);
        contentValues.put("lname", lname);
        contentValues.put("email", email);
        contentValues.put("uname", uname);
        contentValues.put("password", password);

        long i = db.insert("User", null, contentValues);
        if(i!= -1)
        {
            result = true;
        }
        else
            result = false;

        return result;

    }

    public Cursor getData(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

       // String key = "%" +name+ "%";
        //String[] args={key, key, key, key};
        //Cursor res =  db.rawQuery( "select * from ProductDetails where Name like'" +name+"' or Description like'" +name+"'" , null );
        //Cursor res =  db.rawQuery( "select * from ProductDetails where Name like ? or Description like ? or Review like ? or Price like ?" , args );
        Cursor res =  db.rawQuery( "select * from Appointments where ID = " +id , null );
        //Cursor res =  db.rawQuery( "select * from Appointments" , null );

        return res;
    }

    public Cursor getUser(String uname)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = {uname};

        // String key = "%" +name+ "%";
        //String[] args={key, key, key, key};
        //Cursor res =  db.rawQuery( "select * from ProductDetails where Name like'" +name+"' or Description like'" +name+"'" , null );
        //Cursor res =  db.rawQuery( "select * from ProductDetails where Name like ? or Description like ? or Review like ? or Price like ?" , args );
        Cursor res =  db.rawQuery( "select * from User where uname like ?", args );

        return res;
    }

    public Cursor getTask(String date,String time, String timeNext, String check)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        System.out.println("inside getTask");
        String[] args = {date,timeNext,check};
        //Cursor res =  db.rawQuery( "select * from Appointments where date = '" +date+ "'" , null );
        //Cursor res =  db.rawQuery( "select * from Appointments where date like ? AND AMorPM like ? AND time between ? and ? " , args );
        //Cursor res =  db.rawQuery( "select * from Appointments where date like ? AND time between ? and ? AND AMorPM like ?" , args );
        Cursor res =  db.rawQuery( "select * from Appointments where date like ? AND time like ? AND AMorPM like ?" , args );
        System.out.println(res.getCount());
        return res;

        //SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy-MM-DD 'at' HH:mm:ss'Z'");
       /* SimpleDateFormat sysDate = new SimpleDateFormat("MM/dd/yyyy");
        String sysDateFormat = sysDate.format(new Date());
        System.out.println("date from system = "+sysDateFormat);
        String date = null;
        Cursor res = getData(8);
        if (res.getCount() > 0) {

            res.moveToFirst();
            do {
                date = res.getString(res.getColumnIndex("date"));

            }
            while (res.moveToNext());

        }

        System.out.println("date from db = " +date);
        if(sysDateFormat.equals(date))
        {
            System.out.println("Matched date!!!");
        }

        SimpleDateFormat sysTime = new SimpleDateFormat("HH.mm");
        String sysTimeFormat = sysTime.format(new Date());
        System.out.println("time from system = "+sysTimeFormat);
        String time = null;
        Cursor rs = getData(8);
        if (rs.getCount() > 0) {

            rs.moveToFirst();
            do {
                time = rs.getString(rs.getColumnIndex("time"));

            }
            while (rs.moveToNext());

        }

        System.out.println("time from db = " +time);
        if(sysTimeFormat.equals(time))
        {
            System.out.println("Matched!!!");
        }*/
    }


    public int numberOfRows()
    {
        Log.d("Debug -->", "inside number of rows of insert database class");
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "User");
        return numRows;
    }

    public int numberOfAppointments()
    {
        Log.d("Debug -->", "inside number of rows of insert database class");
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "Appointments");
        return numRows;
    }

    public Integer deleteTask (String title)
    {
        String[] args = {title};
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Appointments", "task = ? ",args);
    }

    public Cursor getAlltasks()
    {
        String status = "active";
        String[] args = {status};
        //ArrayList<String> array_task = new ArrayList<String>();
        Log.d("Debug -->", "inside get all of insert database class");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Appointments where status = '"+status+"'", null );
        //Cursor res =  db.rawQuery( "select * from Appointments", null );

        //res.moveToFirst();

        /*while(res.isAfterLast() == false)
        {
            array_task.add(res.getString(res.getColumnIndex(COLUMN_task)));
            res.moveToNext();
        }*/

        //return array_task;
        return res;
    }

    public Cursor getInactiveTasks()
    {
        String status = "inactive";
        String[] args = {status};
        //ArrayList<String> array_task = new ArrayList<String>();
        Log.d("Debug -->", "inside get all of insert database class");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Appointments where status = '"+status+"'", null );
        //Cursor res =  db.rawQuery( "select * from Appointments", null );

        //res.moveToFirst();

        /*while(res.isAfterLast() == false)
        {
            array_task.add(res.getString(res.getColumnIndex(COLUMN_task)));
            res.moveToNext();
        }*/

        //return array_task;
        return res;
    }

}


