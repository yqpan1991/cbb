package com.apollo.cbb.biz.utils;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by Panda on 2015/11/8.
 */
public class EsViewUtils {

    public static Bitmap getViewCache(View view){
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        return bitmap;
    }



}
