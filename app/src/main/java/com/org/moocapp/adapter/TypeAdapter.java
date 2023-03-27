package com.org.moocapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.org.moocapp.R;
import com.org.moocapp.entity.ClassEntity;
import com.org.moocapp.entity.CourseTypeEntity;
import com.org.moocapp.entity.CoursePracticeEntity;

import java.util.List;

public class TypeAdapter extends BaseAdapter{
    private List<CourseTypeEntity> mCourseTypeList;
    private List<ClassEntity> classEntityList;
    private List<CoursePracticeEntity> coursePracticeEntities;
    private Context mContext;
    private int id;

    public TypeAdapter(Context mContext,int id) {
        this.mContext = mContext;
        this.id = id;
    }

    public void setDataType(List<CourseTypeEntity> mCourseTypeList ){
        this.mCourseTypeList = mCourseTypeList;
    }
    public void setDataClass(List<ClassEntity> classEntityList){
        this.classEntityList = classEntityList;
    }
    public void setDataPractice(List<CoursePracticeEntity> coursePracticeEntities){
        this.coursePracticeEntities = coursePracticeEntities;
    }


    @Override
    public int getCount() {
        if(mCourseTypeList!=null)
        return mCourseTypeList.size();
        else if(classEntityList!=null)return classEntityList.size();
        else return coursePracticeEntities.size();
    }

    @Override
    public Object getItem(int i) {
        if(mCourseTypeList!=null)
            return mCourseTypeList.get(i);
        else if(classEntityList!=null)return classEntityList.get(i);
        else return coursePracticeEntities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = null;
        switch (id){
            case R.layout.item_type:
                v=LayoutInflater.from(mContext).inflate(R.layout.item_type, null);
                ViewHoder viewHoder = new ViewHoder(v);
                viewHoder.textView.setText(mCourseTypeList.get(i).getType());
                break;
            case R.layout.item_class:
                v=LayoutInflater.from(mContext).inflate(R.layout.item_class, null);
                ViewHoder2 viewHoder2 = new ViewHoder2(v);
                viewHoder2.textView.setText(classEntityList.get(i).getName());
                break;
            case R.layout.item_select:
                v=LayoutInflater.from(mContext).inflate(R.layout.item_select, null);
                ViewHoder3 viewHoder3 = new ViewHoder3(v);
                viewHoder3.textView.setText(coursePracticeEntities.get(i).getName());
                break;
        }
        return v;
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            itemView.setTop(10);
            textView = itemView.findViewById(R.id.typetext);
        }
    }

    public class ViewHoder2 extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHoder2(@NonNull View itemView) {
            super(itemView);
            itemView.setTop(10);
            textView = itemView.findViewById(R.id.classtext);
        }
    }
    public class ViewHoder3 extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHoder3(@NonNull View itemView) {
            super(itemView);
            itemView.setTop(10);
            textView = itemView.findViewById(R.id.selecttext);
        }
    }

}
