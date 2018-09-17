package com.android.peter.jsoupdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "peter.log." + MainActivity.class.getSimpleName();

    private List<JianShuModel> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        grepData();
    }

    private void grepData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://www.jianshu.com")
                .get()
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"onFailure", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG,"onResponse");
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    try {
                        parseHtml(result);
                    } catch (Exception ex) {
                        Log.d(TAG,"Parse html fail");
                    }
                } else {
                    Log.d(TAG,"Response fail!");
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
            Log.d(TAG,"model = " + jianShuModel.toString());
        }
    }
}
