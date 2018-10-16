package com.android.peter.recyclerviewdemo;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.util.Collections;
import java.util.List;

/**
 * Created by peter on 2018/10/16.
 */

public class SimpleItemTouchCallback extends ItemTouchHelper.Callback {
    private final static String TAG = SimpleItemTouchCallback.class.getSimpleName();

    private MyAdapter mAdapter;
    private List<Integer> mDataList;

    public SimpleItemTouchCallback(MyAdapter adapter, List<Integer> dataList) {
        this.mAdapter = adapter;
        this.mDataList = dataList;
    }

    //设置支持的拖拽、滑动的方向
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.d(TAG,"getMovementFlags");
        int dragFlags = ItemTouchHelper.DOWN | ItemTouchHelper.UP;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

        return makeMovementFlags(dragFlags,swipeFlags);
    }

    // 拖拽时回调
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.d(TAG,"onMove");
        int from = viewHolder.getAdapterPosition();
        int to = target.getAdapterPosition();
        Collections.swap(mDataList,from,to);
        mAdapter.notifyItemMoved(from,to);

        return true;
    }

    // 滑动时回调
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d(TAG,"onSwiped");
        int position = viewHolder.getAdapterPosition();
        mDataList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    //状态改变时回调
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        Log.d(TAG,"onSelectedChanged");
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            MyAdapter.MyViewHolder holder = (MyAdapter.MyViewHolder) viewHolder;
            holder.itemView.setBackgroundColor(0xffbcbcbc); //设置拖拽和侧滑时的背景色
        }
    }

    //拖拽或滑动完成之后调用，用来清除一些状态
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        Log.d(TAG,"clearView");
        MyAdapter.MyViewHolder holder = (MyAdapter.MyViewHolder) viewHolder;
        holder.itemView.setBackgroundColor(0xffbcbcbc); //设置拖拽和侧滑时的背景色
    }
}
