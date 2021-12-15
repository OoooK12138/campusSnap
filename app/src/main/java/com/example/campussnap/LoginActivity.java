package com.example.campussnap;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.example.campussnap.bean.FeedBackBean;
import com.example.campussnap.bean.HistoryItem;
import com.example.campussnap.common.AppContext;
import com.example.campussnap.common.Result;
import com.example.campussnap.entity.User;
import com.example.campussnap.utils.AuthUtils;
import com.example.campussnap.utils.HttpUtils;
import com.example.campussnap.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {
    private EditText etAccount;
    private EditText etPassword;
    private Button btnLogin;
    private TextView register;
    private CheckBox autoLoginBox;
    private CheckBox rememberPwdBox;

    private String account;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        initView();

        User userInfo = AuthUtils.getUserInfo(this);
        if (userInfo!=null) {
            etAccount.setText(userInfo.getUsername());
            etPassword.setText(userInfo.getPassword());

            boolean isAuto = AuthUtils.getIsAuto(this);

            if (isAuto){
                Result result = new Result();
                try {
                    result = userLogin(userInfo);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (result.isSuccess()) {
                    AppContext.getInstance().userLogin(userInfo);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    return;
                }
                AppContext.makeToast("登入失败");
            }
        }

    }

    private void initView(){
        etAccount = findViewById(R.id.account);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.bt_login);
        register = findViewById(R.id.register);
        autoLoginBox = findViewById(R.id.loginBox);
        rememberPwdBox = findViewById(R.id.pwdBox);


        /**
         * 点击登录
         */

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //判断账号密码不能为空
                if (etAccount.getText().toString().equals("")||etPassword.getText().toString().equals("")){
                    AppContext.makeToast("账号密码不能为空");
                    return;
                }

                Result result = new Result();
                User user = new User(etAccount.getText().toString(),etPassword.getText().toString());
                try {
                    result = userLogin(user);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (result.isSuccess()){
                    AppContext.makeToast("登入成功");
                    AppContext.getInstance().userLogin(user);
                    if (rememberPwdBox.isChecked()){
                        AuthUtils.setAccount(LoginActivity.this,etAccount.getText().toString(),etPassword.getText().toString());
                    }

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    return;
                }
                AppContext.makeToast("登入失败");
            }
        });

        /**
         * 点击注册
         */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        /*
            自动登录部分
         */
        boolean isAuto = AuthUtils.getIsAuto(this);
        autoLoginBox.setChecked(isAuto);
        autoLoginBox.setText("自动登录");

        autoLoginBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AuthUtils.setAuto(LoginActivity.this,b);
            }
        });

        /*
        记住密码部分
         */
        boolean isRemember = AuthUtils.getIsRemember(this);
        rememberPwdBox.setText("记住密码");
        rememberPwdBox.setChecked(isRemember);

    }

    public Result userLogin(User user) throws Exception{

        return HttpUtils.GetRequest("/user/login?" + "account=" + URLEncoder.encode(user.getUsername()) + "&password=" + URLEncoder.encode(user.getPassword()));

    }
}