package com.org.moocapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.activaty.MessageDetailActivity;
import com.org.moocapp.adapter.MessageAdapter;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.find.MessageEntity;
import com.org.moocapp.entity.find.MessageListResponse;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MessageFragment extends BaseFragment {


    private String title;
    /**
     * 响应数据
     */
    private int pageNum = 1;
    private List<MessageEntity> datas = new ArrayList<>();
    /**
     *
     */
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    messageAdapter.setDatas(datas);
                    //通知recyclerView刷新页面
                    messageAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    public MessageFragment() {
    }

    public static MessageFragment newInstance(String title) {
        MessageFragment fragment = new MessageFragment();
        fragment.title = title;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //初始化布局
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        //找到
        recyclerView = view.findViewById(R.id.recyclerView_message);
        refreshLayout = view.findViewById(R.id.refreshLayout_message);
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
                datas = null;
                pageNum = 1;
                getMessageList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNum++;
                getMessageList(false);
            }
        });
        //方法一
//        getVideoList(true);
//        VideoAdapter videoAdapter = new VideoAdapter(getActivity(), datas);
//        recyclerView.setAdapter(videoAdapter);
        //方法二
        messageAdapter = new MessageAdapter(getActivity());
        recyclerView.setAdapter(messageAdapter);
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setAuthor("qqqqq");
        messageEntity.setTitle("ttttt");
        messageEntity.setContent("111111111");
        datas.add(messageEntity);
        messageAdapter.setDatas(datas);
        messageAdapter.notifyDataSetChanged();

        /**
         *
         */
        messageAdapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Serializable obj) {
                MessageEntity messageEntity = (MessageEntity) obj;
                Bundle bundle = new Bundle();
                bundle.putSerializable("messageEntity", messageEntity);
                navigateTo(MessageDetailActivity.class, bundle);
            }
        });
        getMessageList(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMessageList(true);
    }

    /**
     * 请求数据接口
     *
     * @param isRefresh
     */

    private void getMessageList(final boolean isRefresh) {
        Log.d("请求", "getVideoList");
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", 10);
        Api.config(ApiConfig.Message_LIST_ALL, params).getRequest(getActivity(), new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishLoadMore(true);
                }
                MessageListResponse response = new Gson().fromJson(res, MessageListResponse.class);
                if (response != null && response.getCode() == 200) {
                    List<MessageEntity> list = response.getData();
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
//                            showToastSync("没有更多数据");
                            showToastSync("已加载全部数据~");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                mHandler.sendEmptyMessage(0);
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishLoadMore(true);
                }
            }
        });
    }

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
}