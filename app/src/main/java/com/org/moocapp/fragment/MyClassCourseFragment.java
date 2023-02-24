package com.org.moocapp.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.org.moocapp.R;
import com.org.moocapp.activaty.CourseDetailActivity;
import com.org.moocapp.adapter.CourseListAdapter;
import com.org.moocapp.api1.api;
import com.org.moocapp.listener.OnItemClickListener;

public class MyClassCourseFragment extends BaseFragment {
    private RecyclerView recyclerView;

    @Override
    protected int initLayout() {
        return R.layout.fragment_my_class_course;
    }

    @Override
    protected void initView() {
        recyclerView = v.findViewById(R.id.myclasscourse);
    }

    @Override
    protected void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        CourseListAdapter courseListAdapter = new CourseListAdapter(context, api.getCourseList(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                navigateTo(CourseDetailActivity.class,null);
            }
        }, R.layout.item_my_course);
        recyclerView.setAdapter(courseListAdapter);
    }
}