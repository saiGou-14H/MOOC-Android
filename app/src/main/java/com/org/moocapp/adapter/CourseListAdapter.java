package com.org.moocapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.org.moocapp.R;
import com.org.moocapp.entity_mysql.MCourse;
import com.org.moocapp.listener.OnItemChildClickListener;
import com.org.moocapp.listener.OnItemClickListener;
import com.org.moocapp.util.CircleTransform;
import com.org.moocapp.util.RoundTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CourseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context mContext;
    private final ArrayList<MCourse> courseInfos;
    private final OnItemClickListener mListener;
    private final int resource;

    public CourseListAdapter(Context mContext, ArrayList<MCourse> courseInfos, OnItemClickListener mListener, int resource) {
        this.mContext = mContext;
        this.courseInfos = courseInfos;
        this.mListener = mListener;
        this.resource = resource;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        switch (resource){
            case R.layout.item_sellect_course:{
                v=LayoutInflater.from(mContext).inflate(R.layout.item_sellect_course, null);
                return new ViewHoder(v,mListener);
            }
            case R.layout.item_my_course:{
                v=LayoutInflater.from(mContext).inflate(R.layout.item_my_course, null);
                return new ViewHoder2(v,mListener);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (resource){
            case R.layout.item_sellect_course:{
                ViewHoder viewHoder = (ViewHoder) holder;
                Picasso.with(mContext).load(courseInfos.get(position).getImage()).transform(new RoundTransform(mContext)).into(viewHoder.image);
                viewHoder.teachername.setText(courseInfos.get(position).getName());
                viewHoder.name.setText(courseInfos.get(position).getName());
                break;
            }
            case R.layout.item_my_course:{
                ViewHoder2 viewHoder = (ViewHoder2) holder;
                Picasso.with(mContext).load(courseInfos.get(position).getImage()).transform(new RoundTransform(mContext)).into(viewHoder.image);
//                viewHoder.image.setImageResource();
                viewHoder.teachername.setText(courseInfos.get(position).getName());
                viewHoder.name.setText(courseInfos.get(position).getName());
                viewHoder.progress.setText("75%");
                viewHoder.progressBar.setProgress(75);
                break;
            }
        }
    }


    @Override
    public int getItemCount() {
        return courseInfos.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView image;
        TextView name;
        TextView teachername;
        LinearLayout sellect_course;
        Button btn;

        OnItemClickListener mListener;
        OnItemChildClickListener mLongListener;

        public ViewHoder(@NonNull View itemView,OnItemClickListener Listener) {
            super(itemView);
            this.mListener = Listener;
            image = itemView.findViewById(R.id.select_course_image);
            teachername = itemView.findViewById(R.id.select_course_teachername);
            btn = itemView.findViewById(R.id.select_course_btn);
            name = itemView.findViewById(R.id.select_course_name);
            sellect_course = itemView.findViewById(R.id.sellect_course);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if(mListener != null){
                mListener.onItemClick(view,getPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (mLongListener != null) {
                mLongListener.onItemChildClick(view, getPosition());
            }
            return true;
        }
    }

    public class ViewHoder2 extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView image;
        TextView name;
        TextView teachername;
        ProgressBar progressBar;
        TextView progress;

        OnItemClickListener mListener;
        OnItemChildClickListener mLongListener;

        public ViewHoder2(@NonNull View itemView,OnItemClickListener Listener) {
            super(itemView);
            this.mListener = Listener;
            image = itemView.findViewById(R.id.mycourseimage);
            teachername = itemView.findViewById(R.id.mycourseteachername);
            name = itemView.findViewById(R.id.mycoursename);
            progressBar = itemView.findViewById(R.id.mycourseprogressbar);
            progress = itemView.findViewById(R.id.mycourseprogress);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if(mListener != null){
                mListener.onItemClick(view,getPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (mLongListener != null) {
                mLongListener.onItemChildClick(view, getPosition());
            }
            return true;
        }
    }

}
