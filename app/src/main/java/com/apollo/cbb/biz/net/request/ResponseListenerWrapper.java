package com.apollo.cbb.biz.net.request;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.apollo.cbb.biz.net.api.EsApiResultHelper;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Panda on 2016/3/6.
 */
public class ResponseListenerWrapper implements Response.Listener<String>{
    public Response.Listener<String> mSucListener;
    private Response.ErrorListener mErrorListener;

    public ResponseListenerWrapper(Response.Listener<String> sucListener, Response.ErrorListener errorListener){
        mSucListener = sucListener;
        mErrorListener = errorListener;
    }

    @Override
    public void onResponse(String s) {
        try {
            if(EsApiResultHelper.isSuc(new JSONObject(s))){
                if(mSucListener != null){
                    mSucListener.onResponse(s);
                }
            }else{
                if(mErrorListener != null){
                    mErrorListener.onErrorResponse(new VolleyError("操作失败"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
