package com.apollo.cbb.ui.adapter;

import android.view.View;

/**
 * Created by Panda on 2016/3/6.
 */
public interface CommonItemClickListener {
    void onItemClickListener(int viewType, int position, View rootView, View clickView);
    boolean onItemLongClickListener(int viewType, int position, View rootView, View clickView);
}
