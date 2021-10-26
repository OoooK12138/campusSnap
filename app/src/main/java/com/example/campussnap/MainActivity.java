package com.example.campussnap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ViewPager2 viewPager;

    private LinearLayout llNews, llHome, llCamera, llUser;
    private ImageView ivNews, ivHome, ivCamera, ivUser, ivCurrent;      //ivCurrent用来存储当前选中标签


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPager();
        initTabView();
    }

    // 初始化导航栏及状态
    private void initTabView() {
        llNews = findViewById(R.id.news_tab);
        llNews.setOnClickListener(this);
        llHome = findViewById(R.id.home_tab);
        llHome.setOnClickListener(this);
        llCamera = findViewById(R.id.camera_tab);
        llCamera.setOnClickListener(this);
        llUser = findViewById(R.id.user_tab);
        llUser.setOnClickListener(this);
        ivNews = findViewById(R.id.news_tab_img);
        ivHome = findViewById(R.id.home_tab_img);
        ivCamera = findViewById(R.id.camera_tab_img);
        ivUser = findViewById(R.id.user_tab_img);

        ivNews.setSelected(true);       //默认新闻标签被选中
        ivCurrent = ivNews;
    };

    private void initPager() {
        viewPager = findViewById(R.id.viewpager);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(BlankFragment.newInstance("新闻"));
        fragments.add(BlankFragment.newInstance("主页"));
        fragments.add(BlankFragment.newInstance("随手拍"));
        fragments.add(BlankFragment.newInstance("我的"));
        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            // 滑动标签改变
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    // 标签切换
    private void changeTab(int position) {
        ivCurrent.setSelected(false);
        switch (position) {
            case R.id.news_tab:
                viewPager.setCurrentItem(0);
            case 0:
                ivNews.setSelected(true);
                ivCurrent = ivNews;
                break;
            case R.id.home_tab:
                viewPager.setCurrentItem(1);
            case 1:
                ivHome.setSelected(true);
                ivCurrent = ivHome;
                break;
            case R.id.camera_tab:
                viewPager.setCurrentItem(2);
            case 2:
                ivCamera.setSelected(true);
                ivCurrent = ivCamera;
                break;
            case R.id.user_tab:
                viewPager.setCurrentItem(3);
            case 3:
                ivUser.setSelected(true);
                ivCurrent = ivUser;
                break;
        }
    }

    // 点击标签改变
    @Override
    public void onClick(View view) {
        changeTab(view.getId());
    }
}