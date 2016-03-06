package com.apollo.cbb.biz.net.api;

import com.apollo.cbb.biz.global.EsGlobal;
import com.apollo.cbb.biz.utils.EsPreferencesUtils;

/**
 * Created by Panda on 2016/3/6.
 */
public class EsApi {
    private static String DEFAULT_HOST = "http://www.baidu.com";
    private static String KEY_HOST = "api_host";
    private static String HOST = EsPreferencesUtils.getString(EsGlobal.getGlobalContext(), KEY_HOST , DEFAULT_HOST);
    private static String SERVER_PREFIX = "/cbb-server";
    public static final String LOGIN = "/user/login";

    public static void setHost(String host){
        HOST = host;
        EsPreferencesUtils.putString(EsGlobal.getGlobalContext(), KEY_HOST, host);

    }
    public static String getHost(){
        return HOST;
    }

    public static String getFullUrl(String url){
        return HOST + SERVER_PREFIX + url;
    }

    public static String getFullUrl(String url,Object[] params){
        return String.format(HOST+SERVER_PREFIX+url,params);
    }
}
