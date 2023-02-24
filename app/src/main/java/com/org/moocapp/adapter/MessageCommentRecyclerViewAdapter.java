package com.org.moocapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.org.moocapp.R;
import com.org.moocapp.entity.find.MessageCommentEntity;
import com.org.moocapp.util.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageCommentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<MessageCommentEntity> datas;

    public void setDatas(List<MessageCommentEntity> datas) {
        this.datas = datas;
    }


    public MessageCommentRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    public MessageCommentRecyclerViewAdapter(Context context, List<MessageCommentEntity> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建适配器的布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_message_comment, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        MessageCommentEntity messageCommentEntity = datas.get(position);
        Picasso.with(mContext)
                .load(messageCommentEntity.getHeadPic())
                .transform(new CircleTransform())
                .into(vh.commentItemImg);
        vh.commentUsername.setText(messageCommentEntity.getUsername());
        vh.commentItemTime.setText(messageCommentEntity.getDate());
        vh.commentItemContent.setText(messageCommentEntity.getContent());
    }

    @Override
    public int getItemCount() {
        if (datas != null && datas.size() > 0) {
            return datas.size();
        } else {
            return 0;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView commentItemImg;            //评论人图片
        public TextView commentUsername;            //评论人昵称
        public TextView commentItemTime;            //评论时间
        public TextView commentItemContent;            //评论内容

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentItemImg = itemView.findViewById(R.id.commentItemImg);
            commentUsername = itemView.findViewById(R.id.comment_Username);
            commentItemTime = itemView.findViewById(R.id.commentItemTime);
            commentItemContent = itemView.findViewById(R.id.commentItemContent);
        }
    }
}
