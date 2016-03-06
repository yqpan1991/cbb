package com.apollo.cbb.biz.net.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Panda on 2016/3/6.
 */
public class EsApiResultHelper {

    public static boolean isSuc(JSONObject jsonObject){
        try {
            return jsonObject != null && jsonObject.getString(EsApiKeys.KEY_RESPONSE).equals(EsApiKeys.KEY_RESPONSE_SUC);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getErrorString(JSONObject jsonObject){
        try {
            return jsonObject.getString("error");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
