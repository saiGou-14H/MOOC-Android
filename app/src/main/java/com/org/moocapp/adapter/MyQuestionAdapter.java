package com.org.moocapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.org.moocapp.R;
import com.org.moocapp.entity.find.ProblemEntity;


import java.io.Serializable;
import java.util.List;

public class MyQuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ProblemEntity> datas;

    /**
     * 点击事件
     */
    private static OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setDatas(List<ProblemEntity> datas) {
        this.datas = datas;
    }

    public MyQuestionAdapter(Context context) {
        this.mContext = context;
    }

    public MyQuestionAdapter(Context context, List<ProblemEntity> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    //返回适配器布局
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建适配器的布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_question, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //    绑定视图Holder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolder vh = (ViewHolder) holder;
        ProblemEntity problemEntity = datas.get(position);
        vh.problemEntity = problemEntity;
//        vh.tv_my_question_title.setText("提问的标题");
//        vh.tv_my_question_content.setText("提问的内容");
//        vh.tv_my_question_time.setText("提问的时间");
        vh.tv_my_question_title.setText(String.valueOf(problemEntity.getTitle()));
        vh.tv_my_question_content.setText(String.valueOf(problemEntity.getContent()));
        vh.tv_my_question_time.setText(String.valueOf(problemEntity.getCreateTime()));
        vh.tv_my_question_comment_num.setText(String.valueOf(problemEntity.getCommentNum()) + "回答");

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
        private TextView tv_my_question_title;
        private TextView tv_my_question_content;
        private TextView tv_my_question_time;
        private TextView tv_my_question_comment_num;


        private ProblemEntity problemEntity;

        public ViewHolder(@NonNull View view) {
            super(view);
            tv_my_question_title = view.findViewById(R.id.tv_my_question_title);
            tv_my_question_content = view.findViewById(R.id.tv_my_question_content);
            tv_my_question_time = view.findViewById(R.id.tv_my_question_time);
            tv_my_question_comment_num = view.findViewById(R.id.tv_my_question_comment_num);

            /**
             *
             */
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(problemEntity);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Serializable obj);
    }
}
