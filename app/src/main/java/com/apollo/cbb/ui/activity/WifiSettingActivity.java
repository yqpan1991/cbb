package com.apollo.cbb.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.apollo.cbb.R;
import com.apollo.cbb.biz.global.EsGlobal;
import com.apollo.cbb.biz.net.api.EsApi;
import com.apollo.cbb.biz.utils.EsNetWorkUtils;

/**
 * Created by Panda on 2016/3/6.
 */
public class WifiSettingActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvCurrent;
    private EditText mEtSuggest;
    private Button mBtUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_settings);
        initTitle();
        initContentView();
        initContentData();
        //显示当前wifi地址
        //当前设置地址
        //更改后地址
    }

    private void initContentData() {
        updateData();
    }

    private void initContentView() {
        mTvCurrent = (TextView) findViewById(R.id.tv_current);
        mEtSuggest = (EditText) findViewById(R.id.et_suggest);
        mBtUpdate = (Button) findViewById(R.id.bt_ok);
        mBtUpdate.setOnClickListener(this);
    }

    private void initTitle() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ((TextView)findViewById(R.id.center_title)).setText("HOST设置");
    }

    private void updateData(){
        mTvCurrent.setText(EsApi.getHost());
//        mEtSuggest.setText("http://"+EsNetWorkUtils.getWifiIp(EsGlobal.getGlobalContext()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_ok:
                handlerUpdate();
                break;

        }
    }

    private void handlerUpdate() {
        EsApi.setHost("http://"+mEtSuggest.getText().toString());
        updateData();
    }
}
