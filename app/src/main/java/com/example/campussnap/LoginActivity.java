package com.example.campussnap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private EditText etAccount;
    private EditText etPassword;
    private Button btnLogin;
    private TextView register;

    private String account;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView(){
        etAccount = findViewById(R.id.account);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.bt_login);
        register = findViewById(R.id.register);

        /**
         * 点击登录
         */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ListActivity.class);
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
    }
}