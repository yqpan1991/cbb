package com.apollo.cbb.biz.net.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.apollo.cbb.biz.net.VolleySingleton;
import com.apollo.cbb.biz.net.request.CustomStringRequest;
import com.apollo.cbb.biz.user.EsUserManager;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Panda on 2016/3/6.
 */
public class EsApiHelper {

    private static final String TAG = EsApiHelper.class.getSimpleName();

    private EsApiHelper(){

    }

    public static void handleLogin(final String userName, final String password, final Response.Listener<String> sucListener, final Response.ErrorListener errorListener){
        StringRequest stringRequest = new CustomStringRequest(Request.Method.POST, EsApi.getFullUrl(EsApi.USER_LOGIN), sucListener, errorListener){
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

    public static void handleLogout(final int userId, final Response.Listener<String> sucListener, final Response.ErrorListener errorListener){

        StringRequest stringRequest = new CustomStringRequest(Request.Method.POST, EsApi.getFullUrl(EsApi.USER_LOGOUT), sucListener, errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put(EsApiKeys.KEY_USER_USERID, userId+"");
                return map;
            }
        };
        VolleySingleton.addRequest(stringRequest);
    }

    public static void fetchRecommendList(final int type, Response.Listener<String> sucListener, final Response.ErrorListener errorListener){

        StringRequest stringRequest = new CustomStringRequest(Request.Method.GET, EsApi.getFullUrl(EsApi.RECOMMEND_WITHOUT_ID, type+""), sucListener, errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put(EsApiKeys.KEY_RECOMMEND_TYPE, type+"");
                return map;
            }
        };
        VolleySingleton.addRequest(stringRequest);
    }

    public static void fetchRecommendList(final int userId, final int type, Response.Listener<String> sucListener, final Response.ErrorListener errorListener){
        StringRequest stringRequest = new CustomStringRequest(Request.Method.GET, EsApi.getFullUrl(EsApi.RECOMMEND_WITH_ID, userId+"", type+""), sucListener, errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put(EsApiKeys.KEY_RECOMMEND_TYPE, type+"");
                map.put(EsApiKeys.KEY_USER_USERID, userId+"");
                return map;
            }
        };
        VolleySingleton.addRequest(stringRequest);
    }

    public static void fetchMyRecommendList(final int userId, Response.Listener<String> sucListener, final Response.ErrorListener errorListener){
        StringRequest stringRequest = new CustomStringRequest(Request.Method.GET, EsApi.getFullUrl(EsApi.RECOMMEND_MIME, userId+""), sucListener, errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put(EsApiKeys.KEY_USER_USERID, userId+"");
                return map;
            }
        };
        VolleySingleton.addRequest(stringRequest);
    }

    public static void fetchCanDateRecommendList(final int userId, Response.Listener<String> sucListener, final Response.ErrorListener errorListener){
        StringRequest stringRequest = new CustomStringRequest(Request.Method.GET, EsApi.getFullUrl(EsApi.RECOMMEND_MIME, userId+""), sucListener, errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put(EsApiKeys.KEY_USER_USERID, userId+"");
                return map;
            }
        };
        VolleySingleton.addRequest(stringRequest);
    }

    public static void uploadRecommend(final int userId,final int type, final String storeName,final  double latitude, final double longtitude,final String recommendInfo, Response.Listener<String> sucListener, final Response.ErrorListener errorListener){
        StringRequest stringRequest = new CustomStringRequest(Request.Method.POST, EsApi.getFullUrl(EsApi.RECOMMEND_UPLOAD), sucListener, errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put(EsApiKeys.KEY_USER_USERID, userId+"");
                map.put(EsApiKeys.KEY_RECOMMEND_TYPE, type+"");
                map.put(EsApiKeys.KEY_RECOMMEND_STORE_NAME, storeName);
                map.put(EsApiKeys.KEY_RECOMMEND_STORE_LATITUDE, latitude+"");
                map.put(EsApiKeys.KEY_RECOMMEND_STORE_LONGTITUDE, longtitude+"");
//                map.put(EsApiKeys.KEY_RECOMMEND_STORE_SHORTSTRING, recommendInfo+"");
                map.put(EsApiKeys.KEY_RECOMMEND_INFO, recommendInfo+"");
                return map;
            }
        };
        VolleySingleton.addRequest(stringRequest);
    }

    public static void fetchRecommendCommentList(final int userStoreId, Response.Listener<String> sucListener, final Response.ErrorListener errorListener){
        StringRequest stringRequest = new CustomStringRequest(Request.Method.GET, EsApi.getFullUrl(EsApi.RECOMMEND_COMMENT_LIST, userStoreId+""), sucListener, errorListener);
        VolleySingleton.addRequest(stringRequest);
    }

    public static void uploadComment(final int userId, final int userStoreId,final String comment, Response.Listener<String> sucListener, final Response.ErrorListener errorListener){
        StringRequest stringRequest = new CustomStringRequest(Request.Method.POST, EsApi.getFullUrl(EsApi.RECOMMEND_COMMENT_UPLOAD), sucListener, errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put(EsApiKeys.KEY_USER_USERID, userId+"");
                map.put(EsApiKeys.KEY_COMMENT_USERSTORE_ID, userStoreId+"");
                map.put(EsApiKeys.KEY_COMMENT_USERSTORE_COMMENT, comment);
                return map;
            }
        };
        VolleySingleton.addRequest(stringRequest);
    }

    public static void registerUser(final String userName, final String password, Response.Listener<String> sucListener, final Response.ErrorListener errorListener){
        StringRequest stringRequest = new CustomStringRequest(Request.Method.POST, EsApi.getFullUrl(EsApi.USER_REGISTER), sucListener, errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put(EsApiKeys.KEY_USER_USERNAME, userName+"");
                map.put(EsApiKeys.KEY_USER_PASSWORD, password+"");
                map.put(EsApiKeys.KEY_USER_USERTYPE, EsUserManager.USER_TYPE_NORMAL+"");
                return map;
            }
        };
        VolleySingleton.addRequest(stringRequest);
    }



}
