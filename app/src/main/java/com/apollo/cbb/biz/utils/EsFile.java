package com.apollo.cbb.biz.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by Administrator on 2015/11/8.
 */
public class EsFile {
    private static final String CATEGORY_PATH = "es_files";
    private static final String IMAGE_PATH = CATEGORY_PATH+ File.separator+"image";


    public static String getImageDir(Context context){
        String path = null;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            path = Environment.getExternalStorageDirectory()+File.separator+ CATEGORY_PATH;
        }else{
            path =  context.getCacheDir()+"/"+ CATEGORY_PATH;
        }
        enableFilePathExist(path);
        return path;

    }

    private static  boolean enableFilePathExist(String path){
        if(!TextUtils.isEmpty(path)){
            File file = new File(path);
            if(file.exists()){
               return true;
            }
            file.mkdirs();
            return true;
        }
        return false;
    }

    public static String getFullImagePath(Context context, String imageName){
        return getImageDir(context) + File.separator + imageName;
    }


}
