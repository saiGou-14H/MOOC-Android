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
import com.org.moocapp.entity_mysql.MCourseType;

import java.util.List;

public class TypeAdapter extends BaseAdapter {
    private List<MCourseType> mCourseTypeList;
    private Context mContext;

    public TypeAdapter(List<MCourseType> mCourseTypeList, Context mContext) {
        this.mCourseTypeList = mCourseTypeList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mCourseTypeList.size();
    }

    @Override
    public Object getItem(int i) {
        return mCourseTypeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_type, null);
        ViewHoder viewHoder = new ViewHoder(v);
        viewHoder.textView.setText(mCourseTypeList.get(i).getType());
        return v;
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            itemView.setTop(10);
            textView = itemView.findViewById(R.id.typetext);
        }
    }

}
