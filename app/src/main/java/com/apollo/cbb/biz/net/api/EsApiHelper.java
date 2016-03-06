package com.apollo.cbb.biz.net.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.apollo.cbb.biz.net.VolleySingleton;
import com.apollo.cbb.biz.net.request.CustomStringRequest;
import com.apollo.cbb.biz.user.EsUserManager;
import com.apollo.cbb.biz.user.UserInfo;
import com.apollo.cbb.biz.utils.EsLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Panda on 2016/3/6.
 */
public class EsApiHelper {

    private static final String TAG = EsApiHelper.class.getSimpleName();

    private EsApiHelper(){

    }

    public static void registerUser(){

    }

    public static void handleLogin(final String userName, final String password, final Response.Listener<String> sucListener, final Response.ErrorListener errorListener){
        JSONObject requestJsonObject = new JSONObject();
        try {
            requestJsonObject.put(EsApiKeys.KEY_USER_USERNAME, userName);
            requestJsonObject.put(EsApiKeys.KEY_USER_PASSWORD, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        EsLog.e(TAG,"request param:"+requestJsonObject.toString());
        StringRequest stringRequest = new CustomStringRequest(Request.Method.POST, EsApi.getFullUrl(EsApi.LOGIN), sucListener, errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put(EsApiKeys.KEY_USER_USERNAME, userName);
                map.put(EsApiKeys.KEY_USER_PASSWORD, password);
                return map;
            }
        };
        VolleySingleton.addRequest(stringRequest);

    }

    public static void handleLogout(int userId){

    }
}
