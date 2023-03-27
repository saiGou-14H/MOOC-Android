package com.org.moocapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.activaty.MyCourseDetailActivity;
import com.org.moocapp.adapter.CourseListAdapter;
import com.org.moocapp.adapter.TypeAdapter;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.ClassEntity;
import com.org.moocapp.entity.CourseEntity;
import com.org.moocapp.entity.LearnProgressEntity;
import com.org.moocapp.entity.Response.ClassResponse;
import com.org.moocapp.entity.Response.CourseResponse;
import com.org.moocapp.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MyClassCourseFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private Spinner class_spinner;
    private List<ClassEntity> classEntities = new ArrayList<>();
    private List<CourseEntity> courseEntities = new ArrayList<>();
    private TypeAdapter adapter;
    private CourseListAdapter courseListAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter.setDataClass(classEntities);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    @Override
    protected int initLayout() {
        return R.layout.fragment_my_class_course;
    }

    @Override
    protected void initView() {
        recyclerView = v.findViewById(R.id.myclasscourse);
        class_spinner = v.findViewById(R.id.class_spinner);
    }

    @Override
    protected void initData() {
        class_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ClassEntity courseEntity = (ClassEntity) adapterView.getSelectedItem();
                getCourseEntityList(String.valueOf(courseEntity.getId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getJoinClass();
        adapter = new TypeAdapter(context,R.layout.item_class);
        adapter.setDataClass(classEntities);
        class_spinner.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getJoinClass();
    }

    public void getJoinClass(){
        Api.config(ApiConfig.shClass,null).getRequest(context, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                ClassResponse classResponse = new Gson().fromJson(res,ClassResponse.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       if (classResponse.getData()!=null&&classResponse.getCode()==200){
                           classEntities.clear();
                           for (int i = 0; i < classResponse.getData().size(); i++) {
                               classEntities.add(classResponse.getData().get(i).getClassX());
                           }
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



    public void getCourseEntityList(String classid){
        Api.config(ApiConfig.shCourse+classid,null).getRequest(context, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CourseResponse courseResponse = new Gson().fromJson(res,CourseResponse.class);
                        System.out.println(courseResponse.getCode());
                        if(courseResponse.getCode()==200&&courseResponse.getData()!=null){
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