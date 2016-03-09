package com.apollo.cbb.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import com.apollo.cbb.R;
import com.apollo.cbb.biz.user.EsUserManager;
import com.apollo.cbb.ui.activity.NavigationBar.NavigationItem;
import com.apollo.cbb.ui.fragment.BaseFragment;
import com.apollo.cbb.ui.fragment.DateFragment;
import com.apollo.cbb.ui.fragment.MyFragment;
import com.apollo.cbb.ui.fragment.RecommendFragment;
import com.apollo.cbb.ui.fragment.SearchFragment;

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
        EsUserManager.destory();
    }
}
