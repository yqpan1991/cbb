package com.apollo.cbb.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.apollo.cbb.R;
import com.apollo.cbb.biz.net.api.EsApiHelper;
import com.apollo.cbb.biz.net.api.EsApiKeys;
import com.apollo.cbb.biz.net.model.RecommendInfo;
import com.apollo.cbb.biz.user.EsUserManager;
import com.apollo.cbb.biz.user.UserInfo;
import com.apollo.cbb.ui.activity.LoginActivity;
import com.apollo.cbb.ui.adapter.RecommendAdapter;
import com.edus.view.DmRecyclerViewWrapper;
import com.edus.view.decoration.DividerItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Panda on 2016/3/6.
 */
public class RecommendDetailFragment extends BaseFragment implements View.OnClickListener {

    public static final String BUNDLE_EXTRA_DATA = "recommend";

    private TextView mTvStoreName;
    private TextView mTvRecommendType;
    private TextView mTvRecommendUser;
    private TextView mTvRecommendInfo;

    private RecommendInfo mRecommendInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mRecommendInfo = (RecommendInfo) arguments.getSerializable(BUNDLE_EXTRA_DATA);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recommend_detail_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvStoreName = (TextView) view.findViewById(R.id.tv_storename);
//        TextView mTvAddress = (TextView) view.findViewById(R.id.tv_address);
        mTvRecommendType = (TextView) view.findViewById(R.id.tv_recommend_type);
        mTvRecommendUser = (TextView) view.findViewById(R.id.tv_recommend_user);
        mTvRecommendInfo = (TextView) view.findViewById(R.id.tv_recommend_info);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        mTvStoreName.setText(mRecommendInfo.storeName);
        mTvRecommendType.setText(RecommendInfo.getRecommendTypeString(getActivity(), mRecommendInfo.type));
        mTvRecommendUser.setText(mRecommendInfo.userId+"");
        mTvRecommendInfo.setText(mRecommendInfo.description);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

}
