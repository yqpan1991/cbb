package com.apollo.cbb.biz.application;

import android.app.Application;

import com.apollo.cbb.biz.global.EsGlobal;
import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Panda on 2016/3/6.
 */
public class EsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EsGlobal.setGlobalContext(this);
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
    }


}
