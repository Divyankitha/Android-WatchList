package com.example.divyankitharaghavaurs.reminderapp;

import java.util.ArrayList;

/**
 * Created by divyankithaRaghavaUrs on 4/20/17.
 */

public class AllTaskInfo
{
    ArrayList<String> taskList = new ArrayList<>();
    ArrayList<String> dateList = new ArrayList<>();
    ArrayList<String> timeList = new ArrayList<>();
    ArrayList<String> amList = new ArrayList<>();
    Boolean em;

    public void setEm(Boolean em) {
        this.em = em;
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
