package com.android.peter.jsoupdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;

/**
 * Created by peter on 2018/9/18.
 */

public class JianShuAdapter extends RecyclerView.Adapter {
    private final static String TAG = "peter.log." + JianShuAdapter.class.getSimpleName();

    private final static int TYPE_WITH_IMAGE = 0;
    private final static int TYPE_WITHOUT_IMAGE = 1;

    private Context mContext;
    private List<JianShuModel> mDataList;
    private View.OnClickListener mOnClickListener;
    private boolean mIsScrolling;
    private RequestOptions mOptions
            = new RequestOptions()
            .error(R.mipmap.ic_error)
            .placeholder(R.mipmap.ic_place_holder);

    public JianShuAdapter(Context context, List<JianShuModel> dataList, View.OnClickListener onClickListener) {
        this.mContext = context;
        this.mDataList = dataList;
        this.mOnClickListener = onClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if(mDataList.get(position).getImage() != null) {
            return TYPE_WITH_IMAGE;
        } else {
            return TYPE_WITHOUT_IMAGE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_WITH_IMAGE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.jian_shu_item_with_iamge,parent,false);
            return new ContentViewHolderWithImage(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.jian_shu_item_without_iamge,parent,false);
            return new ContentViewHolderWithoutImage(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ContentViewHolderWithImage) {
            if(mDataList != null && mDataList.size() > 0) {
                ContentViewHolderWithImage contentViewHolderWithImage = (ContentViewHolderWithImage) holder;
                JianShuModel jianShuModel = mDataList.get(position);
                contentViewHolderWithImage.mTitle.setText(jianShuModel.getTitle());
                contentViewHolderWithImage.mContent.setText(jianShuModel.getContent());
                contentViewHolderWithImage.mNickName.setText(jianShuModel.getNickname());
                contentViewHolderWithImage.mCommentNum.setText(jianShuModel.getCommentsNum());
                contentViewHolderWithImage.mLikeNum.setText(jianShuModel.getLikeNum());
                if(!mIsScrolling) {
                    Glide.with(mContext)
                            .load("http:" + jianShuModel.getImage())
                            .apply(mOptions)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(contentViewHolderWithImage.mImage);
                } else {
                    contentViewHolderWithImage.mImage.setImageDrawable(mContext.getDrawable(R.mipmap.ic_place_holder));
                }
                contentViewHolderWithImage.itemView.setOnClickListener(mOnClickListener);
            }
        } else if(holder instanceof ContentViewHolderWithoutImage) {
            if(mDataList != null && mDataList.size() > 0) {
                ContentViewHolderWithoutImage contentViewHolderWithoutImage = (ContentViewHolderWithoutImage) holder;
                JianShuModel jianShuModel = mDataList.get(position);
                contentViewHolderWithoutImage.mTitle.setText(jianShuModel.getTitle());
                contentViewHolderWithoutImage.mContent.setText(jianShuModel.getContent());
                contentViewHolderWithoutImage.mNickName.setText(jianShuModel.getNickname());
                contentViewHolderWithoutImage.mCommentNum.setText(jianShuModel.getCommentsNum());
                contentViewHolderWithoutImage.mLikeNum.setText(jianShuModel.getLikeNum());
                contentViewHolderWithoutImage.itemView.setOnClickListener(mOnClickListener);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void setData(List<JianShuModel> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    public void updateScrollState(boolean isScrolling) {
        mIsScrolling = isScrolling;
        if(!mIsScrolling) {
            notifyDataSetChanged();
        }
    }

    public static class ContentViewHolderWithImage extends RecyclerView.ViewHolder {
        private TextView mNickName;
        private TextView mTitle;
        private TextView mContent;
        private TextView mCommentNum;
        private TextView mLikeNum;
        private ImageView mImage;

        public ContentViewHolderWithImage(View itemView) {
            super(itemView);
            mNickName = itemView.findViewById(R.id.tv_nickname);
            mTitle = itemView.findViewById(R.id.tv_title);
            mContent = itemView.findViewById(R.id.tv_content);
            mCommentNum = itemView.findViewById(R.id.tv_comment_num);
            mLikeNum = itemView.findViewById(R.id.tv_like_num);
            mImage = itemView.findViewById(R.id.iv_image);
        }
    }

    public static class ContentViewHolderWithoutImage extends RecyclerView.ViewHolder {
        private TextView mNickName;
        private TextView mTitle;
        private TextView mContent;
        private TextView mCommentNum;
        private TextView mLikeNum;

        public ContentViewHolderWithoutImage(View itemView) {
            super(itemView);
            mNickName = itemView.findViewById(R.id.tv_nickname);
            mTitle = itemView.findViewById(R.id.tv_title);
            mContent = itemView.findViewById(R.id.tv_content);
            mCommentNum = itemView.findViewById(R.id.tv_comment_num);
            mLikeNum = itemView.findViewById(R.id.tv_like_num);
        }
    }
}
