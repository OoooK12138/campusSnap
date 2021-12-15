package com.example.campussnap.common;

import android.app.Application;
import android.os.StrictMode;
import android.widget.Toast;

import com.example.campussnap.entity.User;

import java.util.TimeZone;

public class AppContext extends Application {
    private static AppContext instance;
    private User user;

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

    public void userLogin(User user){
        this.user = user;
    }

    public String getUsername(){
        return this.user.getUsername();
    }
}
