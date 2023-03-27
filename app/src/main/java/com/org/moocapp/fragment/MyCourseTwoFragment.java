package com.org.moocapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.activaty.CourseVideoActivity;
import com.org.moocapp.adapter.CatalogueListAdapter;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.CourseChapterEntity;
import com.org.moocapp.entity.CourseEntity;
import com.org.moocapp.entity.LearnProgressEntity;
import com.org.moocapp.entity.Response.CourseChapterResponse;
import com.org.moocapp.entity.Response.LearnProgressResponse;
import com.org.moocapp.entity.Response.LearnProgresssResponse;
import com.org.moocapp.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MyCourseTwoFragment extends BaseFragment {
    private List<CourseChapterEntity> courseChapterEntities = new ArrayList<>();
    private RecyclerView recyclerView;
    private CourseEntity data;
    private CatalogueListAdapter adapter;
    private List<LearnProgressEntity> learnProgressEntities = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if(learnProgressEntities!=null&&learnProgressEntities.size()!=0){
                        adapter.setLearnProgressData(learnProgressEntities);
                        adapter.setCourseChapteData(courseChapterEntities);
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };
    @Override
    protected int initLayout() {
        return R.layout.fragment_my_course_two;
    }

    @Override
    protected void initView() {
        recyclerView = v.findViewById(R.id.hava_catalogueRecyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLearnProgress();
    }

    @Override
    protected void initData() {
        data = (CourseEntity) getActivity().getIntent().getSerializableExtra("data");
        adapter = new CatalogueListAdapter(context, courseChapterEntities, R.layout.item_hava_catalogue, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",courseChapterEntities.get(position));
                bundle.putSerializable("course",data);
                //如果登录就跳转
                navigateTo(CourseVideoActivity.class,bundle);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        getCourseChapterList(String.valueOf(data.getId()));
    }

    public void getCourseChapterList(String courseid){
        Api.config(ApiConfig.shCourseChapter+courseid,null).getRequest(context, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CourseChapterResponse courseChapterResponse = new Gson().fromJson(res,CourseChapterResponse.class);
                        if (courseChapterResponse.getCode()==200&&courseChapterResponse.getData()!=null){
                            courseChapterEntities = courseChapterResponse.getData();
                            mHandler.sendEmptyMessage(0);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    public void getLearnProgress(){
        Api.config(ApiConfig.shLearnProgress+data.getId(),null).getRequest(context, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                LearnProgresssResponse learnProgressResponse = new Gson().fromJson(res,LearnProgresssResponse.class);
                if(learnProgressResponse.getCode()==200&&learnProgressResponse.getData()!=null){
                    learnProgressEntities = learnProgressResponse.getData();
                    mHandler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}