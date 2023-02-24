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
import com.org.moocapp.adapter.MyAnswerAdapter;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.find.MyMQuestionCommentEntity;
import com.org.moocapp.entity.find.MyMQuestionCommentListResponse;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyAnswerFragment extends BaseFragment {

    private String title;
    /**
     * 响应数据
     */
    private int pageNum = 1;
    private List<MyMQuestionCommentEntity> datas = new ArrayList<>();
    /**
     *
     */
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private MyAnswerAdapter myAnswerAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    myAnswerAdapter.setDatas(datas);
                    //通知recyclerView刷新页面
                    myAnswerAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    public MyAnswerFragment() {
    }

    public static MyAnswerFragment newInstance(String title) {
        MyAnswerFragment fragment = new MyAnswerFragment();
        fragment.title = title;

        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_my_answer;
    }

    @Override
    protected void initView() {
        recyclerView = v.findViewById(R.id.recyclerView_my_answer);
        refreshLayout = v.findViewById(R.id.refreshLayout_my_answer);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                getMyMQuestionCommentList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNum++;
                getMyMQuestionCommentList(false);
            }
        });
        myAnswerAdapter = new MyAnswerAdapter(getActivity());
        recyclerView.setAdapter(myAnswerAdapter);
        myAnswerAdapter.setOnItemClickListener(new MyAnswerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Serializable obj) {
                MyMQuestionCommentEntity myMQuestionCommentEntity = (MyMQuestionCommentEntity) obj;
                Bundle bundle = new Bundle();
                bundle.putLong("questionId", myMQuestionCommentEntity.getQuestionId());
                navigateTo(ProblemDetailActivity.class, bundle);
            }
        });
    }

    @Override
    protected void initData() {
        getMyMQuestionCommentList(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMyMQuestionCommentList(true);
    }

    /**
     * 请求数据接口
     *
     * @param isRefresh
     */

    private void getMyMQuestionCommentList(final boolean isRefresh) {
        Log.d("请求", "getMyMQuestionCommentList");
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", ApiConfig.PAGE_SIZE);
        Api.config(ApiConfig.SEARCH_MY_QUESTION_COMMENT_BY_PAGE, params).getRequest(getActivity(), new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishLoadMore(true);
                }
                MyMQuestionCommentListResponse response = new Gson().fromJson(res, MyMQuestionCommentListResponse.class);
                if (response != null && response.getCode() == 200) {
                    List<MyMQuestionCommentEntity> list = response.getData();
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