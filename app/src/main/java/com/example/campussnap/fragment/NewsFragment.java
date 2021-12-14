package com.example.campussnap.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.example.campussnap.R;
import com.example.campussnap.bean.NewsBean;
import com.example.campussnap.common.AppContext;
import com.example.campussnap.common.Result;
import com.example.campussnap.utils.HttpUtils;
import com.example.campussnap.utils.LogUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NewsFragment extends Fragment {

    private TitleAdapter titleAdapter;
    private RecyclerView mRecyclerView;// 新闻列表数据
    private List<NewsBean> newsBeanList = new ArrayList<>();
    private DateFormat dateFormat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View newsView = inflater.inflate(R.layout.fragment_news, container, false);
        mRecyclerView=newsView.findViewById(R.id.lv_news);

        titleAdapter = new TitleAdapter(AppContext.getInstance());
        mRecyclerView.setLayoutManager (new LinearLayoutManager(AppContext.getInstance(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter (titleAdapter);

//        mRecyclerView.setItemAnimator (new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration (new DividerItemDecoration(getActivity (),DividerItemDecoration.VERTICAL));
        init();
        return newsView;
    }

    public void init(){
        dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);
        initList();

    }

    class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.MyHolder> {
        private Context context;// 新闻列表数据

        public TitleAdapter(Context context){
            this.context = context;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // 这里传入对应的item
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            LogUtils.debug(String.valueOf(position));
            holder.title.setText(newsBeanList.get(position).getTitle());
            holder.content.setText(newsBeanList.get(position).getDesc());
            holder.date.setText(dateFormat.format(newsBeanList.get(position).getPublishTime()));
            setImage(holder,position);
//            try {
//                URL url = new URL(newsBeanList.get(position).getImageUrl());
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                //使用GET方法访问网络
//                connection.setRequestMethod("GET");
//                //超时时间为10秒
//                connection.setConnectTimeout(5000);
//
//                int code = connection.getResponseCode();
//                if (code == 200) {
//                    InputStream inputStream = connection.getInputStream();
//                    //使用工厂把网络的输入流生产Bitmap
//                    BitmapFactory.Options options=new BitmapFactory.Options();
//                    options.inSampleSize = 10;
//                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,options);
//                    holder.imgUrl.setImageBitmap(bitmap);
//                    inputStream.close();
//                }else {
//                    throw new RuntimeException("获取失败");
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }

//            Glide.with(context).load(item.getImgUrl()).into(holder.imgUrl);
        }

        @Override
        public int getItemCount() {
            return newsBeanList.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder {
            // 初始化view
            TextView title;
            TextView content;
            TextView date;
            ImageView imgUrl;
            public MyHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.news_item_title);
                content = itemView.findViewById(R.id.news_item_content);
                date = itemView.findViewById(R.id.news_item_date);
                imgUrl = itemView.findViewById(R.id.news_item_imgUrl);
            }
        }
    }

    // 初始化新闻列表数据（假数据）
    public void initList() {
        Result result = new Result();
        try {
            result = HttpUtils.GetRequest("/news/get");
        }catch (Exception e){
            e.printStackTrace();
        }
        newsBeanList = JSONArray.parseArray(result.getData().toString(), NewsBean.class);
    }


    public void  setImage(TitleAdapter.MyHolder holder, int pos){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(newsBeanList.get(pos).getImageUrl());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //使用GET方法访问网络
                    connection.setRequestMethod("GET");
                    //超时时间为10秒
                    connection.setConnectTimeout(5000);

                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        //使用工厂把网络的输入流生产Bitmap
                        BitmapFactory.Options options=new BitmapFactory.Options();
                        options.inSampleSize = 4;
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,options);
                        holder.imgUrl.setImageBitmap(bitmap);
                        inputStream.close();
                    }else {
                        throw new RuntimeException("获取失败");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }
}
