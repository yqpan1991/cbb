package com.apollo.cbb.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.apollo.cbb.R;
import com.apollo.cbb.biz.user.EsUserManager;
import com.apollo.cbb.ui.activity.NavigationBar.NavigationItem;
import com.apollo.cbb.ui.fragment.BaseFragment;
import com.apollo.cbb.ui.fragment.DateFragment;
import com.apollo.cbb.ui.fragment.MyFragment;
import com.apollo.cbb.ui.fragment.RecommendFragment;
import com.apollo.cbb.ui.fragment.SearchFragment;
import com.baidu.mapapi.SDKInitializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    public static final int INDEX_RECOMMEND_FRAGMENT = 0;
    public static final int INDEX_DATE_FRAGMENT = 1;
    public static final int INDEX_SEARCH_FRAGMENT = 2;
    public static final int INDEX_MY_FRAGMENT = 3;
    private static final int FRAGMENT_COUNT = 4;
    private static final int DEFALUT_INDEX = INDEX_RECOMMEND_FRAGMENT;

    private NavigationBar mNavBar;
    private ViewPager mViewPager;
    private FragmentManager mFragmentManager;
    private SDKReceiver mReceiver;

    private List<NavigationBar.NavigationItem> mItemList = new ArrayList<>(FRAGMENT_COUNT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        initView();
        initData();
    }


    private void initView() {
        mNavBar = new NavigationBar(this, (RadioGroup) findViewById(R.id.rg_nav));
        mNavBar.setOnCheckedChangedListener(new NavigationBar.OnCheckedItemChangedListener() {
            @Override
            public void onCheckedItemChanged(NavigationBar.NavigationItem item) {
                MainActivity.this.onCheckedItemChanged(item);
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.vp_content);
    }

    private void onCheckedItemChanged(NavigationBar.NavigationItem item) {
        int index = mItemList.indexOf(item);
        if(index >= 0){
            mViewPager.setCurrentItem(index);
        }
    }

    private void initData() {

        NavigationItem[] items = new NavigationBar.NavigationItem[FRAGMENT_COUNT];
        items[INDEX_RECOMMEND_FRAGMENT] = new NavigationBar.NavigationItem(R.string.nav_recommend, R.mipmap.ic_launcher);
        items[INDEX_DATE_FRAGMENT] = new NavigationBar.NavigationItem(R.string.nav_date, R.mipmap.ic_launcher);
        items[INDEX_SEARCH_FRAGMENT] = new NavigationBar.NavigationItem(R.string.nav_search, R.mipmap.ic_launcher);
        items[INDEX_MY_FRAGMENT] = new NavigationBar.NavigationItem(R.string.nav_mine, R.mipmap.ic_launcher);
        mItemList.addAll(Arrays.asList(items));


        for(int index = 0; index < items.length; index ++){
            mNavBar.addItem(items[index]);
        }
        mFragmentManager = getSupportFragmentManager();
        mViewPager.setOffscreenPageLimit(FRAGMENT_COUNT);
        mViewPager.setAdapter(new MyViewPagerAdapter(mFragmentManager));
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                mNavBar.setCurrentItemIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mNavBar.setCurrentItemIndex(DEFALUT_INDEX);

        initBaiduSDK();
    }

    private void initBaiduSDK() {
        // 注册 SDK 广播监听者
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mReceiver = new SDKReceiver();
        registerReceiver(mReceiver, iFilter);
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment targetFragment = null;
            switch (position) {
                case INDEX_RECOMMEND_FRAGMENT:
                    targetFragment = new RecommendFragment();
                    break;
                case INDEX_DATE_FRAGMENT:
                    targetFragment = new DateFragment();
                    break;
                case INDEX_SEARCH_FRAGMENT:
                    targetFragment = new SearchFragment();
                    break;
                case INDEX_MY_FRAGMENT:
                    targetFragment = new MyFragment();
                    break;
            }
            return targetFragment;
        }

        @Override
        public int getCount() {
            return FRAGMENT_COUNT;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消监听 SDK 广播
        unregisterReceiver(mReceiver);
        EsUserManager.destory();
    }


    /**
     * 构造广播监听类，监听 SDK key 验证以及网络异常广播
     */
    public class SDKReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();
            Log.e(TAG, "action: " + s);
            String tip = null;
            if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
                tip = "key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置";
            } else if (s
                    .equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK)) {
                tip = "key 验证成功! 功能可以正常使用";
            }
            else if (s
                    .equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                tip = "网络出错";
            }
            Log.e(TAG, "net tip info: "+tip);
        }
    }
}
