package com.example.campussnap.bean;

import android.util.Log;

public class UserBean {
    private static String username;

    public static void setUsername(String name){
        username = name;
    }

    public static String getUsername(){
        Log.v("mybug",username + "");
        return username; }
}
