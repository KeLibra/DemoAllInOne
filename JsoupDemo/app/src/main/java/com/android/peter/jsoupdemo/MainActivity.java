package com.android.peter.jsoupdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "peter.log." + MainActivity.class.getSimpleName();

    private final static int MSG_UPDATE_DATA = 0;
    private Context mContext;
    private SwipeRefreshLayout mRefreshLayout;
    private List<JianShuModel> mDataList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private JianShuAdapter mAdapter;
    private ThreadPoolExecutor mThreadPool = (ThreadPoolExecutor) Executors.newScheduledThreadPool(4);
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_UPDATE_DATA:
                    mRefreshLayout.setRefreshing(false);
                    mAdapter.setData(mDataList);
                    break;
                default:
                    //do nothing
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mContext = this;
        mRefreshLayout = findViewById(R.id.srl_update);
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary,null));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG,"onRefresh");
                grepJianShuData();
            }
        });
        mRecyclerView = findViewById(R.id.rv_list);
        mAdapter = new JianShuAdapter(mContext, mDataList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = mLayoutManager.getPosition(v);
                Log.d(TAG,"position = " + position);
                Intent intent = new Intent(mContext,JianShuDetailActivity.class);
                intent.putExtra("url",mDataList.get(position).getUrl());

                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d(TAG,"onScrollStateChanged newState = " + newState);
                switch (newState) {
                    case SCROLL_STATE_IDLE:
                        mAdapter.updateScrollState(false);
                        break;
                    case SCROLL_STATE_DRAGGING:
                    case SCROLL_STATE_SETTLING:
                        mAdapter.updateScrollState(true);
                        break;
                    default:
                        // do nothing
                }
            }
        });

        mRefreshLayout.setRefreshing(true);
        grepJianShuData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!mThreadPool.isShutdown()) {
            mThreadPool.shutdown();
        }
    }

    private void grepJianShuData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://www.jianshu.com")
                .get()
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"onFailure", e);
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG,"onResponse");
                if (response.isSuccessful()) {
                    try {
                        final String result = response.body().string();
                        mThreadPool.execute(new WorkTask(result));
                    } catch (Exception ex) {
                        Log.d(TAG,"Parse html fail");
                        mRefreshLayout.setRefreshing(false);
                    }
                } else {
                    Log.d(TAG,"Response fail!");
                    mRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void parseHtml(String html) {
        //将html转为Document对象
        Document document = Jsoup.parse(html);
        //获得li的元素集合
        Elements elements = document.select("ul.note-list li");
        JianShuModel jianShuModel;
        for (Element element: elements) {
            jianShuModel = new JianShuModel();
            jianShuModel.setNickname(element.select("a.nickname").first().text());
            jianShuModel.setTitle(element.select("a.title").first().text());
            jianShuModel.setContent(element.select("p.abstract").first().text());
            jianShuModel.setImage(element.select("a.wrap-img").first() != null ?
                    element.select("a.wrap-img").first().children().first().attr("src"): null);
            jianShuModel.setUrl(element.select("a.title").first().attr("href"));
            jianShuModel.setCommentsNum(element.select("a.nickname").next().text());
            jianShuModel.setLikeNum(element.select("a.nickname").next().next().text());
            mDataList.add(jianShuModel);
            Log.d(TAG,"model = " + jianShuModel.toString());
        }
        Message msg = Message.obtain();
        msg.what = MSG_UPDATE_DATA;
        mHandler.sendEmptyMessage(MSG_UPDATE_DATA);
    }

    private class WorkTask implements Runnable {
        private String mHtml;

        public WorkTask(String html) {
            mHtml = html;
        }

        @Override
        public void run() {
            parseHtml(mHtml);
        }
    }
}
