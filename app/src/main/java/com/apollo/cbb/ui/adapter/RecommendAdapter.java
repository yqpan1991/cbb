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
public class RecommendAdapter extends DmBaseAdapter<RecommendInfo> {

    private CommonItemClickListener mCommonItemClickListener;

    public RecommendAdapter(Context context) {
        super(context);
    }

    public RecommendAdapter(Context context, CommonItemClickListener commonItemClickListener) {
        super(context);
        mCommonItemClickListener = commonItemClickListener;
    }

    @Override
    public DmBaseViewHolder<RecommendInfo> onCreateAdapterViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.recommend_item, parent, false));
    }

    @Override
    public void onBindAdapterViewHolder(DmBaseViewHolder<RecommendInfo> holder, int dataListPosition) {
        holder.updateData(mDataList.get(dataListPosition), dataListPosition);
    }

    private class MyItemClickListener implements View.OnClickListener {
        private int viewType;
        private int position;
        private View rootView;

        public MyItemClickListener(int viewType, int position, View rootView) {
            this.viewType = viewType;
            this.position = position;
            this.rootView = rootView;
        }

        @Override
        public void onClick(View v) {
            if(mCommonItemClickListener != null){
                mCommonItemClickListener.onItemClickListener(viewType, position, rootView, v);
            }
        }
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
            itemView.setOnClickListener(new MyItemClickListener(0, position, itemView));
        }
    }

}
