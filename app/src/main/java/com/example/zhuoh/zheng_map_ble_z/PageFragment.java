package com.example.zhuoh.zheng_map_ble_z;


import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by zhuo on 2017/7/21.
 */

public class PageFragment extends Fragment {
    public static final String ARGS_PAGE = "args_page";
    private int mPage;
    private WifiManager wifiManager;
    List<ScanResult> list;

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
            case 10:
                View view10 = inflater.inflate(R.layout.fragment_pagewifi,container,false);
                wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if (!wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(true);
                }
                list = wifiManager.getScanResults();
                Log.i("____list",""+list.size());
                ListView listView = (ListView)view10.findViewById(R.id.wifilist);
                if (list == null) {
                }else {
                    listView.setAdapter(new MyAdapterWifi(this.getActivity(),list));
                }
                return view10;
        }
        /*View view = inflater.inflate(R.layout.fragment_page,container,false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText("第"+mPage+"页");
        return view;*/
        View view = inflater.inflate(R.layout.fragment_page1,container,false);
        return view;
    }
}
