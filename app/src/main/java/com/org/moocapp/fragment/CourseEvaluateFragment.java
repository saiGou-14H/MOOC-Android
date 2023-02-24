package com.org.moocapp.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.org.moocapp.R;
import com.org.moocapp.adapter.CourseCommentAdapter;
import com.org.moocapp.api1.api;

public class CourseEvaluateFragment extends BaseFragment {
    private RecyclerView commentview;

    @Override
    protected int initLayout() {
        return R.layout.fragment_course_evaluate;
    }

    @Override
    protected void initView() {

        commentview = v.findViewById(R.id.comment_view);

    }

    @Override
    protected void initData() {
        commentview.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
        CourseCommentAdapter commentAdapter = new CourseCommentAdapter(api.getStudentCourseInfos(), context);
        commentview.setAdapter(commentAdapter);
    }
}