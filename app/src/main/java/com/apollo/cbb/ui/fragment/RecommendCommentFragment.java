package com.apollo.cbb.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.apollo.cbb.R;
import com.apollo.cbb.biz.net.api.EsApiHelper;
import com.apollo.cbb.biz.net.api.EsApiKeys;
import com.apollo.cbb.biz.net.model.CommentInfo;
import com.apollo.cbb.biz.net.model.RecommendInfo;
import com.apollo.cbb.biz.user.EsUserManager;
import com.apollo.cbb.biz.user.UserInfo;
import com.apollo.cbb.ui.activity.LoginActivity;
import com.apollo.cbb.ui.adapter.CommentAdapter;
import com.apollo.cbb.ui.adapter.RecommendAdapter;
import com.edus.view.DmRecyclerViewWrapper;
import com.edus.view.decoration.DividerItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Panda on 2016/3/6.
 */
public class RecommendCommentFragment extends BaseFragment implements View.OnClickListener, EsUserManager.OnUserLogOperationListener {

    public static final String BUNDLE_EXTRA_DATA = "recommend";

    private DmRecyclerViewWrapper mDrvwContent;
    private CommentAdapter mCommentAdatper;
    private TextView mTvEmpty;
    private RelativeLayout mRlComment;
    private Button mBtComment;
    private EditText mEtCommentInfo;


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
        return inflater.inflate(R.layout.recommend_comment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDrvwContent = (DmRecyclerViewWrapper) view.findViewById(R.id.drvw_content);
        mTvEmpty = (TextView) view.findViewById(R.id.tv_empty);
        mTvEmpty.setOnClickListener(this);
        mTvEmpty.setVisibility(View.INVISIBLE);

        mRlComment = (RelativeLayout) view.findViewById(R.id.rl_comment);
        mBtComment = (Button) view.findViewById(R.id.bt_comment);
        mBtComment.setOnClickListener(this);
        mEtCommentInfo = (EditText) view.findViewById(R.id.et_comment_info);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDrvwContent.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        mDrvwContent.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mCommentAdatper = new CommentAdapter(getActivity());
        mDrvwContent.setAdapter(mCommentAdatper);
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

    private void setDataResult(List<CommentInfo> recommendInfos) {
        mCommentAdatper.setDataList(recommendInfos);
        if (recommendInfos != null && !recommendInfos.isEmpty()) {
            mTvEmpty.setVisibility(View.INVISIBLE);
        } else {
            mTvEmpty.setText(R.string.data_result_empty);
            mTvEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void loadData() {
        EsApiHelper.fetchRecommendCommentList(mRecommendInfo.userStoreId, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("DaylyRecommendFragment", s);
                try {
                    String results = (new JSONObject(s)).getJSONArray(EsApiKeys.KEY_RESPONSE_RESULTS).toString();
                    List<CommentInfo> recommendInfos = JSON.parseArray(results, CommentInfo.class);
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
        switch (v.getId()) {
            case R.id.tv_empty:
                handleEmptyClicked();
                break;
            case R.id.bt_comment:
                handleCommitComment();
                break;
        }
    }

    private void handleCommitComment() {
        if (!EsUserManager.getInstance().hasLogIn()) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return;
        }
        String commentInfo = mEtCommentInfo.getText().toString();
        if (TextUtils.isEmpty(commentInfo)) {
            Toast.makeText(getActivity(), R.string.comment_is_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        UserInfo userInfo = EsUserManager.getInstance().getUserInfo();
        EsApiHelper.uploadComment(userInfo.getUserId(), mRecommendInfo.userStoreId, commentInfo, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                mDrvwContent.setRefreshing(true);
                loadData();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void handleEmptyClicked() {
        mDrvwContent.setRefreshing(true);
        loadData();
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
