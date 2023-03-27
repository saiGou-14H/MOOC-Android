package com.org.moocapp.fragment;

import android.widget.GridView;
import android.widget.TextView;

import com.org.moocapp.R;
import com.org.moocapp.adapter.CourseGridAdapter;
import com.org.moocapp.api.Api;
import com.org.moocapp.entity.CourseEntity;

public class CourseIntroductionFragment extends BaseFragment {

    private GridView gridview;
    private CourseEntity data;
    private TextView coursedetailintroduction;
    @Override
    protected int initLayout() {
        return R.layout.fragment_course_introduction;
    }

    @Override
    protected void initView() {

        gridview = v.findViewById(R.id.coursedetialgridview);
        coursedetailintroduction = v.findViewById(R.id.coursedetailintroduction);
    }

    @Override
    protected void initData() {
        data = (CourseEntity) getActivity().getIntent().getSerializableExtra("data");
        if (data!=null){
            coursedetailintroduction.setText(String.valueOf(data.getIntroduction()));
        }
    }
}