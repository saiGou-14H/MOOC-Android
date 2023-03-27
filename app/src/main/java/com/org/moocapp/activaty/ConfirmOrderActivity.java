package com.org.moocapp.activaty;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.adapter.ConfirmOederAdapter;
import com.org.moocapp.adapter.MessageAdapter;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.find.MyCourseEntity;
import com.org.moocapp.entity.find.MyCourseListResponse;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfirmOrderActivity extends BaseActivity {
    private Button confirm_order_back;
    private Button confirm_check_box;
    private Button paymentBtn;
    private TextView placeMoney;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ConfirmOederAdapter confirmOederAdapter;
    /**
     * 响应数据
     */
    private int pageNum = 1;
    private List<MyCourseEntity> datas = new ArrayList<>();


    public double total;//    总计
    ArrayList<String> couList;//课程id

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    confirmOederAdapter.setDatas(datas);
                    //通知recyclerView刷新页面
                    confirmOederAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    @Override
    protected int initLayout() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected void initView() {
        confirm_order_back = findViewById(R.id.confirm_order_back);
        confirm_check_box = findViewById(R.id.confirm_check_box);
        placeMoney = findViewById(R.id.placeMoney);
        paymentBtn = findViewById(R.id.paymentBtn);
        ConfirmOrderClickListener myCartClickListener = new ConfirmOrderClickListener();
        confirm_order_back.setOnClickListener(myCartClickListener);
        confirm_check_box.setOnClickListener(myCartClickListener);
        paymentBtn.setOnClickListener(myCartClickListener);


        recyclerView = findViewById(R.id.recyclerView_confirm_order);
//        refreshLayout = findViewById(R.id.refreshLayout_my_cart);
        //布局管理器
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置线性布局
        recyclerView.setLayoutManager(linearLayoutManager);
        confirmOederAdapter = new ConfirmOederAdapter(this);
        /**
         *
         */
        confirmOederAdapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Serializable obj) {
            }
        });
        recyclerView.setAdapter(confirmOederAdapter);

    }

    @Override
    protected void initData() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            couList = bundle.getStringArrayList("couIds");
            total = bundle.getDouble("total");
            System.out.println("couList" + couList);
            System.out.println("total:" + total);
            placeMoney.setText(String.valueOf(total));
            getMyCourseList(couList);
        }
    }

    /**
     * 获取课程列表接口
     */
    private void getMyCourseList(List ids) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        Api.config(ApiConfig.COURSE_LIST, params).postRequest(this, new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                System.out.println(res);
                MyCourseListResponse response = new Gson().fromJson(res, MyCourseListResponse.class);
                if (response != null && response.getCode() == 200) {
                    List<MyCourseEntity> list = response.getData();
                    if (list != null && list.size() > 0) {
                        datas = list;
                        mHandler.sendEmptyMessage(0);
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    /**
     * 下单接口
     */

    private void addOrder(double total, List courseIdList) {
        HashMap<String, Object> params = new HashMap<>();
        Map<String, Object> o = new HashMap<>();
        o.put("id", "");
        o.put("stuId", "");
        o.put("payState", "");
        o.put("createTime", "");
        o.put("payTime", "");
        o.put("remark", "");
        o.put("allIntegral", total);
        List<Object> l = new ArrayList<>();
        //不加价格
        for (int i = 0; i < courseIdList.size(); i++) {
            Map<String, Object> dd = new HashMap<>();
            dd.put("id", "");
            dd.put("courseId", courseIdList.get(i));
            dd.put("orderId", "");
            dd.put("price", "1");
            l.add(dd);
        }
        //加价格
        for (int i = 0; i < datas.size(); i++) {
            Map<String, Object> couS = new HashMap<>();
            couS.put("id", "");
            couS.put("courseId", courseIdList.get(i));
            couS.put("orderId", "");
            couS.put("price", datas.get(i).getIntegral());
//            l.add(couS);
        }
        params.put("order", o);
        params.put("list", l);
        Api.config(ApiConfig.ORDER_ADD, params).postRequest(this, new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                navigateTo(PaySuccessActivity.class, null);
//                navigateToWithFlag(PaySuccessActivity.class,
//                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                showToastSync("下单成功");
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }


    class ConfirmOrderClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.confirm_check_box:
                    confirm_check_box.setSelected(!confirm_check_box.isSelected());
                    break;
                case R.id.paymentBtn:        //结算按钮
                    if (confirm_check_box.isSelected()) {
                        addOrder(total, couList);
                    } else {
                        showToast("请阅读并同意《综合协议》");
                    }

                    break;
                case R.id.confirm_order_back:        //退出页面按钮
                    finish();
                    break;
            }
        }
    }
}