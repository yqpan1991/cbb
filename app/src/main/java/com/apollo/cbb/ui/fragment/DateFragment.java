package com.apollo.cbb.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
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
public class DateFragment extends BaseFragment implements View.OnClickListener, EsUserManager.OnUserLogOperationListener {

    private DmRecyclerViewWrapper mDrvwContent;
    private RecommendAdapter mRecommendAdapter;
    private TextView mTvEmpty;
    private TextView mTvAdd;

    public static final int REQUEST_CODE_ADD_DATE = 2000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.date_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitle(view);
        mDrvwContent = (DmRecyclerViewWrapper) view.findViewById(R.id.drvw_content);
        mTvEmpty = (TextView) view.findViewById(R.id.tv_empty);
        mTvEmpty.setOnClickListener(this);
        mTvEmpty.setVisibility(View.INVISIBLE);
    }

    private void initTitle(View rootView) {
        rootView.findViewById(R.id.back).setVisibility(View.GONE);
        ((TextView)rootView.findViewById(R.id.center_title)).setText(R.string.nav_date);
        mTvAdd = (TextView) rootView.findViewById(R.id.tv_operation);
        mTvAdd.setVisibility(View.VISIBLE);
        mTvAdd.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDrvwContent.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        mDrvwContent.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecommendAdapter = new RecommendAdapter(getActivity());
        mDrvwContent.setAdapter(mRecommendAdapter);
        mDrvwContent.enableLoadMore(false);
        mDrvwContent.enableRefresh(false);
        mDrvwContent.setOnRefreshListener(mOnRefreshListener);
        loadData();
        EsUserManager.getInstance().registerOnUserLogOperationListener(this);
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadData();
        }
    };

    private void setDataResult(List<RecommendInfo> recommendInfos){
        mRecommendAdapter.setDataList(recommendInfos);
        if(recommendInfos != null && !recommendInfos.isEmpty()){
            mTvEmpty.setVisibility(View.INVISIBLE);
        }else{
            mTvEmpty.setText(R.string.data_result_empty);
            mTvEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void loadData() {
        if(!EsUserManager.getInstance().hasLogIn()){
            mTvEmpty.setVisibility(View.VISIBLE);
            mTvEmpty.setText(R.string.data_result_unlogin);
            return;
        }
        UserInfo userInfo = EsUserManager.getInstance().getUserInfo();
        EsApiHelper.fetchRecommendList(userInfo.getUserId(), RecommendInfo.RECOMMEND_TYPE_DATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("DaylyRecommendFragment", s);
                try {
                    String results = (new JSONObject(s)).getJSONArray(EsApiKeys.KEY_RESPONSE_RESULTS).toString();
                    List<RecommendInfo> recommendInfos = JSON.parseArray(results, RecommendInfo.class);
                    mDrvwContent.enableRefresh(true);
                    mDrvwContent.setRefreshing(false);
                    setDataResult(recommendInfos);
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
                mTvEmpty.setVisibility(View.VISIBLE);
                mTvEmpty.setText(R.string.data_result_error);
                Snackbar.make(mDrvwContent, volleyError.toString(), Snackbar.LENGTH_LONG).setAction("retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDrvwContent.setRefreshing(true);
                    }
                }).show();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EsUserManager.getInstance().unregisterOnUserLogOperationListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_empty:
                handleEmptyClicked();
                break;
            case R.id.tv_operation:
                handleAdd();
                break;
        }
    }

    private void handleAdd() {
        if(EsUserManager.getInstance().hasLogIn()){
            Intent intent = new Intent(getActivity(), CanDateListActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_DATE);
        }else{
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private void handleEmptyClicked() {
        if(EsUserManager.getInstance().hasLogIn()){
            loadData();
        }else{
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_ADD_DATE:
                handleDateAdded(resultCode, data);
                break;
        }
    }

    private void handleDateAdded(int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            Toast.makeText(getActivity(), "data.toString() = "+ data.toString(), Toast.LENGTH_SHORT).show();
            RecommendInfo info = (RecommendInfo) data.getSerializableExtra(CanDateListActivity.EXTRA_ADD_DATE_RESULT);
            if(info == null){
                return;
            }
            showAddDateDialog(info);
        }
    }

    private void showAddDateDialog(final RecommendInfo info) {
        AddDateDialog dialog = new AddDateDialog(getActivity(), info, new AddDateDialog.AddDateResultListener(){

            @Override
            public void onCancelClicked() {

            }

            @Override
            public void onOKClicked(String editInfo) {
                uploadRecommendInfo(info, editInfo);
            }
        });
        dialog.show();
    }

    private void uploadRecommendInfo(RecommendInfo recommendInfo, String recommendDateInfo) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.recommend_uploading));
        progressDialog.show();
        UserInfo userInfo = EsUserManager.getInstance().getUserInfo();
        EsApiHelper.uploadRecommend(userInfo.getUserId(), EsApiConst.RECOMMEND_TYPE_DATE,  recommendInfo.storeName, recommendInfo.latitude, recommendInfo.longtitude, recommendDateInfo,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        mDrvwContent.setRefreshing(true);
                        loadData();
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), volleyError.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onUserLogin() {
        loadData();
    }

    @Override
    public void onUserLout() {
        loadData();
    }
}
