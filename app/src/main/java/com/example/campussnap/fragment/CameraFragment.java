package com.example.campussnap.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import androidx.fragment.app.Fragment;

import com.example.campussnap.R;


public class CameraFragment extends Fragment implements View.OnClickListener {

    private ImageView photoView;
    private Button upLoadBtn;

    private String photoPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, null);

        initView(view);
        return view;
    }
    private void initView(View view){
        upLoadBtn = (Button) view.findViewById(R.id.up_load);
        photoView = (ImageView) view.findViewById(R.id.photo_item);
        photoView.setOnClickListener(this);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                photoView.setImageURI(uri);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.photo_item:
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 2);
                break;
        }
    }
}
