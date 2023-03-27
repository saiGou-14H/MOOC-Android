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
import com.org.moocapp.entity.find.MessageEntity;
import com.org.moocapp.util.CircleTransform;
import com.org.moocapp.util.StringUtils;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<MessageEntity> datas;

    /**
     * 点击事件
     */
    private static OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setDatas(List<MessageEntity> datas) {
        this.datas = datas;
    }

    public MessageAdapter(Context context) {
        this.mContext = context;
    }

    public MessageAdapter(Context context, List<MessageEntity> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    //返回适配器布局
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建适配器的布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_message, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //    绑定视图Holder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //holder
//        position
        //赋值
        ViewHolder vh = (ViewHolder) holder;
        MessageEntity messageEntity = datas.get(position);
        vh.messageEntity = messageEntity;
        vh.tvTitle.setText(messageEntity.getTitle());
//        vh.tvContent.setText(messageEntity.getContent());

        /**
         * CircleTransform类
         */
        Picasso.with(mContext)
                .load(messageEntity.getHeadPic())
                .transform(new CircleTransform())
                .into(vh.imgHeader);
        if (StringUtils.isEmpty(messageEntity.getResourceUrl())){

        }else {
            Picasso.with(mContext)
                    .load(messageEntity.getResourceUrl())
                    .into(vh.message_img);
        }

        vh.tvAuthor.setText(messageEntity.getAuthor());
        vh.tvMessage_like.setText(String.valueOf(messageEntity.getMessageLike() + "点赞"));
        vh.tvComment.setText(String.valueOf(messageEntity.getCommentNum() + "评论"));
        vh.tvMessage_time.setText(String.valueOf(messageEntity.getDate()));
    }

    @Override
    public int getItemCount() {
        if (datas != null && datas.size() > 0) {
            return datas.size();
        } else {
            return 0;
        }
    }

    //拿ViewHolder布局id
    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvContent;
        private TextView tvAuthor;
        private ImageView imgHeader;
        private ImageView message_img;

        private TextView tvMessage_like;
        private TextView tvComment;
        private TextView tvMessage_time;

        private MessageEntity messageEntity;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvTitle = view.findViewById(R.id.message_title);
//            tvContent = view.findViewById(R.id.message_content);
            tvAuthor = view.findViewById(R.id.message_author);
            message_img = view.findViewById(R.id.message_img);

            imgHeader = view.findViewById(R.id.img_header);
            tvMessage_like = view.findViewById(R.id.message_like);
            tvComment = view.findViewById(R.id.message_comment);
            tvMessage_time = view.findViewById(R.id.message_time);

            /**
             *
             */
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(messageEntity);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Serializable obj);
    }
}
