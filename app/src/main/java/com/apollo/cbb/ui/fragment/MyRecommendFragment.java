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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.apollo.cbb.R;
import com.apollo.cbb.biz.net.api.EsApiHelper;
import com.apollo.cbb.biz.net.api.EsApiKeys;
import com.apollo.cbb.biz.net.model.RecommendInfo;
import com.apollo.cbb.biz.user.EsUserManager;
import com.apollo.cbb.biz.user.UserInfo;
import com.apollo.cbb.biz.utils.EsLog;
import com.apollo.cbb.ui.dialog.PoiResultEditDialog;
import com.apollo.cbb.ui.activity.LoginActivity;
import com.apollo.cbb.ui.activity.PoiSearchActivity;
import com.apollo.cbb.ui.adapter.RecommendAdapter;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.edus.view.DmRecyclerViewWrapper;
import com.edus.view.decoration.DividerItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Panda on 2016/3/6.
 */
public class MyRecommendFragment extends BaseFragment implements View.OnClickListener, EsUserManager.OnUserLogOperationListener {

    private DmRecyclerViewWrapper mDrvwContent;
    private RecommendAdapter mRecommendAdapter;
    private TextView mTvEmpty;
    private RelativeLayout mRlOperation;
    private Button mBtAdd;

    private static final int REQUEST_CODE_ADD_RECOMMEND = 100;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_recommend_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRlOperation = (RelativeLayout) view.findViewById(R.id.rl_operation);
        mBtAdd = (Button) view.findViewById(R.id.bt_add);
        mBtAdd.setOnClickListener(this);
        mDrvwContent = (DmRecyclerViewWrapper) view.findViewById(R.id.drvw_content);
        mTvEmpty = (TextView) view.findViewById(R.id.tv_empty);
        mTvEmpty.setOnClickListener(this);
        mTvEmpty.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDrvwContent.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        mDrvwContent.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecommendAdapter = new RecommendAdapter(getActivity());
        mDrvwContent.setAdapter(mRecommendAdapter);
        mDrvwContent.setOnRefreshListener(mOnRefreshListener);
        loadData();
        updateLoginUi();
        EsUserManager.getInstance().registerOnUserLogOperationListener(this);
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadData();
        }
    };

    private void setDataResult(List<RecommendInfo> recommendInfos) {
        mRecommendAdapter.setDataList(recommendInfos);
        if (recommendInfos != null && !recommendInfos.isEmpty()) {
            mTvEmpty.setVisibility(View.INVISIBLE);
        } else {
            mTvEmpty.setText(R.string.data_result_empty);
            mTvEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void loadData() {
        Log.e("test", "load data");
        setDataResult(null);
        if (EsUserManager.getInstance().hasLogIn()) {
            UserInfo userInfo = EsUserManager.getInstance().getUserInfo();
            int userId = userInfo.getUserId();
            EsApiHelper.fetchMyRecommendList(userId, new Response.Listener<String>() {
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
                    if (mRecommendAdapter.getAdapterItemCount() == 0) {
                        mTvEmpty.setText(R.string.data_result_error);
                        mTvEmpty.setVisibility(View.VISIBLE);
                    }
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
        } else {
            mTvEmpty.setText(R.string.data_result_unlogin);
            mDrvwContent.enableLoadMore(false);
            mDrvwContent.enableRefresh(false);
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EsUserManager.getInstance().unregisterOnUserLogOperationListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_empty:
                handleEmptyClicked();
                break;
            case R.id.bt_add:
                handleAddClicked();
                break;
        }
    }

    private void handleAddClicked() {
//        百度地图选取位置,进行推荐,
//                推荐完成之后,可以相约
        Intent intent = new Intent(getActivity(), PoiSearchActivity.class);
        intent.putExtra(PoiSearchActivity.EXTRA_IS_RECOMMEND, true);
        startActivityForResult(intent, REQUEST_CODE_ADD_RECOMMEND);
    }


    private void handleEmptyClicked() {
        if (EsUserManager.getInstance().hasLogIn()) {
            loadData();
        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onUserLogin() {
        loadData();
        updateLoginUi();
    }

    @Override
    public void onUserLout() {
        loadData();
        updateLoginUi();
    }

    private void updateLoginUi() {
        if (EsUserManager.getInstance().hasLogIn()) {
            mRlOperation.setVisibility(View.VISIBLE);
        } else {
            mRlOperation.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_ADD_RECOMMEND:
                handlerRecommendAddResult(resultCode, data);
                break;
        }
    }

    private void handlerRecommendAddResult(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            EsLog.e(TAG, data.toString());
            PoiDetailResult poiDetailResult = data.getParcelableExtra(PoiSearchActivity.EXTRA_RECOMMEND_RESULT);
            if (poiDetailResult == null) {
                return;
            }
            //如果需要做分享,需要将短串进行生成,暂时先不生成
            //1. parseIntentData
            //2. edit info

            //3. commit info
            //弹出对话框,编辑推荐信息,然后推荐即可
            showRecommendDialog(poiDetailResult);

        }
    }

    private void showRecommendDialog(final PoiDetailResult poiDetailResult) {
        PoiResultEditDialog dialog = new PoiResultEditDialog(getContext(), poiDetailResult, EsUserManager.getInstance().getUserInfo().isAdmin(), new PoiResultEditDialog.PoiResultListener() {

            @Override
            public void onCancelClicked() {

            }

            @Override
            public void onOKClicked(String editInfo, int type) {
                //upload to server
                EsLog.e(TAG, "editInfo:" + editInfo + ",type:" + type);
                uploadRecommendInfo(poiDetailResult, editInfo, type);
            }
        });
        dialog.show();

    }

    private void uploadRecommendInfo(PoiDetailResult poiDetailResult, String recommendInfo, int type) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.recommend_uploading));
        progressDialog.show();
        UserInfo userInfo = EsUserManager.getInstance().getUserInfo();
        EsApiHelper.uploadRecommend(userInfo.getUserId(), type,  poiDetailResult.getName(), poiDetailResult.getLocation().latitude, poiDetailResult.getLocation().longitude, recommendInfo,
                new Response.Listener<String>(){

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
}
