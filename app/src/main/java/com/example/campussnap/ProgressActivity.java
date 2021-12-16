package com.example.campussnap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.example.campussnap.bean.FeedBackBean;
import com.example.campussnap.common.AppContext;
import com.example.campussnap.common.Result;
import com.example.campussnap.utils.HttpUtils;
import com.example.campussnap.utils.LogUtils;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProgressActivity extends Activity {

    private List<FeedBackBean> feedBackBeanList = new ArrayList<>();
    private Integer feedBackId ;
    private DateFormat dateFormat;
    private RecyclerView recyclerView;

    private ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_progress);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.progress_title_bar);
        /**
         * 璺宠浆鍙傛暟浼犲叆
         */


        init();
    }

    class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.MyViewHolder>{

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trace, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            //设置开始条不显示
            if (position==0){
                holder.startLine.setVisibility(View.INVISIBLE);
            }
            holder.status.setText(feedBackBeanList.get(position).getDesc());
            holder.date.setText(dateFormat.format(feedBackBeanList.get(position).getTime()));
        }


        @Override
        public int getItemCount() {
            return feedBackBeanList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView status;
            private TextView date;
            private TextView startLine;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                startLine = itemView.findViewById(R.id.tvTopLine);
                status = itemView.findViewById(R.id.tvAcceptStation);
                date = itemView.findViewById(R.id.tvAcceptTime);
            }
        }
    }

    public void init(){
        recyclerView = findViewById(R.id.rv_history);
        ProgressAdapter progressAdapter = new ProgressAdapter();
        recyclerView.setLayoutManager (new LinearLayoutManager(AppContext.getInstance(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter (progressAdapter);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        feedBackId = bundle.getInt("pos");
        LogUtils.debug(feedBackId.toString());

        feedBackId = 20;
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINA);
        Result result = new Result();
        try {
            result = HttpUtils.GetRequest("/feedback/process?" + "feed_back_id=" + URLEncoder.encode(feedBackId.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result.isSuccess()){
            feedBackBeanList = JSONArray.parseArray(result.getData().toString(),FeedBackBean.class);
            LogUtils.debug(feedBackBeanList.toString());
        }

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}