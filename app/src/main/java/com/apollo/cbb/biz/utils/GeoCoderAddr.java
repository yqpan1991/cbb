package com.apollo.cbb.biz.utils;

import android.content.Context;
import android.widget.Toast;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

/**
 * Created by Panda on 2016/3/13.
 *
 * note:使用该类需注意,在不再使用该对象的位置或者是退出该应用的时候,记得callback exit方法.
 */
public class GeoCoderAddr implements OnGetGeoCoderResultListener {

    private GeoCoder mSearch;
    private Context mContext;
    public GeoCoderAddr(Context mContext){
        this.mContext = mContext;
        // 初始化搜索模块，注册事件监听
        mSearch = com.baidu.mapapi.search.geocode.GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
    }

    /**
     * 根据经纬度获取位置
     * @param result
     */
    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(mContext, "抱歉，未能找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
//            result.getLocation().latitude;//纬度
//            result.getLocation().longitude;//经度
    }

    /**
     * 根据经纬度获取位置
     * @param result
     */
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(mContext, "抱歉，未能找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        Toast.makeText(mContext, result.getAddress(), Toast.LENGTH_LONG)
                .show();
    }

    /**
     * 必须调用该方法,用于标记当前GeoCoder不再使用.目的是为了通知BMapManager
     */
    public void exit(){
        mSearch.destroy();
    }
}
