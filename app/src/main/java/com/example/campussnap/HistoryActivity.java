package com.example.campussnap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.example.campussnap.bean.HistoryItem;
import com.example.campussnap.common.AppContext;
import com.example.campussnap.common.Result;
import com.example.campussnap.utils.HttpUtils;
import com.example.campussnap.utils.LogUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

public class HistoryActivity extends Activity {

    private RecyclerView hRecyclerView;
    private HistoryAdapter historyAdapter;
    private ArrayList<HistoryItem> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        initList();
        setContentView(R.layout.activity_history);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.main_title_bar);
        hRecyclerView=findViewById(R.id.rv_history);

        historyAdapter = new HistoryAdapter(HistoryActivity.this, list);
        hRecyclerView.setLayoutManager (new LinearLayoutManager(HistoryActivity.this,LinearLayoutManager.VERTICAL,false));
        hRecyclerView.setItemAnimator (new DefaultItemAnimator());
        hRecyclerView.setAdapter (historyAdapter);
        hRecyclerView.addItemDecoration (new DividerItemDecoration(HistoryActivity.this,DividerItemDecoration.VERTICAL));

        historyAdapter.buttonSetOnclick(new HistoryAdapter.ButtonInterface(){


            private int position;

            /**
             * flag=1为查看进程按钮,flag=2为我要评价按钮
             * 传递item位置
             * @param view
             * @param position  item位置
             * @param flag
             */
            public void onclick(View view, int position,int flag){
                this.position=position;
                if(flag==1){
//                    System.out.println("进度");
                    Intent intent= new Intent(HistoryActivity.this,ProgressActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("pos", list.get(position).getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else{
//                   System.out.println("评价");
                    Intent intent1 = new Intent(HistoryActivity.this,EvaluateActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", list.get(position).getId());
                    intent1.putExtras(bundle);
                    startActivity(intent1);
                }
            }
        });

    }

    static class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

        private Context context;
        private ArrayList<HistoryItem> list = new ArrayList<>();
        private ButtonInterface buttonInterface;
        private DateFormat format = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINA);

        public HistoryAdapter(Context context, ArrayList<HistoryItem> list) {
            this.context = context;
            this.list = list;
        }

        public void buttonSetOnclick(ButtonInterface buttonInterface){
            this.buttonInterface=buttonInterface;
        }

        public interface ButtonInterface{
            public void onclick(View view,int position,int flag);
        }

        public int getItemCount() {
            return list.size();
        }

        @NonNull
        @Override
        /**
         * 创建view
         */
        public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(context).inflate(R.layout.history_item,parent,false);
            return new HistoryAdapter.ViewHolder(view) ;
        }

        @Override
        /**
         * 绑定数据
         */
        public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
            HistoryItem item = list.get(position);
            holder.category.setText(item.getCategory());
            holder.process.setText(item.getProcess());
            holder.title.setText(item.getTitle());
            holder.content.setText(item.getDesc());
            holder.date.setText(format.format(list.get(position).getTime()));
            if (list.get(position).getImageUrl()!=null) {
                setImage(holder, position, list.get(position).getImageUrl());
            }

            /**
             * 进程点击事件 flag=1
             */
            holder.progress.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    if(buttonInterface!=null){
                        buttonInterface.onclick(v,position,1);
                    }
                }
            });

            /**
             * 评价点击事件 falg=2
             */
            holder.evaluate.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    if(buttonInterface!=null){
                        buttonInterface.onclick(v,position,2);
                    }
                }
            });
        }


        class ViewHolder extends RecyclerView.ViewHolder{

            private TextView title;
            private TextView content;
            private TextView date;
            private ImageView imgUrl;
            private Button progress;
            private Button evaluate;
            private TextView category;
            private TextView process;
            RelativeLayout itemLayout;

            public ViewHolder(View itemView){
                super(itemView);
                itemLayout = itemView.findViewById(R.id.history_item_rl);
                category=itemView.findViewById(R.id.history_item_category);
                process=itemView.findViewById(R.id.history_item_process);
                title = itemView.findViewById(R.id.history_item_title);
                content = itemView.findViewById(R.id.history_item_content);
                date = itemView.findViewById(R.id.history_item_date);
                progress=itemView.findViewById(R.id.history_item_progress);
                evaluate=itemView.findViewById(R.id.history_item_evaluate);
                imgUrl = itemView.findViewById(R.id.history_item_imgUrl);

            }
        }

    }



    private void initList() {
        Result result = new Result();
        try {
            result = HttpUtils.GetRequest("/feedback/get?" + "account=" + URLEncoder.encode(AppContext.getInstance().getUsername()));
        }catch (Exception e){
            e.printStackTrace();
        }
        LogUtils.debug(result.toString());
        if (result.getData()!=null) {
            list = (ArrayList<HistoryItem>) JSONArray.parseArray(result.getData().toString(), HistoryItem.class);
            list.sort(new Comparator<HistoryItem>() {
                @Override
                public int compare(HistoryItem historyItem, HistoryItem t1) {
                    if (historyItem.getTime().before(t1.getTime())){
                        return 1;
                    }
                    return -1;
                }
            });
            LogUtils.debug(list.toString());
        }
    }

    public static void  setImage(HistoryActivity.HistoryAdapter.ViewHolder holder, int pos,String imageUrl){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(imageUrl);
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