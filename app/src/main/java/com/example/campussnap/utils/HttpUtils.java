package com.example.campussnap.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.example.campussnap.common.Param;
import com.example.campussnap.common.Result;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

public class HttpUtils {
    private static final String URL = "http://49.235.134.191:8080";
    private static final int TIMEOUT_IN_MILLIONS = 5000;
    private static final String CHARSET = "utf-8";
    private static final String BOUNDARY = UUID.randomUUID().toString();
    private static final String PREFIX = "--";
    private static final String LINE_END = "\r\n";
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")



    public static Result GetRequest(String method) throws Exception {
        URL url = new URL(URL + method);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy((policy));
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

            return JSONObject.parseObject(stringBuffer.toString(),Result.class);
        }else {
            throw new RuntimeException("请求错误" + "状态码:" + responseCode);
        }
    }

    public static Result PostRequest(String method, List<Param> params) throws Exception {
        URL url = new URL(URL + method);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy((policy));
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

    public static Result uploadFile(File file, String RequestURL) throws Exception{
        String CONTENT_TYPE = "multipart/form-data";
        URL url = new URL(RequestURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
        conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
        conn.setDoInput(true); //允许输入流
        conn.setDoOutput(true); //允许输出流
        conn.setUseCaches(false); //不允许使用缓存
        conn.setRequestMethod("POST"); //请求方式
        conn.setRequestProperty("Charset", CHARSET);
        //设置编码
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
        if (file != null) {
            /** * 当文件不为空，把文件包装并且上传 */
            OutputStream outputSteam = conn.getOutputStream();
            DataOutputStream dos = new DataOutputStream(outputSteam);
            StringBuffer sb = new StringBuffer();
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINE_END);
            /**
             * 这里重点注意：
             * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
             * filename是文件的名字，包含后缀名的 比如:abc.png
             */
            sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"" + LINE_END);
            sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
            sb.append(LINE_END);
            dos.write(sb.toString().getBytes());
            InputStream is = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                dos.write(bytes, 0, len);
            }
            is.close();
            dos.write(LINE_END.getBytes());
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
            dos.write(end_data);
            dos.flush();
            /**
             * 获取响应码 200=成功
             * 当响应成功，获取响应的流
             */
            int res = conn.getResponseCode();
            LogUtils.debug(String.valueOf(res));
            if (res == 200) {
                InputStream inputStream = conn.getInputStream();

                String input;
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                while ((input = reader.readLine()) != null) {
                    stringBuffer.append(input);
                }
                return JSONObject.parseObject(stringBuffer.toString(), Result.class);
            } else {
                throw new RuntimeException("请求错误" + "状态码:" + res);
            }
        }
        throw new RuntimeException("文件不存在");
    }

    public static String getParams(List<Param> params){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy((policy));
        StringBuffer data = new StringBuffer();
        for (Param param:params){
            data.append(param.getParam() + "&");
        }
        data.delete(data.lastIndexOf("&"),data.length());
        return data.toString();
    }
}