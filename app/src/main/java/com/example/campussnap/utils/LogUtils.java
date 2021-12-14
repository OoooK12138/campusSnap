package com.example.campussnap.utils;

import android.util.Log;

public class LogUtils {
    private static final String TAG = "mybug";

    public static void debug(String msg){
        Log.v(TAG,msg);
    }
}
