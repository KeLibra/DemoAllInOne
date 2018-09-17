package com.android.peter.jsoupdemo;

/**
 * Created by peter on 2018/9/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * <li id="note-33437865" data-note-id="33437865" class="have-img">
 <a class="wrap-img" href="/p/58f2a556ccc5" target="_blank">
 <img class="  img-blur-done" src="//upload-images.jianshu.io/upload_images/13090773-2e7f06dff23cc479.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/300/h/240" alt="120">
 </a>
 <div class="content">
 <a class="title" target="_blank" href="/p/58f2a556ccc5">最佳程序员却是公司最懒的人, 每天一行代码都不写</a>
 <p class="abstract">
 每个公司都会有自己的最佳程序员，他们一般都有共同的特性：每天都是公司下班最晚的人，永远都在不知疲倦的敲打着代码。 但有一个程序员确不一样，他叫鲍...
 </p>
 <div class="meta">
 <a class="nickname" target="_blank" href="/u/e755673d98bc">一墨编程学习</a>
 <a target="_blank" href="/p/58f2a556ccc5#commentsNum">
 <i class="iconfont ic-list-commentsNum"></i> 3
 </a>      <span><i class="iconfont ic-list-likeNum"></i> 2</span>
 </div>
 </div>
 </li>
*/

public class JianShuModel implements Parcelable{
    private String nickname;
    private String title;
    private String content;
    private String image;
    private String url;
    private String commentsNum;
    private String likeNum;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nickname);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.image);
        dest.writeString(this.url);
        dest.writeString(this.commentsNum);
        dest.writeString(this.likeNum);
    }

    public JianShuModel() {
    }

    protected JianShuModel(Parcel in) {
        this.nickname = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.image = in.readString();
        this.url = in.readString();
        this.commentsNum = in.readString();
        this.likeNum = in.readString();
    }

    public static final Creator<JianShuModel> CREATOR = new Creator<JianShuModel>() {
        @Override
        public JianShuModel createFromParcel(Parcel source) {
            return new JianShuModel(source);
        }

        @Override
        public JianShuModel[] newArray(int size) {
            return new JianShuModel[size];
        }
    };

    public JianShuModel(String nickname, String title, String content, String image, String url, String commentsNum, String likeNum) {
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.image = image;
        this.url = url;
        this.commentsNum = commentsNum;
        this.likeNum = likeNum;
    }

    @Override
    public String toString() {
        return "JianShuModel{" +
                "nickname='" + nickname + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", url='" + url + '\'' +
                ", commentsNum='" + commentsNum + '\'' +
                ", likeNum='" + likeNum + '\'' +
                '}';
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(String commentsNum) {
        this.commentsNum = commentsNum;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }
}
