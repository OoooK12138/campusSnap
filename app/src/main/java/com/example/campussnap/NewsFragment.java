package com.example.campussnap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class NewsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_news, null);
        ListView listView=(ListView)view.findViewById(R.id.lv);
        MyBaseAdapter mAdapter=new MyBaseAdapter();
        listView.setAdapter(mAdapter);
        return view;
    }

    private String[] titles={"震惊！今日校园竟...","震惊！某大一学生竟做出这种行为...","震惊！谁也想不到他居然做出这种事...","震惊！还有什么比这更吓人的...","震惊！校园内竟出现令人不齿的事...",
            "震惊！震惊！震惊！"};

    private String[] contents={"欲知详情，请前往下一条新闻查看！","欲知详情，请前往下一条新闻查看！","欲知详情，请前往下一条新闻查看！","欲知详情，请前往下一条新闻查看！","欲知详情，请前往下一条新闻查看！",
            "很遗憾，新闻内容丢失了！"};

    private String[] dates={"2021-10-14","2021-10-14","2021-10-14","2021-10-14","2021-10-14","2021-10-14"};

    private int[] images={R.drawable.fzu4,R.drawable.fzu5,R.drawable.fzu6,R.drawable.fzu7,R.drawable.fzu8,R.drawable.fzu9};

    class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return titles.length;
        }

        public Object getItem(int position){
            return titles[position];
        }

        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }

        public View getView(int position, LayoutInflater inflater, ViewGroup parent){
            View view = inflater.inflate(R.layout.list_item,parent, false);
            TextView title=(TextView) view.findViewById(R.id.title);
            TextView content=(TextView) view.findViewById(R.id.content);
            TextView date=(TextView) view.findViewById(R.id.date);
            ImageView iv=(ImageView) view.findViewById(R.id.iv);
            title.setText(titles[position]);
            content.setText(contents[position]);
            date.setText(dates[position]);
            iv.setBackgroundResource(images[position]);
            return view;
        }
    }
}
