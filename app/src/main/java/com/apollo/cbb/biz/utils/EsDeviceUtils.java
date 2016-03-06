package com.apollo.cbb.biz.utils;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Created by Panda on 2015/10/26.
 */
public class EsDeviceUtils {
    private static String ES_DEVICE_ID = null;

    public static String getEsDeviceId(Context context){
        if(TextUtils.isEmpty(ES_DEVICE_ID)){
            ES_DEVICE_ID = "jgz" + getSysDeviceId(context);
        }
        return ES_DEVICE_ID;
    }


    public static String getSysDeviceId(Context context){
        String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (deviceId == null || deviceId.length() == 0 || deviceId.equals("000000000000000")) {
            deviceId = Settings.Secure.getString(context.getContentResolver(), "android_id");
        }
        return deviceId;
    }

}
