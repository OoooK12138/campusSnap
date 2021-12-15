package com.example.campussnap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.campussnap.bean.HistoryItem;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView hRecyclerView;
    private HistoryAdapter historyAdapter;
    private ArrayList<HistoryItem> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initList();
        setContentView(R.layout.activity_history);
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
                    bundle.putString("position", String.valueOf(this.position));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else{
//                   System.out.println("评价");
                    Intent intent1 = new Intent(HistoryActivity.this,EvaluateActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("position", String.valueOf(this.position));
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
        public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
            HistoryItem item = list.get(position);
            holder.title.setText(item.getTitle());
            holder.content.setText(item.getContent());
            holder.date.setText(item.getDate());
            Glide.with(context).load(item.getImgUrl()).into(holder.imgUrl);

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
        HistoryItem item = new HistoryItem("我是标题","我是内容","2021-10-27",R.drawable.fzu5,1,"安全隐患","处理完成");
        for(int i=0;i<6;i++){
            list.add(item);
        }
    }


}