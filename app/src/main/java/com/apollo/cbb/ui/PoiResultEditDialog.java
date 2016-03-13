package com.apollo.cbb.ui;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.apollo.cbb.R;
import com.apollo.cbb.biz.net.api.EsApiConst;
import com.baidu.mapapi.search.poi.PoiDetailResult;

/**
 * Created by Panda on 2016/3/13.
 */
public class PoiResultEditDialog extends Dialog implements View.OnClickListener {

    private PoiDetailResult mPoiDetailResult;
    private PoiResultListener mListener;
    private Button mBtOk;
    private Button mBtCancel;
    private TextView mTvStoreName;
    private TextView mTvAddress;
    private RadioGroup mRgRecommendType;
    private EditText mEtPoiRecommendInfo;
    private boolean mIsAdmin;




    public PoiResultEditDialog(Context context, PoiDetailResult poiDetail,boolean isAdmin, PoiResultListener listener) {
        super(context);
        setContentView(R.layout.edit_poi_layout);
        mBtOk = (Button) findViewById(R.id.bt_ok);
        mBtCancel = (Button) findViewById(R.id.bt_cancel);
        mTvStoreName = (TextView) findViewById(R.id.tv_storename);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mRgRecommendType = (RadioGroup) findViewById(R.id.rg_recommend_type);
        mEtPoiRecommendInfo = (EditText) findViewById(R.id.et_recommend_info);

        mPoiDetailResult = poiDetail;
        mListener = listener;
        mIsAdmin = isAdmin;
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
        mTvStoreName.setText(mPoiDetailResult.getName());
        mTvAddress.setText(mPoiDetailResult.getAddress());
        mTvAddress.setText(mPoiDetailResult.getLocation().toString());

        if(mIsAdmin){
            mRgRecommendType.setEnabled(true);
        }else{
            mRgRecommendType.setEnabled(false);
        }
        mRgRecommendType.check(R.id.rb_daily);
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
            Toast.makeText(getContext(), R.string.recommend_info_edit_empty_tip, Toast.LENGTH_SHORT).show();
        }else{
            if(mListener != null){
                int type = mRgRecommendType.getCheckedRadioButtonId() == R.id.rb_daily ?
                        EsApiConst.RECOMMEND_TYPE_DAILY : EsApiConst.RECOMMEND_TYPE_NORMAL;
                mListener.onOKClicked(mEtPoiRecommendInfo.getText().toString(), type);
            }
            dismiss();
        }
    }


    public interface PoiResultListener{
        void onCancelClicked();
        void onOKClicked(String editInfo, int type);
    }


}
