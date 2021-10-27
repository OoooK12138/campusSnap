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
        ListItem item = new ListItem("我是标题","我是内容","2021-10-27","https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E6%9F%AF%E5%8D%97&step_word=&hs=0&pn=3&spn=0&di=211310&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=1434599512%2C870563745&os=1101811868%2C2435391867&simid=3394542561%2C137283769&adpicid=0&lpn=0&ln=1705&fr=&fmq=1635341270826_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=https%3A%2F%2Fgimg2.baidu.com%2Fimage_search%2Fsrc%3Dhttp%3A%2F%2Fqqpublic.qpic.cn%2Fqq_public%2F0%2F0-2946385363-66A996185E2845B55C43B1066027D837%2F0%3Ffmt%3Djpg%26size%3D87%26h%3D765%26w%3D777%26ppv%3D1.jpg%26refer%3Dhttp%3A%2F%2Fqqpublic.qpic.cn%26app%3D2002%26size%3Df9999%2C10000%26q%3Da80%26n%3D0%26g%3D0n%26fmt%3Djpeg%3Fsec%3D1637933278%26t%3D8e1661a0b8275a6ccd740edb3631c835&fromurl=ippr_z2C%24qAzdH3FAzdH3Fh7wtkw5_z%26e3Bqq_z%26e3Bv54AzdH3FfAzdH3Fda8l888cwatfabaa%3F6juj6%3Dfrt1j6&gsm=2&rpstart=0&rpnum=0&islist=&querylist=&nojc=undefined&dyTabStr=MCwzLDEsNiw1LDQsNywyLDgsOQ%3D%3D");
        for(int i=0;i<6;i++){
            list.add(item);
        }
    }


}
