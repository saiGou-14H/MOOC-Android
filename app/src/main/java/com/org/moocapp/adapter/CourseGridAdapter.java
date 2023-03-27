package com.org.moocapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.org.moocapp.R;
import com.org.moocapp.entity.CourseEntity;
import com.org.moocapp.entity_mysql.MCourse;
import com.org.moocapp.util.RoundTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CourseGridAdapter extends BaseAdapter {
    private final Context mContext;
    private  List<CourseEntity> courseInfos;

    public CourseGridAdapter(Context mContext, List<CourseEntity> courseInfos) {
        this.mContext = mContext;
        this.courseInfos = courseInfos;
    }
    public CourseGridAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setDatas(List<CourseEntity> courseInfos){
        this.courseInfos = courseInfos;
    }

    @Override
    public int getCount() {
        return courseInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return courseInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView,ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null){
            // 根据布局文件item_list.xml生成转换视图对象
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home_course, null);
            holder = new ViewHolder();
            holder.iv_image = convertView.findViewById(R.id.iv_image);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            // 将视图持有者保存到转换视图当中
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        // 给控制设置好数据
        CourseEntity courseInfo = courseInfos.get(position);
        Picasso.with(mContext).load(courseInfo.getPicture()).transform(new RoundTransform(mContext,10)).into(holder.iv_image);
        holder.tv_name.setText(courseInfo.getName());
        holder.tv_desc.setText(courseInfo.getIntroduction());

        return convertView;
    }
    public final class ViewHolder {
        public ImageView iv_image;
        public TextView tv_name;
        public TextView tv_desc;
    }
}
