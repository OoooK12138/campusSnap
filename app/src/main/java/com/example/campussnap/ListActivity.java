package com.example.campussnap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * 校园新闻列表
 */
public class ListActivity extends AppCompatActivity{

    private String[] titles={"震惊！今日校园竟...","震惊！某大一学生竟做出这种行为...","震惊！谁也想不到他居然做出这种事...","震惊！还有什么比这更吓人的...","震惊！校园内竟出现令人不齿的事...",
    "震惊！震惊！震惊！"};

    private String[] contents={"欲知详情，请前往下一条新闻查看！","欲知详情，请前往下一条新闻查看！","欲知详情，请前往下一条新闻查看！","欲知详情，请前往下一条新闻查看！","欲知详情，请前往下一条新闻查看！",
            "很遗憾，新闻内容丢失了！"};

    private String[] dates={"2021-10-14","2021-10-14","2021-10-14","2021-10-14","2021-10-14","2021-10-14"};

    private int[] images={R.drawable.fzu4,R.drawable.fzu5,R.drawable.fzu6,R.drawable.fzu7,R.drawable.fzu8,R.drawable.fzu9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ListView listView=(ListView)findViewById(R.id.lv);
        MyBaseAdapter mAdapter=new MyBaseAdapter();
        listView.setAdapter(mAdapter);
    }

    class MyBaseAdapter extends BaseAdapter{
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

        public View getView(int position, View convertView, ViewGroup parent){
            View view = View.inflate(ListActivity.this,R.layout.list_item,null);
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
