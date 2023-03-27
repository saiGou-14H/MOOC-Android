package com.org.moocapp.fragment;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.activaty.MyCourseDetailActivity;
import com.org.moocapp.adapter.CourseListAdapter;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.CourseEntity;
import com.org.moocapp.entity.Response.CourseResponse;
import com.org.moocapp.listener.OnItemClickListener;

import java.util.List;

public class MyCourseFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private List<CourseEntity> courseEntities;
    private CourseListAdapter courseListAdapter;
    @Override
    protected int initLayout() {
        return R.layout.fragment_my_course;
    }

    @Override
    protected void initView() {
        recyclerView = v.findViewById(R.id.mycourseview);
    }

    @Override
    protected void initData() {
        getCourseEntityList();
    }
    @Override
    public void onResume() {
        super.onResume();
        getCourseEntityList();
    }

    public void getCourseEntityList(){
        Api.config(ApiConfig.shHaveCourse,null).getRequest(context, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CourseResponse courseResponse = new Gson().fromJson(res,CourseResponse.class);
                        System.out.println(res);
                        if(courseResponse.getData()!=null&&courseResponse.getCode()==200){
                            courseEntities = courseResponse.getData();
                            courseListAdapter = new CourseListAdapter(context, courseEntities, new OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("data",courseEntities.get(position));
                                    navigateTo(MyCourseDetailActivity.class,bundle);
                                }
                            }, R.layout.item_my_course);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView.setAdapter(courseListAdapter);
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