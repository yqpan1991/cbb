package com.apollo.cbb.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.apollo.cbb.R;
import com.apollo.cbb.biz.global.EsGlobal;
import com.apollo.cbb.biz.net.api.EsApiHelper;
import com.apollo.cbb.biz.user.EsUserManager;
import com.apollo.cbb.biz.user.UserInfo;
import com.apollo.cbb.ui.activity.LoginActivity;
import com.apollo.cbb.ui.activity.WifiSettingActivity;

/**
 * Created by Panda on 2016/3/6.
 */
public class MyFragment extends BaseFragment implements EsUserManager.OnUserLogOperationListener, View.OnClickListener {

    private TextView mTvUserId;
    private TextView mTvUserName;
    private TextView mTvLoginTip;
    private Button mBtLogout;
    private Button mBtSet;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvUserId = (TextView) view.findViewById(R.id.tv_userid);
        mTvUserName = (TextView) view.findViewById(R.id.tv_username);
        mTvLoginTip = (TextView) view.findViewById(R.id.tv_logintip);
        mBtLogout = (Button) view.findViewById(R.id.bt_logout);
        mBtSet = (Button) view.findViewById(R.id.bt_set);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EsUserManager.getInstance().registerOnUserLogOperationListener(this);
        mTvUserId.setOnClickListener(this);
        mTvUserName.setOnClickListener(this);
        mTvLoginTip.setOnClickListener(this);
        mBtLogout.setOnClickListener(this);
        mBtSet.setOnClickListener(this);
        updateLogOperationView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EsUserManager.getInstance().unregisterOnUserLogOperationListener(this);
    }

    private void updateLogOperationView(){
        if(EsUserManager.getInstance().hasLogIn()){
            UserInfo userInfo = EsUserManager.getInstance().getUserInfo();
            mTvUserId.setVisibility(View.VISIBLE);
            mTvUserName.setVisibility(View.VISIBLE);
            mTvUserId.setText("userId:" + userInfo.getUserId());
            mTvUserName.setText("userName:"+userInfo.getUserName());
            mTvLoginTip.setVisibility(View.INVISIBLE);
            mBtLogout.setVisibility(View.VISIBLE);
        }else{
            mTvUserId.setVisibility(View.INVISIBLE);
            mTvUserName.setVisibility(View.INVISIBLE);
            mTvLoginTip.setVisibility(View.VISIBLE);
            mBtLogout.setVisibility(View.GONE);
        }
    }


    @Override
    public void onUserLogin() {
        updateLogOperationView();
    }

    @Override
    public void onUserLout() {
        updateLogOperationView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_logintip:
                handleLogin();
                break;
            case R.id.tv_userid:
            case R.id.tv_username:
                showUserDetail();
                break;
            case R.id.bt_logout:
                handleLogout();
                break;
            case R.id.bt_set:
                showSetDetail();
                break;

        }
    }

    private void showSetDetail() {
        Intent intent = new Intent(getActivity(), WifiSettingActivity.class);
        startActivity(intent);
    }

    private void handleLogout() {
        UserInfo userInfo = EsUserManager.getInstance().getUserInfo();
        if(userInfo == null){
            updateLogOperationView();
        }else{
            int userId = userInfo.getUserId();
            EsApiHelper.handleLogout(userId, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    handleLogoutSuc();
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(EsGlobal.getGlobalContext(), volleyError.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void handleLogoutSuc() {
        EsUserManager userManager = EsUserManager.getInstance();
        userManager.setHasLogin(false);
        userManager.setUserInfo(null);
        userManager.clearNativeSaveUserInfo();
        userManager.notifyUserLogout();
    }

    private void showUserDetail() {
        Toast.makeText(EsGlobal.getGlobalContext(), "show userDetail", Toast.LENGTH_SHORT).show();
    }

    private void handleLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
