package com.apollo.cbb.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apollo.cbb.R;
import com.apollo.cbb.biz.net.model.RecommendInfo;

/**
 * Created by Panda on 2016/3/13.
 */
public class AddDateDialog extends Dialog implements View.OnClickListener {

    private RecommendInfo mRecommendInfo;
    private AddDateResultListener mListener;
    private Button mBtOk;
    private Button mBtCancel;
    private TextView mTvStoreName;
    private TextView mTvAddress;
    private EditText mEtPoiRecommendInfo;

    public AddDateDialog(Context context, RecommendInfo  recommendInfo,  AddDateResultListener listener) {
        super(context);
        setContentView(R.layout.edit_add_date_layout);
        mBtOk = (Button) findViewById(R.id.bt_ok);
        mBtCancel = (Button) findViewById(R.id.bt_cancel);
        mTvStoreName = (TextView) findViewById(R.id.tv_storename);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mEtPoiRecommendInfo = (EditText) findViewById(R.id.et_recommend_info);

        mRecommendInfo = recommendInfo;
        mListener = listener;
        initListener();
        initData();
        setCancelable(true);
        setCanceledOnTouchOutside(false);

    }

    private void initListener() {
        mBtOk.setOnClickListener(this);
        mBtCancel.setOnClickListener(this);
    }

    private void initData() {
        mTvStoreName.setText(mRecommendInfo.storeName);
        mTvAddress.setText(mRecommendInfo.latitude+","+mRecommendInfo.longtitude);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_ok:
                handleOkClicked();
                break;
            case R.id.bt_cancel:
                handleCancelClicked();
                break;
        }
    }

    private void handleCancelClicked() {
        if(mListener != null){
            mListener.onCancelClicked();
        }
        dismiss();
    }

    private void handleOkClicked() {
        if(TextUtils.isEmpty(mEtPoiRecommendInfo.getText().toString())){
            Toast.makeText(getContext(), R.string.recommend_date_info_edit_empty_tip, Toast.LENGTH_SHORT).show();
        }else{
            if(mListener != null){
                mListener.onOKClicked(mEtPoiRecommendInfo.getText().toString());
            }
            dismiss();
        }
    }


    public interface AddDateResultListener {
        void onCancelClicked();
        void onOKClicked(String editInfo);
    }


}
