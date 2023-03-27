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
import com.org.moocapp.entity.find.ProblemEntity;
import com.org.moocapp.util.CircleTransform;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class ProblemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<ProblemEntity> datas;
    /**
     * 点击事件
     */
    private static OnItemClickListener mOnItemClickListener;

    public void setDatas(List<ProblemEntity> datas) {
        this.datas = datas;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public ProblemAdapter(Context context) {
        this.mContext = context;
    }

    public ProblemAdapter(Context context, List<ProblemEntity> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建适配器的布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_problem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //赋值
        ViewHolder vh = (ViewHolder) holder;
        ProblemEntity problemEntity = datas.get(position);
        vh.problemEntity = problemEntity;
        vh.tvProblem_author.setText(String.valueOf(problemEntity.getUsername()));
        vh.tvProblem_time.setText(String.valueOf(problemEntity.getCreateTime()));
        vh.tvProblem_title.setText(String.valueOf(problemEntity.getTitle()));
//        vh.tvProblem_content.setText(String.valueOf(problemEntity.getContent()));
        vh.tvProblem_comment_num.setText(String.valueOf(problemEntity.getCommentNum()));
        vh.tvProblem_like_num.setText(String.valueOf(problemEntity.getLikeNum()));

        Picasso.with(mContext)
                .load(problemEntity.getHeadPic())
                .transform(new CircleTransform())
                .into(vh.img_Problem_img_header);
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
        private ImageView img_Problem_img_header;
        private TextView tvProblem_author;
        private TextView tvProblem_time;
        private TextView tvProblem_title;
        private TextView tvProblem_content;
        private TextView tvProblem_comment_num;
        private TextView tvProblem_like_num;

        private ProblemEntity problemEntity;

        public ViewHolder(@NonNull View view) {
            super(view);
            img_Problem_img_header = view.findViewById(R.id.problem_img_header);
            tvProblem_author = view.findViewById(R.id.problem_author);
            tvProblem_time = view.findViewById(R.id.problem_time);
            tvProblem_title = view.findViewById(R.id.problem_title);
            tvProblem_comment_num = view.findViewById(R.id.problem_comment_num);
            tvProblem_like_num = view.findViewById(R.id.problem_like_num);

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
