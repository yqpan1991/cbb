package com.apollo.cbb.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.apollo.cbb.R;
import com.apollo.cbb.biz.global.EsGlobal;
import com.apollo.cbb.biz.net.api.EsApiConst;
import com.apollo.cbb.biz.net.api.EsApiHelper;
import com.apollo.cbb.biz.net.api.EsApiKeys;
import com.apollo.cbb.biz.net.model.RecommendInfo;
import com.apollo.cbb.biz.user.EsUserManager;
import com.apollo.cbb.biz.user.UserInfo;
import com.apollo.cbb.ui.activity.CanDateListActivity;
import com.apollo.cbb.ui.activity.LoginActivity;
import com.apollo.cbb.ui.adapter.RecommendAdapter;
import com.apollo.cbb.ui.dialog.AddDateDialog;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.edus.view.DmRecyclerViewWrapper;
import com.edus.view.decoration.DividerItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Panda on 2016/3/6.
 */
public class DateFragment extends BaseFragment implements View.OnClickListener{

    private static final int INDEX_COMMON = 0;
    private static final int INDEX_MIME = 1;
    private static final int INDEX_COUNT = 2;

    private static final int INDEX_COMMON_TITLE = R.string.title_date_common;
    private static final int INDEX_MIME_TITLE = R.string.title_date_mime;

    private TabLayout mTlTitle;
    private ViewPager mVpContent;
    private MyPagerAdapter mPagerAdaper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.date_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTlTitle = (TabLayout) view.findViewById(R.id.tl_title);
        mVpContent = (ViewPager) view.findViewById(R.id.vp_content);
        mTlTitle.setTabMode(TabLayout.MODE_FIXED);

        mPagerAdaper = new MyPagerAdapter(getChildFragmentManager());
        mVpContent.setOffscreenPageLimit(INDEX_COUNT);
        mVpContent.setAdapter(mPagerAdaper);
        mTlTitle.setupWithViewPager(mVpContent);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = null;
            switch (position){
                case INDEX_COMMON:
                    fragment = new CommonDateFragment();
                    break;
                case INDEX_MIME:
                    fragment = new MyDateFragment();
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
                case INDEX_COMMON:
                    titleResid = INDEX_COMMON_TITLE;
                    break;
                case INDEX_MIME:
                    titleResid = INDEX_MIME_TITLE;
                    break;
            }
            return getActivity().getString(titleResid);
        }
    }
}
