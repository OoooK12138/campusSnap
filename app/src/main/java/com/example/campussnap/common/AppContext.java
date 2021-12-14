package com.example.campussnap.common;

import android.app.Application;
import android.os.StrictMode;
import android.widget.Toast;

import java.util.TimeZone;

public class AppContext extends Application {
    private static AppContext instance;

    public static AppContext getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        TimeZone time = TimeZone.getTimeZone("GMT+8");
        TimeZone.setDefault(time);
        super.onCreate();
        //将应用程序本身的上下文实例赋值给instance变量

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.instance = AppContext.this;
    }

    public static void makeToast(String msg){
        Toast.makeText(instance,msg,Toast.LENGTH_SHORT).show();
    }
}
