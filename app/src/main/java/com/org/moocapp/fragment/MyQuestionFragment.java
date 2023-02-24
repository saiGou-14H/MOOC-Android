package com.org.moocapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.org.moocapp.R;

import com.org.moocapp.activaty.ProblemDetailActivity;
import com.org.moocapp.adapter.MyQuestionAdapter;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;

import com.org.moocapp.entity.find.ProblemEntity;
import com.org.moocapp.entity.find.ProblemListResponse;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MyQuestionFragment extends BaseFragment {

    private String title;
    /**
     * 响应数据
     */
    private int pageNum = 1;
    private List<ProblemEntity> datas = new ArrayList<>();
    /**
     *
     */
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private MyQuestionAdapter myQuestionAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    myQuestionAdapter.setDatas(datas);
                    //通知recyclerView刷新页面
                    myQuestionAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    public MyQuestionFragment() {
    }

    public static MyQuestionFragment newInstance(String title) {
        MyQuestionFragment fragment = new MyQuestionFragment();
        fragment.title = title;
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_my_question;
    }

    @Override
    protected void initView() {
        recyclerView = v.findViewById(R.id.recyclerView_my_question);
        refreshLayout = v.findViewById(R.id.refreshLayout_my_question);
        //布局管理器
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置线性布局
        recyclerView.setLayoutManager(linearLayoutManager);
        //
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                getMyMQuestionList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNum++;
                getMyMQuestionList(false);
            }
        });
        myQuestionAdapter = new MyQuestionAdapter(getActivity());
        recyclerView.setAdapter(myQuestionAdapter);
        /**
         *
         */
        myQuestionAdapter.setOnItemClickListener(new MyQuestionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Serializable obj) {
                ProblemEntity problemEntity = (ProblemEntity) obj;
                Bundle bundle = new Bundle();
                bundle.putLong("questionId", problemEntity.getId());
                bundle.putSerializable("problemEntity", problemEntity);
                navigateTo(ProblemDetailActivity.class, bundle);
            }
        });
    }

    @Override
    protected void initData() {
        getMyMQuestionList(true);

    }


    @Override
    public void onResume() {
        super.onResume();
        getMyMQuestionList(true);
    }

    /**
     * 请求数据接口
     *
     * @param isRefresh
     */

    private void getMyMQuestionList(final boolean isRefresh) {
        Log.d("请求", "getMyMQuestionList");
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", ApiConfig.PAGE_SIZE);
        Api.config(ApiConfig.SEARCH_MY_QUESTION_BY_PAGE, params).getRequest(getActivity(), new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishLoadMore(true);
                }
                ProblemListResponse response = new Gson().fromJson(res, ProblemListResponse.class);
                if (response != null && response.getCode() == 200) {
                    List<ProblemEntity> list = response.getData();
                    if (list != null && list.size() > 0) {
                        if (isRefresh) {
                            datas = list;
                        } else {
                            datas.addAll(list);
                        }
                        mHandler.sendEmptyMessage(0);
                    } else {
                        if (isRefresh) {
                            showToastSync("暂时无数据");
                        } else {
                            showToastSync("没有更多数据");
//                            showToastSync("已加载全部数据~");
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
}