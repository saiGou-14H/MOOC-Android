package com.org.moocapp.fragment;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.activaty.CourseVideoActivity;
import com.org.moocapp.activaty.IntegralHistoryActivity;
import com.org.moocapp.activaty.SignActivity;
import com.org.moocapp.adapter.SectionsPagerAdapter;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.CourseEntity;
import com.org.moocapp.entity.Response.CourseResponse;
import com.org.moocapp.entity.Response.UserEntity;
import com.org.moocapp.entity.Response.UserResponse;
import com.org.moocapp.entity_mysql.MCourse;
import com.org.moocapp.util.RoundTransform;
import com.squareup.picasso.Picasso;
import com.org.moocapp.util.utils;

import java.util.ArrayList;
import java.util.List;

public class StudyFragment extends BaseFragment implements View.OnClickListener {
    private String[] titles = {"自选课程","班级课程"};
    private ViewPager2 viewpager;
    private TabLayout tab;
    private List<Fragment> fragments = new ArrayList<>();
    private RelativeLayout image_bg;
    private Button gosign_btn;
    private Button aa;
    private UserEntity user;
    private TextView studytime;
    private TextView Todaystudytime;

    @Override
    protected int initLayout() {
        return R.layout.fragment_study;
    }

    @Override
    protected void initView() {
        viewpager = v.findViewById(R.id.studyviewpager);
        tab = v.findViewById(R.id.studytab);
        image_bg = v.findViewById(R.id.stydy_qd);
        gosign_btn = v.findViewById(R.id.gosign_btn);
        Todaystudytime = v.findViewById(R.id.todaystudytime);
        studytime = v.findViewById(R.id.studytime);
        aa = v.findViewById(R.id.studyfindbtn);
        aa.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        fragments.add(new MyCourseFragment());
        fragments.add(new MyClassCourseFragment());
        tab.setTabMode(TabLayout.MODE_FIXED);
        SectionsPagerAdapter sectionsPagerAdap = new SectionsPagerAdapter(getActivity().getSupportFragmentManager(),getLifecycle(),fragments);
        viewpager.setAdapter(sectionsPagerAdap);
        new TabLayoutMediator(tab, viewpager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles[position]);
            }
        }).attach();

        gosign_btn.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Picasso.with(context).load(R.mipmap.upimage).transform(new RoundTransform(context,10)).into(utils.setLayoutBackground(getResources(),image_bg));
        //image_bg.setBackground(getResources().getDrawable(R.mipmap.upimage));
        getUser();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.gosign_btn:{
               navigateTo(SignActivity.class,null);
               break;
            }
            case R.id.studyfindbtn:{
                navigateTo(IntegralHistoryActivity.class,null);
                break;
            }
        }
    }

    public void getUser(){
        Api.config(ApiConfig.shUser,null).getRequest(context, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                UserResponse userResponse = new Gson().fromJson(res,UserResponse.class);
                getActivity().runOnUiThread(new Runnable() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void run() {
                        if (userResponse.getCode()==200&&userResponse.getData()!=null){
                            user = userResponse.getData();
                            studytime.setText(
                                    String.format("%.1f", new Double(user.getStudyTime()/60.0))+"分钟");
                            Todaystudytime.setText(String.format("%.1f", new Double(user.getTodayStudyTime()/60.0))+"分钟");
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