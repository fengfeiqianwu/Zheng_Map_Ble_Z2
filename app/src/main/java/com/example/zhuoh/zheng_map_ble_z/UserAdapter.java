package com.example.zhuoh.zheng_map_ble_z;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by zhuo on 2017/6/26.
 */

public class UserAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> data;
    private ViewHolder viewHolder;

    public UserAdapter(Context context, List<Map<String, Object>> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.info_item, null);
            viewHolder.tv_data = (TextView) convertView
                    .findViewById(R.id.tv_datasearch);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 赋值
        Map map = data.get(position);
        String str = "";
        str = str+map.get("time").toString()+","+map.get("lat").toString()+","+
                map.get("lng").toString()+","+map.get("lac").toString()+","+
                map.get("cid").toString()+","+map.get("bid").toString()+","+
                map.get("cellMode").toString()+","+map.get("channel").toString()+","+
                map.get("rxlevel").toString();
        viewHolder.tv_data.setText(str);
        return convertView;
    }

    class ViewHolder {
        public TextView tv_data;

    }
}
