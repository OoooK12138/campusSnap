package com.example.campussnap.utils;

/**
 * 自动登录及记住密码
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.campussnap.entity.User;

public class AuthUtils {

    private static final String key = "auth";

    public static boolean getIsRemember(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(key,Activity.MODE_PRIVATE);
        boolean remember = sharedPreferences.getBoolean("remember", false);
        return remember;
    }

    public static boolean getIsAuto(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(key,Activity.MODE_PRIVATE);
        boolean auto = sharedPreferences.getBoolean("auto", false);
        return auto;
    }

    public static void setRemember(Context context,Boolean remember){
        SharedPreferences sharedPreferences = context.getSharedPreferences(key,Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean("remember",remember);
        edit.commit();
    }

    public static void setAuto(Context context,Boolean auto){
        SharedPreferences sharedPreferences = context.getSharedPreferences(key,Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean("auto",auto);
        edit.commit();
    }

    public static void setAccount(Context context,String username,String password){
        SharedPreferences sharedPreferences = context.getSharedPreferences(key,Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("username",username);
        edit.putString("password",password);
        edit.commit();
    }

    public static User getUserInfo(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(key,Activity.MODE_PRIVATE);
        User user = new User();
        user.setUsername(sharedPreferences.getString("username",null));
        user.setPassword(sharedPreferences.getString("password",null));

        if (user.getUsername()==null||user.getPassword()==null){
            return null;
        }
        return user;
    }
}
