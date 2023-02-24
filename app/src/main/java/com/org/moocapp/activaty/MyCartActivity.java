package com.org.moocapp.activaty;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.adapter.MyCartAdapter;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.find.MyCartEntity;
import com.org.moocapp.entity.find.MyCartListResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MyCartActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private MyCartAdapter myCartAdapter;
    private CheckBox all_checkbox;//全选
    private Button placeDeleteBtn;//删除
    private Button placeBuyBtn;//下单
    public TextView tv_total_price;//价格文本
    private LinearLayout cart_isEmpty;
    private SmartRefreshLayout refreshLayout_my_cart;
    /**
     * 响应数据
     */
    private int pageNum = 1;
    private List<MyCartEntity> list = new ArrayList<>();
    private List<MyCartEntity> datas = new ArrayList<>();
    private Button button;

    public int nums;
    public Double prices;
    public double total;//    总计
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    myCartAdapter.setDatas(datas);
                    //通知recyclerView刷新页面
                    myCartAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_my_cart;
    }


    @Override
    protected void initView() {
        button = findViewById(R.id.my_cart_back);
        all_checkbox = findViewById(R.id.all_checkbox);
        placeDeleteBtn = findViewById(R.id.placeDeleteBtn);
        placeBuyBtn = findViewById(R.id.placeBuyBtn);
        tv_total_price = findViewById(R.id.tv_total_price);
        cart_isEmpty = findViewById(R.id.cart_isEmpty);
        refreshLayout_my_cart = findViewById(R.id.refreshLayout_my_cart);


        recyclerView = findViewById(R.id.recyclerView_my_cart);
        refreshLayout = findViewById(R.id.refreshLayout_my_cart);
        //布局管理器
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置线性布局
        recyclerView.setLayoutManager(linearLayoutManager);

        myCartAdapter = new MyCartAdapter(this);
        /**
         *
         */
        myCartAdapter.setOnItemClickListener(new MyCartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Serializable obj) {
                getTotalPrice();
            }
        });
        myCartAdapter.setOnDeleteClickListener(new MyCartAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick() {
                getMyCartList(true);
            }
        });
        recyclerView.setAdapter(myCartAdapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
//                refreshLayout.finishRefresh(true);
                getMyCartList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNum++;
//                refreshLayout.finishLoadMore(true);
                getMyCartList(false);
            }
        });
        getMyCartList(true);

    }

    @Override
    protected void initData() {
        MyCartClickListener myCartClickListener = new MyCartClickListener();
        button.setOnClickListener(myCartClickListener);
        all_checkbox.setOnClickListener(myCartClickListener);
        placeDeleteBtn.setOnClickListener(myCartClickListener);
        placeBuyBtn.setOnClickListener(myCartClickListener);
        tv_total_price.setOnClickListener(myCartClickListener);
        getTotalPrice();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyCartList(true);
        getTotalPrice();
    }


    /**
     * 获取购物车接口
     */
    private void getMyCartList(final boolean isRefresh) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", ApiConfig.PAGE_SIZE);
        Api.config(ApiConfig.CART_LIST_ALL, params).getRequest(this, new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishLoadMore(true);
                }
                MyCartListResponse response = new Gson().fromJson(res, MyCartListResponse.class);
                if (response != null && response.getCode() == 200) {
                    list = response.getData();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list != null && list.size() > 0) {
                                refreshLayout_my_cart.setVisibility(View.VISIBLE);
                                cart_isEmpty.setVisibility(View.GONE);
                                if (isRefresh) {
                                    datas = list;
                                } else {
                                    datas.addAll(list);
                                }
                                mHandler.sendEmptyMessage(0);
                            } else {
                                if (isRefresh) {
//                            showToastSync("购物车空空如也～");
                                    refreshLayout_my_cart.setVisibility(View.GONE);
                                    cart_isEmpty.setVisibility(View.VISIBLE);
                                } else {
                                    showToastSync("已加载全部数据~");
                                }
                            }
                        }
                    });

                }
            }

            @Override
            public void onFailure(Exception e) {
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishLoadMore(true);
                }
            }
        });
    }


    /**
     * 遍历当前选中的商品 累加价格
     */
    private void getTotalPrice() {
        int number = 0;
        total = 0;
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                MyCartEntity myCartEntity = datas.get(i);
                if (myCartEntity.isFlag()) {
                    Integer price = myCartEntity.getIntegral();
                    prices = Double.valueOf(price);
                    total += prices;
                    //记录当前选中的条目
                    number++;
                } else {
                    //有一个未选中都设置全选为未选中
                    all_checkbox.setChecked(false);
                }
            }
//            tv_total_price.setText("￥" + total);
            tv_total_price.setText("" + total);
        } else {
            all_checkbox.setChecked(false);
        }
        //如果当前选中条目为总大小，则设置全选为选中
        if (number == datas.size() && datas.size() != 0) {
            all_checkbox.setChecked(true);
        }

    }

    /**
     * //全选按钮监听
     */
    private void all_checkboxClick() {
        if (all_checkbox.isChecked()) {
            Log.e("isChecked", "true");
            for (int i = 0; i < datas.size(); i++) {
                MyCartEntity myCartEntity = datas.get(i);
                myCartEntity.setFlag(true);//datas.get(i).setFlag(true);
            }
        } else {
            Log.e("isChecked", "false");
            for (int i = 0; i < datas.size(); i++) {
                MyCartEntity myCartEntity = datas.get(i);
                myCartEntity.setFlag(false);// datas.get(i).setFlag(false);
            }
        }
        myCartAdapter.notifyDataSetChanged();
        getTotalPrice();
    }


    class MyCartClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.all_checkbox:    //全选或者全不选的点击事件
                    Log.e("isChecked", String.valueOf(all_checkbox.isChecked()));
                    all_checkboxClick();
                    break;
                case R.id.placeDeleteBtn:    //删除的点击事件
                    Log.e("placeDeleteBtn", "placeDeleteBtn");
                    break;
                case R.id.placeBuyBtn:    //去结算的点击事件
                    boolean isSelectCart = false;
                    ArrayList<MyCartEntity> list = new ArrayList<>();
                    ArrayList<String> couIds = new ArrayList<String>();
                    for (int i = 0; i < datas.size(); i++) {
                        MyCartEntity myCartEntity = datas.get(i);
                        if (myCartEntity.isFlag()) {
                            list.add(myCartEntity);
                            couIds.add(String.valueOf(myCartEntity.getCouId()));
                            isSelectCart = true;
                        }
                    }
                    if (isSelectCart) {
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("couIds", couIds);
                        bundle.putDouble("total", total);
                        navigateTo(ConfirmOrderActivity.class, bundle);
                    } else {
                        showToast("你还没有选择课程哦");
                    }
                    break;

                case R.id.my_cart_back:        //退出页面按钮
                    finish();
                    break;
            }
        }
    }
}