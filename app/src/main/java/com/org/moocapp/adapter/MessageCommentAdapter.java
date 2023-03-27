package com.org.moocapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.org.moocapp.R;
import com.org.moocapp.entity.find.MessageCommentEntity;
import com.org.moocapp.util.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageCommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<MessageCommentEntity> datas;

    public void setDatas(List<MessageCommentEntity> datas) {
        this.datas = datas;
    }

    public MessageCommentAdapter(Context context) {
        this.mContext = context;
    }

    public MessageCommentAdapter(Context context, List<MessageCommentEntity> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        if (datas != null && datas.size() > 0) {
            return datas.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MessageCommentEntity messageCommentEntity = datas.get(i);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_message_comment, null);
            holder.commentItemImg =
                    convertView.findViewById(R.id.commentItemImg);
            holder.commentUsername = convertView.findViewById(R.id.comment_Username);
            holder.commentItemTime = convertView.findViewById(R.id.commentItemTime);
            holder.commentItemContent = convertView.findViewById(R.id.commentItemContent);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(mContext)
                .load(messageCommentEntity.getHeadPic())
                .transform(new CircleTransform())
                .into(holder.commentItemImg);
        holder.commentUsername.setText(messageCommentEntity.getUsername());
        holder.commentItemTime.setText(messageCommentEntity.getDate());
        holder.commentItemContent.setText(messageCommentEntity.getContent());
        //如果需要继续布局回复评论，加adapter
        return convertView;
    }

    private final class ViewHolder {
        public ImageView commentItemImg;            //评论人图片
        public TextView commentUsername;            //评论人昵称
        public TextView commentItemTime;            //评论时间
        public TextView commentItemContent;            //评论内容
    }
}
