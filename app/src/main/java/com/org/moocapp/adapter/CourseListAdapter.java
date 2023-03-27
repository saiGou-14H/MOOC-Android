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

import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.CourseEntity;
import com.org.moocapp.entity.LearnProgressEntity;
import com.org.moocapp.entity.Response.LearnProgresssResponse;
import com.org.moocapp.entity_mysql.MCourse;
import com.org.moocapp.listener.OnItemChildClickListener;
import com.org.moocapp.listener.OnItemClickListener;
import com.org.moocapp.util.CircleTransform;
import com.org.moocapp.util.RoundTransform;
import com.org.moocapp.util.utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context mContext;
    private List<CourseEntity> courseInfos;
    private final OnItemClickListener mListener;
    private final int resource;

    public CourseListAdapter(Context mContext, List<CourseEntity> courseInfos, OnItemClickListener mListener, int resource) {
        this.mContext = mContext;
        this.courseInfos = courseInfos;
        this.mListener = mListener;
        this.resource = resource;
    }

    public void setDatas(List<CourseEntity> courseInfos){
        this.courseInfos = courseInfos;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setLearnProgressData(){

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
                if (courseInfos.get(position).getPicture()!=""&&courseInfos.get(position).getPicture()!=null){
                    try{
                        Picasso.with(mContext).load(courseInfos.get(position).getPicture()).transform(new RoundTransform(mContext,40)).into(viewHoder.image);
                    }catch (Exception e){
                        System.out.println(courseInfos.get(position).getPicture());
                    }
                }
                viewHoder.teachername.setText(courseInfos.get(position).getIntroduction());
                viewHoder.name.setText(courseInfos.get(position).getName());
                viewHoder.btn.setText(String.valueOf(courseInfos.get(position).getIntegral()));
                viewHoder.time.setText(String.format("%.1f", new Double(courseInfos.get(position).getTime()/3600.0))+"课时");
                break;
            }
            case R.layout.item_my_course:{
                ViewHoder2 viewHoder = (ViewHoder2) holder;
                if(courseInfos.get(position).getPicture()!=null){
                    Picasso.with(mContext).load(courseInfos.get(position).getPicture()).transform(new RoundTransform(mContext,40)).into(viewHoder.image);
//                viewHoder.image.setImageResource();

                }
                viewHoder.teachername.setText("授课老师："+courseInfos.get(position).getUsername());
                viewHoder.name.setText(courseInfos.get(position).getName());
                if(courseInfos.get(position).getTime()!=null){
                    viewHoder.mycourseTime.setText(String.format("%.1f", new Double(courseInfos.get(position).getTime()/3600.0))+"课时");
                }else{
                    viewHoder.mycourseTime.setText(String.format("%.1f", new Double(0/3600.0))+"课时");
                }
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
        TextView time;
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
            time = itemView.findViewById(R.id.courseTime);
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
        TextView mycourseTime;

        OnItemClickListener mListener;
        OnItemChildClickListener mLongListener;

        public ViewHoder2(@NonNull View itemView,OnItemClickListener Listener) {
            super(itemView);
            this.mListener = Listener;
            image = itemView.findViewById(R.id.mycourseimage);
            teachername = itemView.findViewById(R.id.mycourseteachername);
            name = itemView.findViewById(R.id.mycoursename);
            mycourseTime = itemView.findViewById(R.id.mycourseTime);
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
