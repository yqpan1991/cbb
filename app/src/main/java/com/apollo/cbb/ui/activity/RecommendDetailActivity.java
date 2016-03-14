package com.apollo.cbb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.apollo.cbb.R;
import com.apollo.cbb.biz.net.model.RecommendInfo;
import com.apollo.cbb.ui.fragment.BaseFragment;
import com.apollo.cbb.ui.fragment.RecommendCommentFragment;
import com.apollo.cbb.ui.fragment.RecommendDetailFragment;

/**
 * Created by Panda on 2016/3/14.
 */
public class RecommendDetailActivity extends BaseActivity{

    private static final int INDEX_DETAIL = 0;
    private static final int INDEX_COMMENT = 1;
    private static final int INDEX_COUNT = 2;

    private static final int INDEX_DETAIL_TITLE = R.string.title_recommend_detail;
    private static final int INDEX_COMMENT_TITLE = R.string.title_recommend_comment;

    public static final String EXTRA_RECOMMEND_INFO = "recommend_info";

    private TabLayout mTlTitle;
    private ViewPager mVpContent;
    private MyPagerAdapter mPagerAdaper;

    private RecommendInfo mRecommendInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_detail_layout);
        initView();
        initData();
    }

    private void initView() {
        mTlTitle = (TabLayout) findViewById(R.id.tl_title);
        mVpContent = (ViewPager) findViewById(R.id.vp_content);
        mTlTitle.setTabMode(TabLayout.MODE_FIXED);

        mPagerAdaper = new MyPagerAdapter(getSupportFragmentManager());
        mVpContent.setOffscreenPageLimit(INDEX_COUNT);
        mVpContent.setAdapter(mPagerAdaper);
        mTlTitle.setupWithViewPager(mVpContent);
    }
    private void initData() {
        if(!parseComingIntent()){
            return;
        }

    }

    private boolean parseComingIntent() {
        Intent intent = getIntent();
        mRecommendInfo = (RecommendInfo) intent.getSerializableExtra(EXTRA_RECOMMEND_INFO);
        if(mRecommendInfo == null){
            finish();
            return false;
        }
        return true;
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = null;
            switch (position){
                case INDEX_DETAIL:
                    fragment = new RecommendDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(RecommendDetailFragment.BUNDLE_EXTRA_DATA, mRecommendInfo);
                    fragment.setArguments(bundle);
                    break;
                case INDEX_COMMENT:
                    fragment = new RecommendCommentFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable(RecommendCommentFragment.BUNDLE_EXTRA_DATA, mRecommendInfo);
                    fragment.setArguments(bundle1);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return INDEX_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            int titleResid = 0;
            switch (position){
                case INDEX_DETAIL:
                    titleResid = INDEX_DETAIL_TITLE;
                    break;
                case INDEX_COMMENT:
                    titleResid = INDEX_COMMENT_TITLE;
                    break;
            }
            return getString(titleResid);
        }
    }

    public static Intent genRecommendDetailIntent(Context context, RecommendInfo recommendInfo){
        Intent intent = new Intent(context, RecommendDetailActivity.class);
        intent.putExtra(EXTRA_RECOMMEND_INFO, recommendInfo);
        return intent;
    }

}

