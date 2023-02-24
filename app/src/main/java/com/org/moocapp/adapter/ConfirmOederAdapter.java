package com.org.moocapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.org.moocapp.R;
import com.org.moocapp.entity.find.MyCourseEntity;
import com.org.moocapp.util.CircleTransform;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class ConfirmOederAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<MyCourseEntity> datas;


    public void setDatas(List<MyCourseEntity> datas) {
        this.datas = datas;
    }

    /**
     * 点击事件
     */
    private static MessageAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(MessageAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public ConfirmOederAdapter(Context context) {
        this.mContext = context;
    }

    public ConfirmOederAdapter(Context context, List<MyCourseEntity> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_confirm_order, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        MyCourseEntity myCourseEntity = datas.get(position);
        vh.myCourseEntity = myCourseEntity;
        vh.confirm_order_course_name.setText(String.valueOf(myCourseEntity.getName()));
        Picasso.with(mContext)
                .load(myCourseEntity.getPicture())
                .transform(new CircleTransform())
                .into(vh.confirm_order_img_course_pic);
        vh.confirm_order_price.setText(String.valueOf(myCourseEntity.getIntegral()));
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
        private TextView confirm_order_course_name;
        private ImageView confirm_order_img_course_pic;
        private TextView confirm_order_price;


        private MyCourseEntity myCourseEntity;

        public ViewHolder(@NonNull View view) {
            super(view);
            confirm_order_course_name = view.findViewById(R.id.confirm_order_course_name);
            confirm_order_price = view.findViewById(R.id.confirm_order_price);
            confirm_order_img_course_pic = view.findViewById(R.id.confirm_order_img_course_pic);


            /**
             *
             */
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(myCourseEntity);
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Serializable obj);
    }
}
