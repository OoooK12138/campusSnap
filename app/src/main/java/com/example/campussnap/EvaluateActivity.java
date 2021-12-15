package com.example.campussnap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.campussnap.bean.CommentBean;
import com.example.campussnap.common.AppContext;
import com.example.campussnap.common.Result;
import com.example.campussnap.utils.HttpUtils;
import com.example.campussnap.utils.LogUtils;

//发表评价界面
public class EvaluateActivity extends Activity implements View.OnClickListener{

    private ImageView back_btn;
    private Button comment_btn;
    private EditText comment_input;
    private RatingBar result_ratingBar;
    private RatingBar speed_ratingBar;

    private Integer feedBackId;

    private CommentBean postData = new CommentBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_evaluate);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.evaluate_title_bar);

        init();
    }

    // 初始化控件
    private void init() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        feedBackId = bundle.getInt("id");
        postData.setFeedBackId(feedBackId);

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(this);
        comment_btn = findViewById(R.id.comment_btn);
        comment_btn.setOnClickListener(this);
        comment_input = findViewById(R.id.comment_input);
        comment_input.setOnClickListener(this);
        result_ratingBar = findViewById(R.id.result_ratingBar);
        result_ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                postData.setRatingResult((int)rating);
            }
        });
        speed_ratingBar = findViewById(R.id.speed_ratingBar);
        speed_ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                postData.setRatingSpeed((int)rating);
            }
        });


    }

    // 发表评论
    public void postComment(){
        String comment = comment_input.getText().toString();
        if(comment.equals("")){
            Toast.makeText(EvaluateActivity.this, "评价内容不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        postData.setCommend(comment);

        Result result = new Result();
        try {
            result = HttpUtils.PostRequest("/feedback/evaluate" , JSONObject.toJSONString(postData));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.debug(result.toString());
        if (result.isSuccess()){
            AppContext.makeToast("评价成功");
        }else {
            AppContext.makeToast("评价失败");
        }
    }

    @Override
    public void onClick(View view) {
        int position = view.getId();
        switch (position) {
            case R.id.back_btn:         // 返回
                this.finish();
                break;
            case R.id.comment_btn:      // 发表评论
                postComment();
                break;
        }
    }
}