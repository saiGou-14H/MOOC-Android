package com.org.moocapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.org.moocapp.R;
import com.org.moocapp.entity.IntegralHistoryEntity;

import java.util.List;

public class IntegralHistoryAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<IntegralHistoryEntity> integralHistoryEntities;
    private final Context mContext;

    public IntegralHistoryAdpter(List<IntegralHistoryEntity> integralHistoryEntities, Context mContext) {
        this.integralHistoryEntities = integralHistoryEntities;
        this.mContext = mContext;
    }

    public void setDatas(List<IntegralHistoryEntity> integralHistoryEntities){
        this.integralHistoryEntities = integralHistoryEntities;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(mContext,R.layout.item_integralhistory,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        String type;
        if(integralHistoryEntities.get(position).isType()){
            type="+"+integralHistoryEntities.get(position).getIntegralchild();
        }else{
            type="-"+integralHistoryEntities.get(position).getIntegralchild();
        }
        viewHolder.type.setText(type);
        viewHolder.origin.setText(integralHistoryEntities.get(position).getOrigin());
        viewHolder.time.setText(integralHistoryEntities.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return integralHistoryEntities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        TextView origin;
        TextView type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.integralhistorytime);
            origin = itemView.findViewById(R.id.integralhistoryorigin);
            type = itemView.findViewById(R.id.integralhistorytype);
        }
    }
}
