package com.example.zhuoh.zheng_map_ble_z;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataActivity extends AppCompatActivity {

    private ListView lv_users;
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        lv_users = (ListView)findViewById(R.id.list_info);
        UserDao userDao = new UserDao(data,this);
        userDao.getAllObjects();
        lv_users.setAdapter(new UserAdapter(this,data));
    }
}
