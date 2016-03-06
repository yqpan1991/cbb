package com.apollo.cbb.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.apollo.cbb.R;
import com.apollo.cbb.biz.global.EsGlobal;
import com.apollo.cbb.biz.net.api.EsApiHelper;
import com.apollo.cbb.biz.net.api.EsApiKeys;
import com.apollo.cbb.biz.user.EsUserManager;
import com.apollo.cbb.biz.user.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Panda on 2016/3/6.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtUserName;
    private EditText mEtPassword;
    private Button mBtLogin;
    private Button mBtRegister;

    private ProgressDialog mProgressDialog;
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initTitle();
        initContentView();
        initContentData();
    }

    private void initContentView() {
        mEtUserName = (EditText) findViewById(R.id.et_username);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mBtLogin = (Button) findViewById(R.id.bt_login);
        mBtLogin.setOnClickListener(this);
        mBtRegister = (Button) findViewById(R.id.bt_register);
        mBtRegister.setOnClickListener(this);
    }

    private void initContentData() {
        if(EsUserManager.getInstance().hasLogIn()){
            Toast.makeText(EsGlobal.getGlobalContext(), "user has login", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

    }


    private void initTitle() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ((TextView)findViewById(R.id.center_title)).setText("登录");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                handleLogin();
                break;
            case R.id.bt_register:
                handleRegister();
                break;
        }
    }

    private void handleRegister() {
        Toast.makeText(EsGlobal.getGlobalContext(), "handleRegister", Toast.LENGTH_SHORT).show();
    }

    private void handleLogin() {
        if(isLogin){
            Toast.makeText(EsGlobal.getGlobalContext(), "正在登录中,请稍后...", Toast.LENGTH_SHORT).show();
            return;
        }
        String userName = mEtUserName.getText().toString();
        String password = mEtPassword.getText().toString();
        if(TextUtils.isEmpty(userName) ||  TextUtils.isEmpty(password)){
            Toast.makeText(EsGlobal.getGlobalContext(), "数据不完整,请检查", Toast.LENGTH_SHORT).show();
            return;
        }
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("登录中");
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        isLogin = true;
        EsApiHelper.handleLogin(userName, password, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject userJson = (new JSONObject(s)).getJSONObject(EsApiKeys.KEY_USER_USERINFO);
                    UserInfo userInfo = UserInfo.parseJSONString(userJson.toString());
                    EsUserManager userManager = EsUserManager.getInstance();
                    userManager.setUserInfo(userInfo);
                    userManager.saveUserInfoToNative();
                    userManager.setHasLogin(true);
                    userManager.notifyUserLogin();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                isLogin  = false;
                mProgressDialog.dismiss();
                mProgressDialog = null;
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                isLogin = false;
                mProgressDialog.dismiss();
                mProgressDialog = null;
                Toast.makeText(EsGlobal.getGlobalContext(), volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
