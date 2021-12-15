package com.example.campussnap;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class CameraActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView photoView;
    private Button upLoadBtn;
    private Uri imageURL;

    private String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.evaluate_title_bar);

        initView();
    }


    private void initView(){
        upLoadBtn = findViewById(R.id.up_load);
        photoView = findViewById(R.id.photo_item);
        photoView.setOnClickListener(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                imageURL = data.getData();
                photoView.setImageURI(imageURL);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.photo_item:
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 2);
                break;
        }
    }
}