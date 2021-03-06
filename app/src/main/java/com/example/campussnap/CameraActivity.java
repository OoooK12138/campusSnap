package com.example.campussnap;


import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.alibaba.fastjson.JSONObject;
import com.example.campussnap.bean.HistoryItem;
import com.example.campussnap.common.AppContext;
import com.example.campussnap.common.Result;
import com.example.campussnap.utils.HttpUtils;
import com.example.campussnap.utils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

public class CameraActivity extends Activity {

    private ImageView photoView;
    private Button upLoadBtn;
    private Uri imageURL;

    private String mFilePath;
    private TextView locationInfo;
    private EditText ed_title;
    private EditText ed_problem;
    private RadioGroup category;
    private RadioGroup degree;

    private ImageView returnBtn;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_camera);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.main_title_bar);
        init();
    }

    public void init(){

        upLoadBtn = findViewById(R.id.up_load);
        photoView = findViewById(R.id.photo_item);
        ed_title = findViewById(R.id.title_text);
        ed_problem = findViewById(R.id.problem_text);
        category = findViewById(R.id.problem_description);
        degree = findViewById(R.id.importance_group);
        returnBtn=findViewById(R.id.turn_back);


        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;


                mFilePath=getExternalFilesDir(null) + "/" + "mytest.png";
                File cameraPhoto = new File(mFilePath);

                Uri photoUri = FileProvider.getUriForFile(
                        AppContext.getInstance(),
                        getPackageName() + ".fileprovider",
                        cameraPhoto);

                /*takePhotoBiggerThan7((new File(mFilePath)).getAbsolutePath());*/
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);;
                //MediaStore.ACTION_IMAGE_CAPTURE  ????????????????????????
                intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(intent, 0x3);
            }
        });

        locationInfo=findViewById(R.id.locationInfo);
        locationInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(CameraActivity.this,LocationActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivityForResult(intent,888);
            }
        });
        upLoadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(mFilePath);
                if (!file.exists()){
                    AppContext.makeToast("???????????????");
                    return;
                }
                Result image = new Result();
                try {
                    image = HttpUtils.uploadFile(file,"/file/image/upload");
                }catch (Exception e){
                    e.printStackTrace();
                }
                LogUtils.debug(image.toString());
                if (!image.isSuccess()){
                    AppContext.makeToast("??????????????????");
                    return;
                }

                HistoryItem historyItem = new HistoryItem();
                historyItem.setId(0);
                historyItem.setImageUrl(image.getData().toString());
                historyItem.setTitle(ed_title.getText().toString());
                historyItem.setDesc(ed_problem.getText().toString());
                historyItem.setAddress(locationInfo.getText().toString());
                historyItem.setAccount(AppContext.getInstance().getUsername());
                RadioButton bt_category = (RadioButton)findViewById(category.getCheckedRadioButtonId());
                historyItem.setCategory(bt_category.getText().toString());
                RadioButton bt_degree = (RadioButton)findViewById(degree.getCheckedRadioButtonId());

                if (bt_degree.getText().toString().equals("??????")){
                    historyItem.setDegree(0);
                }else {
                    historyItem.setDegree(1);
                }
                historyItem.setTime(new Date());
                historyItem.setProcess("?????????");
                LogUtils.debug(historyItem.toString());

                Result result = new Result();
                try {
                    result = HttpUtils.PostRequest("/feedback/save", JSONObject.toJSONString(historyItem));
                }catch (Exception e){
                    e.printStackTrace();
                }
                LogUtils.debug(result.toString());
                if (result.isSuccess()){
                    AppContext.makeToast("????????????");
                }else {
                    AppContext.makeToast("????????????");
                }


            }
        });

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }








    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            // ????????????????????????
                // ????????????????????????
            mFilePath=getExternalFilesDir(null) + "/" + "mytest.png";
            File file = new File(mFilePath);
            if (file.exists()) {
                LogUtils.debug("exits");
                try {
                    FileInputStream inputStream = new FileInputStream(file);
                    photoView.setImageBitmap(BitmapFactory.decodeStream(inputStream));
                 } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            LogUtils.debug("unexits");
        }
        if (requestCode==888){
            Bundle bundle = data.getExtras();
            String str =bundle.getString("aaa");
//           Log.v("aaaaa",str);
            locationInfo.setText(str);
        }
    }







}
