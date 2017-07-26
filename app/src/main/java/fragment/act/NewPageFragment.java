package fragment.act;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhuoh.zheng_map_ble_z.FullDataActivity;
import com.example.zhuoh.zheng_map_ble_z.PageFragment;
import com.example.zhuoh.zheng_map_ble_z.R;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.Set;

import UI.SimpleCardFragment;
import entity.TabEntity;
import utils.ViewFindUtils;

import static com.example.zhuoh.zheng_map_ble_z.DeviceListActivity.EXTRA_DEVICE_ADDRESS;


public class NewPageFragment extends Fragment {
    //case1
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"全网", "移2", "移3", "移4", "联2","联3","联4","电2","电4","WIFI"};
    private ViewPager mViewPager;
    private CommonTabLayout mTabLayout_8;

    //case5
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    TextView tv_paired_device,tv_new_device;
    Button btn_scan;
    ListView ListV_paried,ListV_new;

    public static final String ARGS_PAGE = "args_page";
    private int mPage;
    public NewPageFragment() {
        // Required empty public constructor
    }


    public static NewPageFragment newInstance(int page) {
        Bundle args = new Bundle();

        args.putInt(ARGS_PAGE, page);
        NewPageFragment fragment = new NewPageFragment();
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
                View view = inflater.inflate(R.layout.activity_full_data,container,false);
                initviewpage(view);
                return view;
            case 2:
                View view2 = inflater.inflate(R.layout.fragment_page2,container,false);
                return view2;
            case 3:
                View view3 = inflater.inflate(R.layout.fragment_page2,container,false);
                return view3;
            case 4:
                View view4 = inflater.inflate(R.layout.fragment_page2,container,false);
                return view4;
            case 5:
                View view5 = inflater.inflate(R.layout.newfragment_device,container,false);
                initdevice(view5);
                IntentFilter filter = new IntentFilter();
                filter.addAction(BluetoothDevice.ACTION_FOUND);
                filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
                view5.getContext().registerReceiver(mReceiver,filter);
                return view5;
        }
        /*View view = inflater.inflate(R.layout.fragment_page,container,false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText("第"+mPage+"页");
        return view;*/
        View view = inflater.inflate(R.layout.fragment_page2,container,false);
        return view;
    }
    //case1
    void initviewpage(View view){
        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));

        }

        //mDecorView = .getDecorView();
        mViewPager = ViewFindUtils.find(view, R.id.vp_2);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return true;
            }
        });
        mViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }
        /** indicator圆角色块 */
        mTabLayout_8 = ViewFindUtils.find(view, R.id.tl_8);
        mTabLayout_8.setTabData(mTabEntities);
        tl_2();
    }
    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position+1);
            //return mFragments.get(position);
        }
    }
    private void tl_2() {
        mTabLayout_8.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                /*if (position == 0) {
                    mTabLayout_8.showMsg(0, mRandom.nextInt(100) + 1);*/
//                    UnreadMsgUtils.show(mTabLayout_2.getMsgView(0), mRandom.nextInt(100) + 1);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout_8.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
    }
    //case5
    void initdevice(View view5){

        tv_new_device=(TextView)view5.findViewById(R.id.tv_new_device);
        tv_paired_device=(TextView)view5.findViewById(R.id.tv_paired_device);
        // 初始化数组适配器。一个已配对装置和
        //一个新发现的设备
        mPairedDevicesArrayAdapter = new ArrayAdapter<>(view5.getContext(),android.R.layout.test_list_item);
        mNewDevicesArrayAdapter    = new ArrayAdapter<>(view5.getContext(),android.R.layout.test_list_item);

        //寻找和建立配对设备列表
        ListV_paried   = (ListView)view5.findViewById(R.id.listV_paried_device);
        ListV_paried.setAdapter(mPairedDevicesArrayAdapter);

        ListV_paried.setOnItemClickListener(mDeviceClickListener);

        // 寻找和建立为新发现的设备列表
        ListV_new = (ListView) view5.findViewById(R.id.listV_new_device);
        ListV_new.setAdapter(mNewDevicesArrayAdapter);
        ListV_new.setOnItemClickListener(mDeviceClickListener);



        // 结果取消如果用户备份
        btn_scan = (Button) view5.findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                mNewDevicesArrayAdapter.clear();
                doDiscovery();
                v.setVisibility(View.GONE);
            }
        });

        // 获取本地蓝牙适配器
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        // 得到一套目前配对设备
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
        if (pairedDevices.size() > 0)
        {
            tv_paired_device.setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices)
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
        }
        else
        {
            tv_paired_device.setVisibility(View.GONE);
            String noDevices = getResources().getText(R.string.none_paired).toString();
            mPairedDevicesArrayAdapter.add(noDevices);
        }
    }
    private final BroadcastReceiver mReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            // 当发现设备
            if (BluetoothDevice.ACTION_FOUND.equals(action))
            {
                //把蓝牙设备对象的意图
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 如果它已经配对，跳过它，因为它的上市
                // 早已
                if (device.getBondState() != BluetoothDevice.BOND_BONDED)
                {
                    for (int index=0;index<mNewDevicesArrayAdapter.getCount();index++)
                    {
                        if (mNewDevicesArrayAdapter.getItem(index).equals(device.getName() + "\n" + device.getAddress()))
                        {
                            return;
                        }
                    }

                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
                //当发现后，改变活动名称
            }
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
            {
                //setProgressBarIndeterminateVisibility(false);
                //setTitle(R.string.select_device);
                if (mNewDevicesArrayAdapter.getCount() == 0)
                {
                    String noDevices = getResources().getText(R.string.none_found).toString();
                    mNewDevicesArrayAdapter.add(noDevices);
                }
                btn_scan.setVisibility(View.VISIBLE);

            }
        }
    };
    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3)
        {
            mBtAdapter.cancelDiscovery();
            //获得设备地址，这是近17字的
            //视图
            String info = ((TextView) v).getText().toString();
            Log.v("View5", "BT info length = "+info.length()+ "info ="+info );
            if (info.length()<=17) {
                Log.v("View5", "lenth<=17" );
                return;
            }

            String address = info.substring(info.length() - 17);
            Log.v("View5", "BT address="+address );
            //创建结果意图和包括地址
            /*Intent intent = new Intent();
            intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
            //结果，完成这项活动
            setResult(Activity.RESULT_OK, intent);
            finish();*/
        }
    };
    private void doDiscovery()
    {
        /*if (D) Log.d(TAG, "doDiscovery()");
        // 显示扫描的称号
        setProgressBarIndeterminateVisibility(true);
        setTitle(R.string.scanning);*/
        // 打开新设备的字幕
        tv_new_device.setVisibility(View.VISIBLE);

        // 如果我们已经发现，阻止它
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();}
        // 要求从bluetoothadapter发现
        mBtAdapter.startDiscovery();
    }
}
