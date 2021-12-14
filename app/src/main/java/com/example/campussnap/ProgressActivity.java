package com.example.campussnap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);


        /**
         * 跳转参数传入
         */
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
//        System.out.println(bundle.get("position"));
    }
}