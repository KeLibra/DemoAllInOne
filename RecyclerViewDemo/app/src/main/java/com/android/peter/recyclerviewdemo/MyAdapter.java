package com.android.peter.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by peter on 2018/10/16.
 */

public class MyAdapter extends RecyclerView.Adapter {
    private final static int TYPE_HEADER = 0;
    private final static int TYPE_FOOTER = 1;
    private final static int TYPE_OTHER = 2;

    private Context mContext;
    private List<Integer> mDataList;
    private View.OnClickListener mOnClickListener;
    private View.OnLongClickListener mOnLongClickListener;
    private View mHeaderView;
    private View mFooterView;

    public MyAdapter(Context context, List<Integer> dataList, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener) {
        this.mContext = context;
        this.mDataList = dataList;
        this.mOnClickListener = onClickListener;
        this.mOnLongClickListener = onLongClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return TYPE_HEADER;
        }else if(position == 40 + 1){
            return TYPE_FOOTER;
        } else {
            return TYPE_OTHER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_HEADER) {
            mHeaderView =  LayoutInflater.from(mContext).inflate(R.layout.item_header_view,parent,false);
            return new RecyclerView.ViewHolder(mHeaderView){};
        }else if(viewType == TYPE_FOOTER){
            mFooterView =  LayoutInflater.from(mContext).inflate(R.layout.item_footer_view,parent,false);
            return new RecyclerView.ViewHolder(mFooterView){};
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_list,parent,false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof MyViewHolder) {
            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.mPosition.setText(String.valueOf(position));
            myViewHolder.itemView.setOnClickListener(mOnClickListener);
            myViewHolder.itemView.setOnLongClickListener(mOnLongClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size() + 2/*(mFooterView == null ? 0: 1) + (mHeaderView == null ? 0: 1)*/;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mPosition;

        public MyViewHolder(View itemView) {
            super(itemView);
            mPosition = itemView.findViewById(R.id.tv_item);
        }
    }

    public void addHeaderView(View view) {
        mHeaderView = view;
    }

    public void addFooterView(View view) {
        mFooterView = view;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }
}
