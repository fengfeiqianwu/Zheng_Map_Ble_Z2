package fragment.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhuoh.zheng_map_ble_z.FullDataActivity;
import com.example.zhuoh.zheng_map_ble_z.PageFragment;
import com.example.zhuoh.zheng_map_ble_z.R;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import UI.SimpleCardFragment;
import entity.TabEntity;
import utils.ViewFindUtils;


public class NewPageFragment extends Fragment {
    //case1
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"全网", "移2", "移3", "移4", "联2","联3","联4","电2","电4","WIFI"};
    private ViewPager mViewPager;
    private CommonTabLayout mTabLayout_8;

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
                View view5 = inflater.inflate(R.layout.fragment_page2,container,false);
                return view5;
        }
        /*View view = inflater.inflate(R.layout.fragment_page,container,false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText("第"+mPage+"页");
        return view;*/
        View view = inflater.inflate(R.layout.fragment_page2,container,false);
        return view;
    }
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
}
