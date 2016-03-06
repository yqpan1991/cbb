package com.apollo.cbb.biz.application;

import android.app.Application;

import com.apollo.cbb.biz.global.EsGlobal;

/**
 * Created by Panda on 2016/3/6.
 */
public class EsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EsGlobal.setGlobalContext(this);
    }


}
