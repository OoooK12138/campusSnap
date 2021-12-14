package com.example.campussnap.fragment;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//
//public class HomeFragment extends Fragment implements View.OnClickListener{
//
//    private ImageView lCamera,lHistory;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_home, null);
//        initView(view);
//        return view;
//    }
//
//    private void initView(View view){
//        lCamera=view.findViewById(R.id.camera);
//        lCamera.setOnClickListener(this);
//        lHistory=view.findViewById(R.id.history);
//        lHistory.setOnClickListener(this);
//    }
//
//    /**
//     * 鎷嶇収鍙婂巻鍙茶褰曠殑鐐瑰嚮
//     * @param view
//     */
//
//    public void onClick(View view){
//        switch (view.getId())
//        {
//            /**
//             * 璺宠浆澶辫触锛�
//             */
//            case R.id.camera:
//                FragmentManager fm= getActivity().getSupportFragmentManager();
//                fm.beginTransaction()
//                        .replace(R.id.camera_tab, new CameraFragment(), null)
//                        .addToBackStack(null)
//                        .commit();
//                break;
//            case R.id.history:
//                Intent intent1 = new Intent(getActivity(),HistoryActivity.class);
//                startActivity(intent1);
//                break;
//        }
//    }
//
//}
