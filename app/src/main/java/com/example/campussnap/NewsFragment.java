package com.example.campussnap;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.campussnap.config.ListItem;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private TitleAdapter titleAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<ListItem> list = new ArrayList<>();    // 新闻列表数据

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initList();
        View newsView = inflater.inflate(R.layout.fragment_news, container, false);
        mRecyclerView=newsView.findViewById(R.id.lv_news);

        titleAdapter = new TitleAdapter(getActivity(), list);
        mRecyclerView.setLayoutManager (new LinearLayoutManager(getActivity (),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setItemAnimator (new DefaultItemAnimator());
        mRecyclerView.setAdapter (titleAdapter);
        mRecyclerView.addItemDecoration (new DividerItemDecoration(getActivity (),DividerItemDecoration.VERTICAL));
        return newsView;
    }

    class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.MyHolder> {
        private Context context;
        private ArrayList<ListItem> list = new ArrayList<>();    // 新闻列表数据

        public TitleAdapter(Context context, ArrayList<ListItem> list){
            this.context = context;
            this.list = list;
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
            ListItem item = list.get(position);
            holder.title.setText(item.getTitle());
            holder.content.setText(item.getContent());
            holder.date.setText(item.getDate());
            Glide.with(context).load(item.getImgUrl()).into(holder.imgUrl);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder {
            // 初始化view
            RelativeLayout itemLayout;
            TextView title;
            TextView content;
            TextView date;
            ImageView imgUrl;
            public MyHolder(@NonNull View itemView) {
                super(itemView);
                itemLayout = itemView.findViewById(R.id.news_item_rl);
                title = itemView.findViewById(R.id.news_item_title);
                content = itemView.findViewById(R.id.news_item_content);
                date = itemView.findViewById(R.id.news_item_date);
                imgUrl = itemView.findViewById(R.id.news_item_imgUrl);
            }
        }
    }

    // 初始化新闻列表数据（假数据）
    public void initList() {
        ListItem item = new ListItem("我是标题","我是内容","2021-10-27",R.drawable.fzu4);
        for(int i=0;i<6;i++){
            list.add(item);
        }
    }


}
