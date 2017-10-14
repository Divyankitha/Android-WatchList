package com.example.divyankitharaghavaurs.reminderapp;

import java.util.ArrayList;

/**
 * Created by divyankithaRaghavaUrs on 4/20/17.
 */

public class DisplayTaskInfo
{
    ArrayList<String> taskList = new ArrayList<>();
    ArrayList<String> dateList = new ArrayList<>();
    ArrayList<String> timeList = new ArrayList<>();
    ArrayList<String> amList = new ArrayList<>();
    String time1, time2, AMorPM, date;
    Boolean empty;
    Boolean log;

    public void setLog(Boolean log) {
        this.log = log;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    public void setAMorPM(String AMorPM) {
        this.AMorPM = AMorPM;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public void setTaskList(ArrayList<String> taskList) {
        this.taskList = taskList;
    }

    public void setDateList(ArrayList<String> dateList) {
        this.dateList = dateList;
    }

    public void setTimeList(ArrayList<String> timeList) {
        this.timeList = timeList;
    }

    public void setAmList(ArrayList<String> amList) {
        this.amList = amList;
    }
}
