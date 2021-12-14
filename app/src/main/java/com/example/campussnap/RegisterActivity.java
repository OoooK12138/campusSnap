package com.example.campussnap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campussnap.common.AppContext;
import com.example.campussnap.common.Result;
import com.example.campussnap.utils.HttpUtils;
import com.example.campussnap.utils.LogUtils;

public class RegisterActivity extends AppCompatActivity {
    private EditText account;
    private EditText password;
    private EditText rePassword;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    public void init(){
        account = findViewById(R.id.re_account);
        password = findViewById(R.id.re_password);
        rePassword = findViewById(R.id.re_rePassword);
        button = findViewById(R.id.bt_register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!password.getText().toString().equals(rePassword.getText().toString())){
                    AppContext.makeToast("密码不一致");
                    return;
                }
                Result result = new Result();
                try {
                    result = HttpUtils.GetRequest("/user/save?account=" + account.getText().toString() + "&password=" + password.getText().toString() );
                } catch (Exception e) {
                    e.printStackTrace();
                }
                LogUtils.debug(result.toString());
                if (result.isSuccess()){
                    AppContext.makeToast("注册成功");
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);

                }else {
                    AppContext.makeToast(result.getMessage());
                }

            }
        });
    }

}