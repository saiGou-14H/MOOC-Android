package com.org.moocapp.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.org.moocapp.R;
import com.org.moocapp.entity_mysql.MCourseChapter;

import java.util.List;

public class CatalogueListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MCourseChapter>  mCourseChapters;
    private final Context mContext;

    public CatalogueListAdapter(Context mContext,List<MCourseChapter>  mCourseChapters) {
        this.mCourseChapters = mCourseChapters;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(mContext).inflate(R.layout.item_catalogue,null);
        View v = View.inflate(mContext,R.layout.item_catalogue,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.title.setText(mCourseChapters.get(position).getTitle());
        viewHolder.index.setText("第 "+mCourseChapters.get(position).getIndex()+" 章节");
    }

    @Override
    public int getItemCount() {
        return mCourseChapters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView index;
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            index = itemView.findViewById(R.id.catalogueindex);
            title = itemView.findViewById(R.id.cataloguetitle);
        }
    }

}
