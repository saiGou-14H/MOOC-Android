package com.org.moocapp.fragment;

import android.os.Bundle;
import android.view.View;

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
import com.org.moocapp.entity.Response.LearnProgresssResponse;
import com.org.moocapp.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class CourseCatalogueFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private CourseEntity data;
    private List<CourseChapterEntity> courseChapterEntities;
    private CatalogueListAdapter adapter;

    @Override
    protected int initLayout() {
        return R.layout.fragment_course_catalogue;
    }

    @Override
    protected void initView() {
        recyclerView = v.findViewById(R.id.catalogueRecyclerView);
    }

    @Override
    protected void initData() {
        data = (CourseEntity) getActivity().getIntent().getSerializableExtra("data");
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
                        courseChapterEntities = courseChapterResponse.getData();
                        if (courseChapterResponse.getCode()==200&&courseChapterEntities!=null){
                            adapter = new CatalogueListAdapter(context, courseChapterEntities, R.layout.item_catalogue, new OnItemClickListener() {
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
                        }
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}