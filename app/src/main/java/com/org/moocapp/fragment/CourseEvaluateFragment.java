package com.org.moocapp.fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.adapter.CourseCommentAdapter;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.CourseCommentEntity;
import com.org.moocapp.entity.CourseEntity;
import com.org.moocapp.entity.Response.CourseCommentResponse;
import com.org.moocapp.entity.StudentCourseEntity;

import java.util.List;

public class CourseEvaluateFragment extends BaseFragment {
    private RecyclerView commentview;
    private List<CourseCommentEntity> courseCommentEntities;
    private CourseEntity course;

    @Override
    protected int initLayout() {
        return R.layout.fragment_course_evaluate;
    }

    @Override
    protected void initView() {
        course = (CourseEntity) getActivity().getIntent().getSerializableExtra("data");
        commentview = v.findViewById(R.id.comment_view);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getCourseComment(course.getId());
    }

    public void getCourseComment(int courseid){
        Api.config(ApiConfig.shCourseComment+courseid,null).getRequest(context, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                CourseCommentResponse courseCommentResponse = new Gson().fromJson(res,CourseCommentResponse.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(courseCommentResponse.getCode()==200&&courseCommentResponse.getData()!=null){
                            System.out.println(res);
                            courseCommentEntities = courseCommentResponse.getData();
                            commentview.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
                            CourseCommentAdapter commentAdapter = new CourseCommentAdapter(courseCommentEntities, context);
                            commentview.setAdapter(commentAdapter);
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