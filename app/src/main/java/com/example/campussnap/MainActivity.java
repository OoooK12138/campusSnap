package com.example.campussnap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campussnap.entity.User;
import com.example.campussnap.utils.AuthUtils;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean remember = AuthUtils.getIsRemember(this);

        if (remember){
            Intent intent = new Intent(MainActivity.this,ListActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);
    }
}