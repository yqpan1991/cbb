package com.apollo.cbb.ui.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.apollo.cbb.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Panda on 2015/9/20.
 */
public class NavigationBar {
    private Context mContext;
    private LayoutInflater mInflater;
    private RadioGroup mRadioGroup;
    private OnCheckedItemChangedListener mListener;

    private ViewHolder mViewHolder;

    private List<NavigationItem> mNavItemList = new ArrayList<>();

    public NavigationBar(Context context, RadioGroup radioGroup) {
        mContext = context;
        mRadioGroup = radioGroup;
        mViewHolder = new ViewHolder(mRadioGroup);
        initData();
    }

    private void initData() {
        mInflater = LayoutInflater.from(mContext);
    }

    public void addItem(NavigationItem item) {
        mNavItemList.add(item);
        //inflate this item
        RadioButton radiobutton = new RadioButton(mContext);
        radiobutton.setText(item.mDescId);
/*        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        mRadioGroup.addView(radiobutton, layoutParams);   */
        mRadioGroup.addView(radiobutton);
        item.resId = radiobutton.getId();
    }


    public void setOnCheckedChangedListener(OnCheckedItemChangedListener listener) {
        mListener = listener;
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mListener != null) {
//                    for (NavigationItem item : mNavItemList) {
//                        if (checkedId == item.resId) {
//                            mListener.onCheckedItemChanged(item);
//                            break;
//                        }
//                    }
                    mListener.onCheckedItemChanged(group, checkedId);
                }
            }
        });
    }


    public interface OnCheckedItemChangedListener {
        void onCheckedItemChanged(RadioGroup group, int checkedId);
    }


    public void setCurrentItemIndex(int index) {
        switch (index) {
            case MainActivity.INDEX_RECOMMEND_FRAGMENT:
                if (mListener != null) {
                    mViewHolder.rbRecommend.setChecked(true);
                    mListener.onCheckedItemChanged(null, R.id.rb_recommend);
                }
                break;
            case MainActivity.INDEX_DATE_FRAGMENT:
                if (mListener != null) {
                    mViewHolder.rbDate.setChecked(true);
                    mListener.onCheckedItemChanged(null, R.id.rb_date);
                }
                break;
            case MainActivity.INDEX_SEARCH_FRAGMENT:
                if (mListener != null) {
                    mViewHolder.rbSearch.setChecked(true);
                    mListener.onCheckedItemChanged(null, R.id.rb_search);
                }
                break;
            case MainActivity.INDEX_MY_FRAGMENT:
                if (mListener != null) {
                    mViewHolder.rbMe.setChecked(true);
                    mListener.onCheckedItemChanged(null, R.id.rb_Me);
                }
                break;
            default:
                break;
        }
    }


    public static class  ViewHolder{
        public RadioButton rbRecommend;
        public RadioButton rbDate;
        public RadioButton rbSearch;
        public RadioButton rbMe;
        public ViewHolder(View view){
            rbRecommend = (RadioButton)view.findViewById(R.id.rb_recommend);
            rbDate = (RadioButton) view.findViewById(R.id.rb_date);
            rbSearch = (RadioButton) view.findViewById(R.id.rb_search);
            rbMe = (RadioButton) view.findViewById(R.id.rb_Me);
        }
    }

    public static class NavigationItem {
        private int mDescId;
        private int mDrawableId;
        private int resId;

        public NavigationItem(int descId, int drawableId) {
            mDescId = descId;
            mDrawableId = drawableId;
        }
    }


}
