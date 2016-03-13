package com.apollo.cbb.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apollo.cbb.R;
import com.apollo.cbb.biz.net.model.RecommendInfo;
import com.edus.view.DmBaseAdapter;
import com.edus.view.DmBaseViewHolder;

/**
 * Created by Panda on 2016/3/6.
 */
public class CanDateAdapter extends DmBaseAdapter<RecommendInfo> {

    public CanDateAdapter(Context context) {
        super(context);
    }

    @Override
    public DmBaseViewHolder<RecommendInfo> onCreateAdapterViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.date_item, parent, false));
    }

    @Override
    public void onBindAdapterViewHolder(DmBaseViewHolder<RecommendInfo> holder, int dataListPosition) {
        holder.updateData(mDataList.get(dataListPosition), dataListPosition);
    }


    private class MyViewHolder extends DmBaseViewHolder<RecommendInfo>{

        private TextView mTvStoreName;
        private TextView mTvDescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvStoreName = (TextView) itemView.findViewById(R.id.tv_storeName);
            mTvDescription = (TextView) itemView.findViewById(R.id.tv_description);
        }

        @Override
        public void updateData(RecommendInfo recommendInfo, int position) {
            super.updateData(recommendInfo, position);
            mTvStoreName.setText(recommendInfo.storeName);
            mTvDescription.setText(recommendInfo.description);
        }
    }

}
