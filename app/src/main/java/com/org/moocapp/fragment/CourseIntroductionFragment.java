package com.org.moocapp.fragment;

import android.widget.GridView;

import com.org.moocapp.R;
import com.org.moocapp.adapter.CourseGridAdapter;
import com.org.moocapp.api1.api;

public class CourseIntroductionFragment extends BaseFragment {

    private GridView gridview;

    @Override
    protected int initLayout() {
        return R.layout.fragment_course_introduction;
    }

    @Override
    protected void initView() {

        gridview = v.findViewById(R.id.coursedetialgridview);
    }

    @Override
    protected void initData() {
        //模拟网络获取数据api.getCourseList()
        CourseGridAdapter courseGridAdapter = new CourseGridAdapter(context, api.getCourseList());
        gridview.setAdapter(courseGridAdapter);
    }
}