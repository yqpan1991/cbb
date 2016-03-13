package com.apollo.cbb.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
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
import com.apollo.cbb.ui.adapter.CanDateAdapter;
import com.apollo.cbb.ui.adapter.CommonItemClickListener;
import com.apollo.cbb.ui.adapter.RecommendAdapter;
import com.edus.view.DmRecyclerViewWrapper;
import com.edus.view.decoration.DividerItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Panda on 2016/3/13.
 */
public class CanDateListActivity extends BaseActivity implements View.OnClickListener {

    public static final String EXTRA_ADD_DATE_RESULT = "add_date_result";

    private DmRecyclerViewWrapper mDrvwContent;
    private CanDateAdapter mRecommendAdapter;
    private TextView mTvEmpty;
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candate_recommend_layout);
        initView();
        initData();
    }

    private void initData() {
        mUserInfo = EsUserManager.getInstance().getUserInfo();
        mDrvwContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mDrvwContent.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecommendAdapter = new CanDateAdapter(this, mCommonItemClickListener);
        mDrvwContent.setAdapter(mRecommendAdapter);
        mDrvwContent.enableLoadMore(false);
        mDrvwContent.enableRefresh(false);
        mDrvwContent.setOnRefreshListener(mOnRefreshListener);
        loadData();
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadData();
        }
    };


    private void loadData() {
        Log.e("test", "load data");
        EsApiHelper.fetchCanDateRecommendList(RecommendInfo.RECOMMEND_TYPE_NORMAL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("DaylyRecommendFragment", s);
                try {
                    String results = (new JSONObject(s)).getJSONArray(EsApiKeys.KEY_RESPONSE_RESULTS).toString();
                    List<RecommendInfo> recommendInfos = JSON.parseArray(results, RecommendInfo.class);
                    mDrvwContent.enableRefresh(true);
                    mDrvwContent.setRefreshing(false);
                    setDataResult(recommendInfos);
                    //if empty,show empty view
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mDrvwContent.enableRefresh(true);
                mDrvwContent.setRefreshing(false);
                Log.e("DaylyRecommendFragment", volleyError.toString());
                Snackbar.make(mDrvwContent, volleyError.toString(), Snackbar.LENGTH_LONG).setAction("retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDrvwContent.setRefreshing(true);
                    }
                }).show();
            }
        });
    }

    private void setDataResult(List<RecommendInfo> recommendInfos){
        mRecommendAdapter.setDataList(recommendInfos);
        if(recommendInfos != null && !recommendInfos.isEmpty()){
            mTvEmpty.setVisibility(View.INVISIBLE);
        }else{
            mTvEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        mDrvwContent = (DmRecyclerViewWrapper) findViewById(R.id.drvw_content);
        mTvEmpty = (TextView) findViewById(R.id.tv_empty);
        mTvEmpty.setOnClickListener(this);
        mTvEmpty.setVisibility(View.INVISIBLE);
    }

    private CommonItemClickListener mCommonItemClickListener = new CommonItemClickListener() {

        @Override
        public void onItemClickListener(int viewType, int position, View rootView, View clickView) {
            RecommendInfo info = mRecommendAdapter.getAdapterDataItem(position);
            showConfirmDialog(info);
        }

        @Override
        public boolean onItemLongClickListener(int viewType, int position, View rootView, View clickView) {
            return false;
        }
    };

    private void showConfirmDialog(final RecommendInfo result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_recommend).setMessage(getString(R.string.title_recommend_content, result.storeName)).setPositiveButton(R.string.title_recommend_ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //需要直接生成短串
                setResult(RESULT_OK, new Intent().putExtra(EXTRA_ADD_DATE_RESULT, result));
                finish();
            }
        }).setNegativeButton(R.string.title_recommend_cancel, null).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_empty:
                loadData();
                break;
        }
    }
}
