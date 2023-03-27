package com.org.moocapp.activaty;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.adapter.MyOrderAdapter;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.find.MyOrderCourseEntity;
import com.org.moocapp.entity.find.MyOrderCourseListResponse;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyOrderActivity extends BaseActivity {


    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private MyOrderAdapter myOrderAdapter;
    private TextInputEditText my_order_search_EditText;
    private String searchCouName = "";//搜索名称

    private List<MyOrderCourseEntity> datas = new ArrayList<>();
    /**
     * 响应数据
     */
    private int pageNum = 1;
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
                    myOrderAdapter.setDatas(datas);
                    //通知recyclerView刷新页面
                    myOrderAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_my_order;
    }


    @Override
    protected void initView() {
        button = findViewById(R.id.my_order_back);
        my_order_search_EditText = findViewById(R.id.my_order_search);
        my_order_search_EditText.setOnEditorActionListener(new MyOnEditor());


        recyclerView = findViewById(R.id.recyclerView_my_order);
        refreshLayout = findViewById(R.id.refreshLayout_my_order);
        //布局管理器
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置线性布局
        recyclerView.setLayoutManager(linearLayoutManager);

        myOrderAdapter = new MyOrderAdapter(this);
        /**
         *
         */
        myOrderAdapter.setOnItemClickListener(new MyOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Serializable obj) {
            }
        });
        recyclerView.setAdapter(myOrderAdapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                getMyOrderCourseList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNum++;
                getMyOrderCourseList(false);
            }
        });

    }

    @Override
    protected void initData() {
        MyCartClickListener myCartClickListener = new MyCartClickListener();
        button.setOnClickListener(myCartClickListener);
        getMyOrderCourseList(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyOrderCourseList(true);
    }

    /**
     * 获取订单课程列表接口
     */
    private void getMyOrderCourseList(final boolean isRefresh) {
        searchCouName = my_order_search_EditText.getText().toString().trim();
        HashMap<String, Object> params = new HashMap<>();
        params.put("couName", searchCouName);
        params.put("pageNum", pageNum);
        params.put("pageSize", ApiConfig.PAGE_SIZE);
        Api.config(ApiConfig.MY_ORDER_COURSE_LIST, params).getRequest(this, new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                System.out.println(res);
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishLoadMore(true);
                }

                MyOrderCourseListResponse response = new Gson().fromJson(res, MyOrderCourseListResponse.class);
                if (response != null && response.getCode() == 200) {
                    List<MyOrderCourseEntity> list = response.getData();
                    if (list != null && list.size() > 0) {
                        if (isRefresh) {
                            datas = list;
                        } else {
                            datas.addAll(list);
                        }
                        mHandler.sendEmptyMessage(0);
                    } else {
                        if (isRefresh) {
                            showToastSync("空空如也～");
                        } else {
                            showToastSync("已加载全部数据~");
                        }
                    }
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
     * 事件
     */
    class MyOnEditor implements TextInputEditText.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == KeyEvent.ACTION_DOWN) {
                Log.e("TAG", "点击了enter键");
                datas = null;
                getMyOrderCourseList(true);
                mHandler.sendEmptyMessage(0);
            }
            return false;
        }
    }

    /**
     * 事件
     */
    class MyCartClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.my_order_back:        //退出页面按钮
                    finish();
                    break;
            }
        }
    }
}