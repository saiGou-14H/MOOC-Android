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
import com.org.moocapp.entity.find.MyOrderCourseEntity;
import com.org.moocapp.util.CircleTransform;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<MyOrderCourseEntity> datas;


    public void setDatas(List<MyOrderCourseEntity> datas) {
        this.datas = datas;
    }

    /**
     * 点击事件
     */
    private static OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public MyOrderAdapter(Context context) {
        this.mContext = context;
    }

    public MyOrderAdapter(Context context, List<MyOrderCourseEntity> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_order, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        MyOrderCourseEntity myOrderCourseEntity = datas.get(position);
        vh.myOrderCourseEntity = myOrderCourseEntity;
        vh.my_order_course_name.setText(String.valueOf(myOrderCourseEntity.getName()));
        Picasso.with(mContext)
                .load(myOrderCourseEntity.getPicture())
                .into(vh.my_order_img_course_pic);
        vh.my_order_price.setText(String.valueOf(myOrderCourseEntity.getIntegral()));
        vh.my_order_time.setText(String.valueOf(myOrderCourseEntity.getCreateTime()));
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
        private TextView my_order_course_name;
        private ImageView my_order_img_course_pic;
        private TextView my_order_price;
        private TextView my_order_time;
        private MyOrderCourseEntity myOrderCourseEntity;

        public ViewHolder(@NonNull View view) {
            super(view);
            my_order_course_name = view.findViewById(R.id.my_order_course_name);
            my_order_img_course_pic = view.findViewById(R.id.my_order_img_course_pic);
            my_order_price = view.findViewById(R.id.my_order_price);
            my_order_time = view.findViewById(R.id.my_order_time);
            /**
             *
             */
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(myOrderCourseEntity);
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Serializable obj);
    }
}
