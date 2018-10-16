package com.android.peter.recyclerviewdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Integer> mDataList = new ArrayList<>();
    private MyAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SimpleItemTouchCallback mItemTouchCallback;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mRecyclerView = findViewById(R.id.rv_list);
        mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        initData();
        mAdapter = new MyAdapter(mContext, mDataList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = mLayoutManager.getPosition(v);
                Toast.makeText(mContext, "position = " + position, Toast.LENGTH_SHORT).show();
            }
        }, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int position = mLayoutManager.getPosition(v);
                return false;
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mItemTouchCallback = new SimpleItemTouchCallback(mAdapter,mDataList);
        mItemTouchHelper = new ItemTouchHelper(mItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void initData() {
        for(int i=0 ; i<40 ; i++) {
            mDataList.add(i);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerView.smoothScrollToPosition(1);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
