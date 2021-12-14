package com.example.campussnap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.campussnap.entity.User;
import com.example.campussnap.utils.AuthUtils;

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

        boolean isAuto = AuthUtils.getIsAuto(this);

        if (isAuto){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }

        User userInfo = AuthUtils.getUserInfo(this);
        if (userInfo!=null) {
            etAccount.setText(userInfo.getUsername());
            etPassword.setText(userInfo.getPassword());
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
                if (rememberPwdBox.isChecked()){
                    AuthUtils.setAccount(LoginActivity.this,etAccount.getText().toString(),etPassword.getText().toString());
                }

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
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
}