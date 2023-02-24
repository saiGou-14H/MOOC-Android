package com.org.moocapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.org.moocapp.R;
import com.org.moocapp.entity_mysql.MStudentCourse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CourseCommentAdapter extends RecyclerView.Adapter {
    private List<MStudentCourse> mStudentCourseList;
    private  final Context context;

    public CourseCommentAdapter(List<MStudentCourse> mStudentCourseList, Context context) {
        this.mStudentCourseList = mStudentCourseList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(context,R.layout.item_course_comment,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.comment.setText(mStudentCourseList.get(position).getComment());
        viewHolder.time.setText("2022-10-30");
    }

    @Override
    public int getItemCount() {
        return mStudentCourseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView header;
        TextView username;
        TextView time;
        TextView comment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            header = itemView.findViewById(R.id.header);
            username = itemView.findViewById(R.id.user_comment_username);
            time = itemView.findViewById(R.id.user_comment_time);
            comment = itemView.findViewById(R.id.user_comment_connent);
        }
    }
}
