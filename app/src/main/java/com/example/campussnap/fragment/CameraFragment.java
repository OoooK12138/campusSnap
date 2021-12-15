package com.example.campussnap.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.campussnap.CameraActivity;
import com.example.campussnap.HistoryActivity;
import com.example.campussnap.R;

public class CameraFragment extends Fragment implements View.OnClickListener{

    private ImageView lCamera,lHistory;
    private ImageView photoView;
    private Button upLoadBtn;
    private Uri imageURL;
    private String imageUrl;


    private String photoPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        initView(view);
        return view;
    }

    private void initView(View view){
        lCamera=view.findViewById(R.id.camera);
        lCamera.setOnClickListener(this);
        lHistory=view.findViewById(R.id.history);
        lHistory.setOnClickListener(this);
    }

    /**
     * 点击图片进入历史记录和随手拍
     * @param view
     */

    public void onClick(View view){
        switch (view.getId())
        {
            /**
             * 跳转失败
             */
            case R.id.camera:
                Intent intent0 = new Intent(getActivity(), CameraActivity.class);
                startActivity(intent0);
                break;
            case R.id.history:
                Intent intent1 = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent1);
                break;
        }
    }

}
