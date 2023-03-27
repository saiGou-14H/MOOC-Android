package com.org.moocapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.adapter.CourseCommentAdapter;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.CourseCommentEntity;
import com.org.moocapp.entity.CourseEntity;
import com.org.moocapp.entity.Response.CourseCommentResponse;
import com.org.moocapp.entity.Response.ResponseHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyCourseOneFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView commentview;
    private List<CourseCommentEntity> courseCommentEntities = new ArrayList<>();
    private CourseEntity course;
    private TextInputEditText have_comment;
    private CourseCommentAdapter commentAdapter;
    private TextView have_comment_btn;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    commentAdapter.setDatas(courseCommentEntities);
                    commentAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.fragment_my_course_one;
    }

    @Override
    protected void initView() {
        course = (CourseEntity) getActivity().getIntent().getSerializableExtra("data");
        commentview = v.findViewById(R.id.have_comment_view);
        have_comment = v.findViewById(R.id.have_comment_Edit);
        have_comment_btn = v.findViewById(R.id.have_comment_bth);
    }

    @Override
    protected void initData() {
        have_comment_btn.setOnClickListener(this);
        commentview.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
        commentAdapter= new CourseCommentAdapter(courseCommentEntities, context);
        commentview.setAdapter(commentAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getCourseComment(course.getId());
    }

    public void adCourseComment(int courseid){
        HashMap<String,Object> map = new HashMap<>();
        map.put("comment",String.valueOf(have_comment.getText()));
        Api.config(ApiConfig.adCourseComment+courseid,map).postRequest(context, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                ResponseHeader responseHeader = new Gson().fromJson(res,ResponseHeader.class);
                if (responseHeader.getCode()==200){
                    showToastSync("发表评价成功");
                    getCourseComment(course.getId());
                    mHandler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
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
                            courseCommentEntities.clear();
                            courseCommentEntities = courseCommentResponse.getData();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.have_comment_bth:{
                if(have_comment.getText()!=null&& !have_comment.getText().equals("")){
                    adCourseComment(course.getId());
                }else {
                    showToastSync("评论内容为空！");
                }
                break;
            }
        }
    }
}