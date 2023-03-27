package com.org.moocapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.org.moocapp.R;
import com.org.moocapp.entity.find.MyMQuestionCommentEntity;

import java.io.Serializable;
import java.util.List;

public class MyAnswerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<MyMQuestionCommentEntity> datas;

    /**
     * 点击事件
     */
    private static OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setDatas(List<MyMQuestionCommentEntity> datas) {
        this.datas = datas;
    }

    public MyAnswerAdapter(Context context) {
        this.mContext = context;
    }

    public MyAnswerAdapter(Context context, List<MyMQuestionCommentEntity> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    //返回适配器布局
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建适配器的布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_answer, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //    绑定视图Holder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //赋值
        ViewHolder vh = (ViewHolder) holder;
        MyMQuestionCommentEntity myMQuestionCommentEntity = datas.get(position);
        vh.myMQuestionCommentEntity = myMQuestionCommentEntity;
        vh.tv_my_answer_title.setText(String.valueOf(myMQuestionCommentEntity.getTitle()));
        vh.tv_my_answer_content.setText(String.valueOf(myMQuestionCommentEntity.getContent()));
        vh.tv_my_answer_time.setText(String.valueOf(myMQuestionCommentEntity.getDate()));
        if (myMQuestionCommentEntity.getBest() == true) {
            vh.tv_my_answer_is_select.setText("已采纳");
        } else {
            vh.tv_my_answer_is_select.setText("待采纳");
        }


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
        private TextView tv_my_answer_title;
        private TextView tv_my_answer_content;
        private TextView tv_my_answer_time;
        private TextView tv_my_answer_is_select;
        private MyMQuestionCommentEntity myMQuestionCommentEntity;

        public ViewHolder(@NonNull View view) {
            super(view);
            tv_my_answer_title = view.findViewById(R.id.my_answer_title);
            tv_my_answer_content = view.findViewById(R.id.my_answer_content);
            tv_my_answer_time = view.findViewById(R.id.my_answer_time);
            tv_my_answer_is_select = view.findViewById(R.id.tv_my_answer_is_select);

            /**
             *
             */
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(myMQuestionCommentEntity);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Serializable obj);
    }
}
