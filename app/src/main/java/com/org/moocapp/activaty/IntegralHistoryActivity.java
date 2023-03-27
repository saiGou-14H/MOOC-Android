package com.org.moocapp.activaty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.adapter.IntegralHistoryAdpter;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.IntegralHistoryEntity;
import com.org.moocapp.entity.Response.IntegralHistoryResponse;

import java.util.ArrayList;
import java.util.List;

public class IntegralHistoryActivity extends BaseActivity {
    private IntegralHistoryAdpter integralHistoryAdpter;
    private List<IntegralHistoryEntity> integralHistoryEntities = new ArrayList<>();
    private RecyclerView recyclerView;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    integralHistoryAdpter.setDatas(integralHistoryEntities);
                    integralHistoryAdpter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_integral_history;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.IntegralHistoryRecyclerView);
    }

    @Override
    protected void initData() {
        integralHistoryAdpter = new IntegralHistoryAdpter(integralHistoryEntities,mContext);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(integralHistoryAdpter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        shIntegralHistory();
    }

    public void shIntegralHistory(){
        Api.config(ApiConfig.shIntegralHistory,null).getRequest(mContext, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                IntegralHistoryResponse integralHistoryResponse = new Gson().fromJson(res,IntegralHistoryResponse.class);
                if(integralHistoryResponse.getData()!=null&&integralHistoryResponse.getCode()==200){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            integralHistoryEntities=integralHistoryResponse.getData();
                            mHandler.sendEmptyMessage(0);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}