package com.example.campussnap.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.campussnap.R;
import com.example.campussnap.bean.UserBean;

public class HomeFragment extends Fragment {

    private TextView username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        username = (TextView) view.findViewById(R.id.username);
        String name = UserBean.getUsername();
        Log.v("mybug","用户界面：" + name);
        username.setText(name);
        return view;
    }
}
