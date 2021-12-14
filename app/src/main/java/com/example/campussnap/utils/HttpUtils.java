package com.example.campussnap.utils;

import android.os.StrictMode;

import com.alibaba.fastjson.JSONObject;
import com.example.campussnap.common.Param;
import com.example.campussnap.common.Result;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class HttpUtils {
    private static final String URL = "http://49.235.134.191:8080";
    private static final int TIMEOUT_IN_MILLIONS = 5000;

    public static Result GetRequest(String method) throws Exception {
        URL url = new URL(URL + method);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(TIMEOUT_IN_MILLIONS);
        int responseCode = connection.getResponseCode();

        if (responseCode == 200) {
            InputStream inputStream = connection.getInputStream();

            String input;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            while ((input = reader.readLine()) != null) {
                stringBuffer.append(input);
            }
            LogUtils.debug(stringBuffer.toString());
            return JSONObject.parseObject(stringBuffer.toString(),Result.class);
        }else {
            throw new RuntimeException("请求错误" + "状态码:" + responseCode);
        }
    }

    public static Result PostRequest(String method, List<Param> params) throws Exception {
        URL url = new URL(URL + method);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(TIMEOUT_IN_MILLIONS);
        int responseCode = connection.getResponseCode();
        String data = getParams(params);
        LogUtils.debug(data);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", String.valueOf(data.length()));
        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data.getBytes());
        if (responseCode == 200) {
            InputStream inputStream = connection.getInputStream();

            String input;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            while ((input = reader.readLine()) != null) {
                stringBuffer.append(input);
            }
            LogUtils.debug(stringBuffer.toString());
            return JSONObject.parseObject(stringBuffer.toString(),Result.class);
        }else {
            throw new RuntimeException("请求错误" + "状态码:" + responseCode);
        }
    }


    public static String getParams(List<Param> params){
        StringBuffer data = new StringBuffer();
        for (Param param:params){
            data.append(param.getParam() + "&");
        }
        data.delete(data.lastIndexOf("&"),data.length());
        return data.toString();
    }
}
