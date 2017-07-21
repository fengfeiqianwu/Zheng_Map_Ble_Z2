package com.example.zhuoh.zheng_map_ble_z;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zhuo on 2017/7/21.
 */

public class PageFragment extends Fragment {
    public static final String ARGS_PAGE = "args_page";
    private int mPage;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();

        args.putInt(ARGS_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARGS_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        switch (mPage){
            case 1:
                View view = inflater.inflate(R.layout.fragment_page1,container,false);
                return view;
            case 2:
                View view2 = inflater.inflate(R.layout.fragment_page2,container,false);
                return view2;
        }
        /*View view = inflater.inflate(R.layout.fragment_page,container,false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText("第"+mPage+"页");
        return view;*/
        View view = inflater.inflate(R.layout.fragment_page1,container,false);
        return view;
    }
}
