package com.example.zhuoh.zheng_map_ble_z;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import com.squareup.picasso.Picasso;

/**
 * Created by zhuo on 2017/7/24.
 */

public class MyAdapterWifi extends BaseAdapter {
    LayoutInflater inflater;
    List<ScanResult> list;
    private Context mcontext;
    private boolean isWifiConnected;
    public MyAdapterWifi(Context context, List<ScanResult> list) {
        // TODO Auto-generated constructor stub
        mcontext = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.isWifiConnected = false;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = null;
        isWifiConnected = false;
        ScanResult ap = list.get(position);
        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.wifi_list, null);
            viewHolder.rssi = ((ImageView) convertView.findViewById(R.id.wifiap_item_iv_rssi));
            viewHolder.ssid = ((TextView) convertView.findViewById(R.id.wifiap_item_tv_ssid));
            viewHolder.desc = ((TextView) convertView.findViewById(R.id.wifiap_item_tv_desc));
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NetworkInfo mNetworkInfo = ((ConnectivityManager) mcontext
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        WifiManager wifiManager = (WifiManager) mcontext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo mWifiInfo = wifiManager.getConnectionInfo();
        String BSSID = mWifiInfo.getBSSID();
        Boolean isWifiConnect = mNetworkInfo.isConnected();
        if (isWifiConnect && ap.BSSID.equals(BSSID)) {
            isWifiConnected = true;
        }
        viewHolder.ssid.setText(ap.SSID);
        viewHolder.desc.setText(getDesc(ap));
        Picasso.with(mcontext).load(getRssiImgId(ap)).into(viewHolder.rssi);
        return convertView;
    }
    public static class ViewHolder {
        public ImageView rssi;
        public TextView ssid;
        public TextView desc;
    }
    private String getDesc(ScanResult ap) {
        String desc = "";
        if (ap.SSID.startsWith(WifiApConst.WIFI_AP_HEADER)) {
            desc = "专用网络，可以直接连接";
        }
        else {
            String descOri = ap.capabilities;
            if (descOri.toUpperCase().contains("WPA-PSK")
                    || descOri.toUpperCase().contains("WPA2-PSK")) {
                desc = "受到密码保护";
            }
            else {
                desc = "未受保护的网络";
            }
        }
        return desc;
    }
    private int getRssiImgId(ScanResult ap) {
        int imgId;
        if (isWifiConnected) {
            imgId = R.drawable.ic_connected;
        }
        else {
            int rssi = Math.abs(ap.level);
            if (rssi > 100) {
                imgId = R.drawable.ic_small_wifi_rssi_0;
            }
            else if (rssi > 80) {
                imgId = R.drawable.ic_small_wifi_rssi_1;
            }
            else if (rssi > 70) {
                imgId = R.drawable.ic_small_wifi_rssi_2;
            }
            else if (rssi > 60) {
                imgId = R.drawable.ic_small_wifi_rssi_3;
            }
            else {
                imgId = R.drawable.ic_small_wifi_rssi_4;
            }
        }
        return imgId;
    }
}
