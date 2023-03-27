package com.org.moocapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.activaty.ProblemDetailActivity;
import com.org.moocapp.adapter.ProblemAdapter;
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


public class ProblemFragment extends BaseFragment {
    /**
     * 响应数据
     */
    private int pageNum = 1;
    private List<ProblemEntity> datas = new ArrayList<>();

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private ProblemAdapter problemAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    problemAdapter.setDatas(datas);
                    //通知recyclerView刷新页面
                    problemAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    public ProblemFragment() {
    }

    public static ProblemFragment newInstance() {
        ProblemFragment fragment = new ProblemFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_problem, container, false);
        //找到
        recyclerView = view.findViewById(R.id.recyclerView_problem);
        refreshLayout = view.findViewById(R.id.refreshLayout_problem);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
//                Log.d("///", "刷新了");
//                refreshLayout.finishRefresh(true);
                getProblemList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNum++;
//                Log.d("///", "加载了");
//                refreshLayout.finishLoadMore(true);
                getProblemList(false);
            }
        });
        problemAdapter = new ProblemAdapter(getActivity());
        recyclerView.setAdapter(problemAdapter);
        /**
         *
         */
        problemAdapter.setOnItemClickListener(new ProblemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Serializable obj) {
                ProblemEntity problemEntity = (ProblemEntity) obj;
                Bundle bundle = new Bundle();
                bundle.putLong("questionId", problemEntity.getId());
                bundle.putSerializable("problemEntity", problemEntity);
                navigateTo(ProblemDetailActivity.class, bundle);
            }
        });
        getProblemList(true);
//        for (int i = 0; i < 8; i++) {
//            ProblemEntity problemEntity = new ProblemEntity();
//            problemEntity.setTitle("标题-" + i);
//            problemEntity.setContent("内容-" + i);
//            problemEntity.setUsername("作者-" + i);
//            problemEntity.setHeadPic("https://profile-avatar.csdnimg.cn/eabd69cac9064aa89f4d55b70acaf132_lgz0921.jpg");
//            datas.add(problemEntity);
//        }
//        problemAdapter.setDatas(datas);
//        problemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        getProblemList(true);
    }

    /**
     * 请求数据接口
     *
     * @param isRefresh
     */

    private void getProblemList(final boolean isRefresh) {
        Log.e("请求", "getProblemList");
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", 10);
//        params.put("categoryId", categoryId);


        Api.config(ApiConfig.PROBLEM_LIST_ALL, params).getRequest(getActivity(), new TtitCallback() {
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