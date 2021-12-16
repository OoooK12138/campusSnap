package com.example.campussnap;

// 登陆后进入的程序界面

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.campussnap.fragment.CameraFragment;
import com.example.campussnap.fragment.HomeFragment;
import com.example.campussnap.fragment.NewsFragment;

import java.util.ArrayList;

//import com.example.campussnap.fragment.HomeFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener{
    ViewPager2 viewPager;

    private LinearLayout llNews, llHome, llCamera;
    private ImageView ivNews, ivHome, ivCamera, ivCurrent;      //ivCurrent用来存储当前选中标签



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.main_title_bar);

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
        ivNews = findViewById(R.id.news_tab_img);
        ivHome = findViewById(R.id.home_tab_img);
        ivCamera = findViewById(R.id.camera_tab_img);
        ivNews.setSelected(true);       //默认新闻标签被选中
        ivCurrent = ivNews;
    };

    private void initPager() {
        viewPager = findViewById(R.id.viewpager);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new NewsFragment());
        fragments.add(new CameraFragment());
        fragments.add(new HomeFragment());
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
            case R.id.camera_tab:
                viewPager.setCurrentItem(1);
            case 1:
                ivCamera.setSelected(true);
                ivCurrent = ivCamera;
                break;
            case R.id.home_tab:
                viewPager.setCurrentItem(2);
            case 2:
                ivHome.setSelected(true);
                ivCurrent = ivHome;
                break;

        }
    }

    // 点击标签改变
    @Override
    public void onClick(View view) {
        changeTab(view.getId());
    }
}