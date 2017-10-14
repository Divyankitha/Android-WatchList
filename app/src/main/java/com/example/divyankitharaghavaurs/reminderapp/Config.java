package com.example.divyankitharaghavaurs.reminderapp;

/**
 * Created by divyankithaRaghavaUrs on 4/19/17.
 */

public class Config
{
    /*public final static String insertUser = "http://192.168.1.120:8080/ToDoList/ConnectToDB/DB/userInsert";
    public final static String getUser = "http://192.168.1.120:8080/ToDoList/ConnectToDB/DB/getUser";
    public final static String insertTask = "http://192.168.1.120:8080/ToDoList/ConnectToDB/DB/taskInsert";
    public final static String getAllTask = "http://192.168.1.120:8080/ToDoList/ConnectToDB/DB/getAllTask";
    public final static String Task = "http://192.168.1.120:8080/ToDoList/ConnectToDB/DB/Task";
    public final static String deleteTask = "http://192.168.1.120:8080/ToDoList/ConnectToDB/DB/deleteTask";*/
    public static String url="http://ec2-54-201-24-152.us-west-2.compute.amazonaws.com:8080";
    /*public final static String insertUser = "http://ec2-54-187-103-152.us-west-2.compute.amazonaws.com:8080/ToDoListApp/ConnectToDB/DB/userInsert";
    public final static String getUser = "http://ec2-54-187-103-152.us-west-2.compute.amazonaws.com:8080/ToDoListApp/ConnectToDB/DB/getUser";
    public final static String insertTask = "http://ec2-54-187-103-152.us-west-2.compute.amazonaws.com:8080/ToDoListApp/ConnectToDB/DB/taskInsert";
    public final static String getAllTask = "http://ec2-54-187-103-152.us-west-2.compute.amazonaws.com:8080/ToDoListApp/ConnectToDB/DB/getAllTask";
    public final static String Task = "http://ec2-54-187-103-152.us-west-2.compute.amazonaws.com:8080/ToDoListApp/ConnectToDB/DB/Task";
    public final static String deleteTask = "http://ec2-54-187-103-152.us-west-2.compute.amazonaws.com:8080/ToDoListApp/ConnectToDB/DB/deleteTask";
*/

    public final static String insertUser = url+"/ToDoListApp/ConnectToDB/DB/userInsert";
    public final static String getUser = url+"/ToDoListApp/ConnectToDB/DB/getUser";
    public final static String insertTask = url+"/ToDoListApp/ConnectToDB/DB/taskInsert";
    public final static String getAllTask = url+"/ToDoListApp/ConnectToDB/DB/getAllTask";
    public final static String Task = url+"/ToDoListApp/ConnectToDB/DB/Task";
    public final static String deleteTask = url+"/ToDoListApp/ConnectToDB/DB/deleteTask";


}
