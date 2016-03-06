package com.apollo.cbb.biz.user;

import com.apollo.cbb.biz.global.EsGlobal;
import com.apollo.cbb.biz.utils.EsPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Panda on 2016/3/6.
 */
public class EsUserManager {

    private static final String SETTING_NATIVE_USERINFO = null;
    private static final String KEY_NATIVE_USERINFO = "native_userinfo";

    private static EsUserManager instance;
    private UserInfo mUserInfo;

    private List<OnUserLogOperationListener> mOnUserLogOperationList;

    public static EsUserManager getInstance(){
        if(instance == null){
            synchronized (EsUserManager.class){
                if(instance == null){
                    instance = new EsUserManager();
                }
            }
        }
        return instance;
    }

    private EsUserManager(){
        mOnUserLogOperationList = new ArrayList<>();
    }

    //用户信息
    //用户是否登录了
    private boolean hasLogin;

    public void setHasLogin(boolean hasLogin) {
        this.hasLogin = hasLogin;
    }

    public boolean hasLogIn() {
        return hasLogin;
    }


    public UserInfo getUserInfo() {
        return mUserInfo;
    }

    public void setUserInfo(UserInfo userInfo){
        mUserInfo = userInfo;
    }

    public void saveUserInfoToNative(){
        EsPreferencesUtils.putString(EsGlobal.getGlobalContext(), KEY_NATIVE_USERINFO, mUserInfo.toServerJSON());
    }


    public UserInfo getNativeSaveUserInfo() {
        return UserInfo.parseJSONString(EsPreferencesUtils.getString(EsGlobal.getGlobalContext(), KEY_NATIVE_USERINFO, null));
    }

    public void clearNativeSaveUserInfo() {
        EsPreferencesUtils.putString(EsGlobal.getGlobalContext(), KEY_NATIVE_USERINFO, null);
    }

    private void stop(){
        mOnUserLogOperationList.clear();
    }

    public static synchronized void destory(){
        if(instance != null){
            instance.stop();
            instance = null;
        }
    }

    public void registerOnUserLogOperationListener(OnUserLogOperationListener listener){
        if(!mOnUserLogOperationList.contains(listener)){
            mOnUserLogOperationList.add(listener);
        }
    }

    public void unregisterOnUserLogOperationListener(OnUserLogOperationListener listener){
        mOnUserLogOperationList.remove(listener);
    }

    public void notifyUserLogout(){
        for(OnUserLogOperationListener listener : mOnUserLogOperationList){
            listener.onUserLout();
        }
    }

    public void notifyUserLogin(){
        for(OnUserLogOperationListener listener : mOnUserLogOperationList){
            listener.onUserLogin();
        }
    }

    public interface OnUserLogOperationListener{
        public void onUserLogin();
        public void onUserLout();
    }

}
