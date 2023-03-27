package com.org.moocapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.org.moocapp.R;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.find.MyCartEntity;
import com.org.moocapp.util.CircleTransform;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<MyCartEntity> datas;


    public void setDatas(List<MyCartEntity> datas) {
        this.datas = datas;
    }

    /**
     * 点击事件
     */
    private static OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private static OnDeleteClickListener monDeleteClickListener;

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        monDeleteClickListener = onDeleteClickListener;
    }

    public MyCartAdapter(Context context) {
        this.mContext = context;
    }

    public MyCartAdapter(Context context, List<MyCartEntity> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_cart, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        MyCartEntity myCartEntity = datas.get(position);
        vh.myCartEntity = myCartEntity;
        vh.tvMy_cart_course_name.setText(String.valueOf(myCartEntity.getName()));
        Picasso.with(mContext)
                .load(myCartEntity.getPicture())
                .into(vh.tvMy_cart_img_course_pic);
        vh.tvMy_cart_price.setText(String.valueOf(myCartEntity.getIntegral()));
        vh.flag.setChecked(myCartEntity.isFlag());
        vh.flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCartEntity myCartEntity1 = datas.get(position);
                myCartEntity1.setFlag(!myCartEntity1.isFlag());
                //刷新条目显示
                notifyItemChanged(position);
                mOnItemClickListener.onItemClick(myCartEntity);

            }
        });
        vh.tv_deleteById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCartEntity myCartEntity = datas.get(position);
                notifyDataSetChanged();
                deleteMyCartById(myCartEntity.getCouId());
                datas.remove(position);
                monDeleteClickListener.onDeleteClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (datas != null && datas.size() > 0) {
            return datas.size();
        } else {
            return 0;
        }
    }

    /**
     * 删除购物车接口
     */
    private void deleteMyCartById(Long couId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("couId", couId);
        Api.config(ApiConfig.CART_DELETE, params).getRequest(mContext, new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                Log.e("删除成功", "");


            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMy_cart_course_name;
        private ImageView tvMy_cart_img_course_pic;
        private TextView tvMy_cart_price;
        private CheckBox flag;
        private TextView tv_deleteById;
        private MyCartEntity myCartEntity;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvMy_cart_course_name = view.findViewById(R.id.my_cart_course_name);
            tvMy_cart_img_course_pic = view.findViewById(R.id.my_cart_img_course_pic);
            tvMy_cart_price = view.findViewById(R.id.my_cart_price);
            flag = view.findViewById(R.id.check_box);
            tv_deleteById = view.findViewById(R.id.tv_deleteById);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(myCartEntity);
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Serializable obj);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick();
    }
}
