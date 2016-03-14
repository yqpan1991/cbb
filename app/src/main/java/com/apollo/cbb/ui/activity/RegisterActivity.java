package com.apollo.cbb.ui.activity;

import android.content.Intent;
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
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtUserName;
    private EditText mEtPassword;
    private EditText mEtPasswordConfirm;
    private Button mBtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initTitle();
        initView();
        initData();
    }

    private void initTitle() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ((TextView)findViewById(R.id.center_title)).setText(R.string.title_register);
    }

    private void initView() {
        mEtUserName = (EditText) findViewById(R.id.et_username);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mEtPasswordConfirm = (EditText) findViewById(R.id.et_password_confirm);

        mBtRegister = (Button) findViewById(R.id.bt_register);
        mBtRegister.setOnClickListener(this);
    }

    private void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_register:
                handlerRegister();
                break;
        }
    }

    private void handlerRegister() {
        final String userName = mEtUserName.getText().toString();
        final String password = mEtPassword.getText().toString().trim();
        String passwordConfirm = mEtPasswordConfirm.getText().toString().trim();
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            mEtUserName.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordConfirm)){
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.equals(passwordConfirm)){
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            return;
        }
        EsApiHelper.registerUser(userName, password, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                handleLoginSucceed(userName, password);
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(EsGlobal.getGlobalContext(), volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void handleLoginSucceed(String userName, String password) {
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
                    startActivity(new Intent(EsGlobal.getGlobalContext(), MainActivity.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(EsGlobal.getGlobalContext(), volleyError.toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
