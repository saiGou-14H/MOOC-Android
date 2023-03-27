package com.org.moocapp.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.org.moocapp.R;
import com.org.moocapp.entity.CourseChapterEntity;
import com.org.moocapp.entity.LearnProgressEntity;
import com.org.moocapp.listener.OnItemClickListener;
import com.org.moocapp.util.utils;

import java.util.ArrayList;
import java.util.List;

public class CatalogueListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CourseChapterEntity> mCourseChapters;
    private final Context mContext;
    private final int resource;
    private List<LearnProgressEntity> learnProgressEntities ;

    private final OnItemClickListener mListener;

    public CatalogueListAdapter(Context mContext, List<CourseChapterEntity> mCourseChapters, int resource, OnItemClickListener mListener) {
        this.mCourseChapters = mCourseChapters;
        this.mContext = mContext;
        this.resource = resource;
        this.mListener = mListener;
    }
    public void setLearnProgressData(List<LearnProgressEntity> mlearnProgressEntities){
        this.learnProgressEntities = mlearnProgressEntities;
    }
    public void setCourseChapteData(List<CourseChapterEntity> mCourseChapters){
        this.mCourseChapters = mCourseChapters;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(mContext).inflate(R.layout.item_catalogue,null);

        switch (resource) {
            case R.layout.item_catalogue: {
                View v = View.inflate(mContext, R.layout.item_catalogue, null);
                return new ViewHolder(v, mListener);
            }
            case R.layout.item_hava_catalogue: {
                View v = View.inflate(mContext, R.layout.item_hava_catalogue, null);
                return new ViewHolder2(v, mListener);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (resource) {
            case R.layout.item_catalogue: {
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.title.setText(mCourseChapters.get(position).getContent());
                viewHolder.index.setText("第 " + mCourseChapters.get(position).getChaIndex() + " 章节");
                break;
            }
            case R.layout.item_hava_catalogue: {
                ViewHolder2 viewHolder = (ViewHolder2) holder;
                viewHolder.title.setText(mCourseChapters.get(position).getContent());
                viewHolder.index.setText("第 " + mCourseChapters.get(position).getChaIndex() + " 章节");

                if(learnProgressEntities!=null){
                    for (int i = 0; i < learnProgressEntities.size(); i++) {
                        if(learnProgressEntities.get(i).getChaId()==mCourseChapters.get(position).getId()){

                            System.out.println(String.valueOf(learnProgressEntities.get(i).getReadTime()/1000));
                            System.out.println(mCourseChapters.get(position).getTime());
//                            String str = utils.getRatio(new Double(learnProgressEntities.get(i).getReadTime()/1000),new Double(mCourseChapters.get(position).getTime()));
//                            Double x = Double.valueOf(str.replace("%",""));
//                            viewHolder.progressBar.setProgress(Integer.valueOf((int) (x*100/100)));
//                            viewHolder.progress.setText(str);
                            viewHolder.progressBar.setProgress((int) (learnProgressEntities.get(i).getProgress()*100));
                            viewHolder.progress.setText(String.format("%.1f",(double) (learnProgressEntities.get(i).getProgress()*100.0))+"%");

                        }
                    }
                }
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mCourseChapters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView index;
        TextView title;
        OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener mListener) {
            super(itemView);
            index = itemView.findViewById(R.id.catalogueindex);
            title = itemView.findViewById(R.id.cataloguetitle);
            onItemClickListener = mListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view, getPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView index;
        TextView title;
        ProgressBar progressBar;
        TextView progress;
        OnItemClickListener onItemClickListener;

        public ViewHolder2(@NonNull View itemView, OnItemClickListener mListener) {
            super(itemView);
            index = itemView.findViewById(R.id.have_catalogueindex);
            title = itemView.findViewById(R.id.have_cataloguetitle);
            progressBar = itemView.findViewById(R.id.have_coursedetialprogressbar);
            progress = itemView.findViewById(R.id.have_coursedetialprogress);
            onItemClickListener = mListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view, getPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }

}
