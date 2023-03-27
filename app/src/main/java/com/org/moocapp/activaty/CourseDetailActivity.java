package com.org.moocapp.activaty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.adapter.SectionsPagerAdapter;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.CourseEntity;
import com.org.moocapp.entity.Response.ResponseHeader;
import com.org.moocapp.entity.Response.StudentCourseResponse;
import com.org.moocapp.entity.StudentCourseEntity;
import com.org.moocapp.fragment.CourseCatalogueFragment;
import com.org.moocapp.fragment.CourseEvaluateFragment;
import com.org.moocapp.fragment.CourseIntroductionFragment;
import com.org.moocapp.util.DrawableUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CourseDetailActivity extends BaseActivity implements View.OnClickListener {

    private String[] titles = {"简介","目录","评价"};
    private TabLayout tab;
    List<Fragment> fragments = new ArrayList<>();
    private ViewPager2 viewpager;
    private Button returnbtn;
    private CourseEntity data;

    private ImageView image;
    private TextView name;
    private TextView dz;
    private TextView sc;
    private TextView share;

    private Button dz_btn;
    private Button sc_btn;
    private Button share_btn;
    private Button addcar_btn;
    private Button pay_btn;
    @SuppressLint("ResourceAsColor")
    @Override
    protected int initLayout() {
        return R.layout.activity_course_detail;
    }

    @Override
    protected void initView() {
        tab = findViewById(R.id.coursedetailtab);
        viewpager = findViewById(R.id.coursedetialviewpager);
        returnbtn = findViewById(R.id.coursedetialreturnbtn);
        image = findViewById(R.id.detailcourse_image);
        name = findViewById(R.id.detailcourse_name);
        dz = findViewById(R.id.detailcourse_dz);
        sc = findViewById(R.id.detailcourse_sc);
        share = findViewById(R.id.detailcourse_share);

        dz_btn = findViewById(R.id.detailcourse_dz_btn);
        sc_btn = findViewById(R.id.detailcourse_sc_btn);
        share_btn = findViewById(R.id.detailcourse_share_btn);
        addcar_btn = findViewById(R.id.detialcourseaddcar);
        pay_btn = findViewById(R.id.detialcourselpay);
    }

    @Override
    protected void initData() {
        data = (CourseEntity)getIntent().getSerializableExtra("data");

        fragments.add(new CourseIntroductionFragment());
        fragments.add(new CourseCatalogueFragment());
        fragments.add(new CourseEvaluateFragment());
        tab.setTabMode(TabLayout.MODE_FIXED);
        SectionsPagerAdapter sectionsPagerAdap = new SectionsPagerAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        viewpager.setAdapter(sectionsPagerAdap);
        new TabLayoutMediator(tab, viewpager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles[position]);
            }
        }).attach();
        returnbtn.setOnClickListener(this);

        dz_btn.setOnClickListener(this);
        sc_btn.setOnClickListener(this);
        share_btn.setOnClickListener(this);
        addcar_btn.setOnClickListener(this);
        pay_btn.setOnClickListener(this);

        initData2(data);
    }

    public void initData2(CourseEntity data){
        if(data.getPicture()!=null)
            Picasso.with(mContext).load(data.getPicture()).into(image);{
        }
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        name.setText(data.getName());
        dz.setText(String.valueOf(data.getRecoNum()));
        sc.setText(String.valueOf(data.getCollNum()));
        share.setText(String.valueOf(data.getShare()));
        initStudentCourse(data.getId());
    }
    public void initStudentCourse(int courseid){
        Api.config(ApiConfig.shStudentCourse+courseid,null).getRequest(mContext, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StudentCourseResponse studentCourseResponse = new Gson().fromJson(res,StudentCourseResponse.class);
                        if(studentCourseResponse.getCode() == 200 && studentCourseResponse.getData()!=null){
                            StudentCourseEntity studentCourseEntity = studentCourseResponse.getData();
                            if(studentCourseEntity.isRecommend()){
                                Drawable drawable =getResources().getDrawable(R.drawable.dianzan);
                                drawable = DrawableUtils.tint(drawable, Color.RED);
                                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                                dz.setCompoundDrawables(drawable,null,null,null);
                            }
                            if(studentCourseEntity.isCollect()){
                                Drawable drawable =getResources().getDrawable(R.drawable.shoucang);
                                drawable = DrawableUtils.tint(drawable, Color.RED);
                                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                                sc.setCompoundDrawables(drawable,null,null,null);
                            }
                        }
                    }
                });

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    public void run(int index,int courseid){
        String url = ApiConfig.appStudent;
        if(index==1)url=url+"up/";
                else if(index==2)url+="collect/";
                else url+="share/";
        Api.config(url+courseid,null).getRequest(mContext, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                ResponseHeader responseHeader = new Gson().fromJson(res,ResponseHeader.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(responseHeader.getCode()==200){
                            if(index==1) {
                                Drawable drawable =getResources().getDrawable(R.drawable.dianzan);
                                drawable = DrawableUtils.tint(drawable, Color.RED);
                                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                                dz.setCompoundDrawables(drawable,null,null,null);
                                dz.setText(String.valueOf(Integer.valueOf((String) dz.getText())+1));

                            }else if(index==2){
                                Drawable drawable =getResources().getDrawable(R.drawable.shoucang);
                                drawable = DrawableUtils.tint(drawable, Color.RED);
                                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                                sc.setCompoundDrawables(drawable,null,null,null);
                                sc.setText(String.valueOf(Integer.valueOf((String) sc.getText())+1));
                            }
                        }else if (responseHeader.getCode()==300){
                            if(index==1) {
                                Drawable drawable =getResources().getDrawable(R.drawable.dianzan);
                                drawable = DrawableUtils.tint(drawable, Color.BLACK);
                                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                                dz.setCompoundDrawables(drawable,null,null,null);
                                dz.setText(String.valueOf(Integer.valueOf((String) dz.getText())-1));
                            }else if(index==2){
                                Drawable drawable =getResources().getDrawable(R.drawable.shoucang);
                                drawable = DrawableUtils.tint(drawable, Color.BLACK);
                                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                                sc.setCompoundDrawables(drawable,null,null,null);
                                sc.setText(String.valueOf(Integer.valueOf((String) sc.getText())-1));
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

    }

    public void addcar(){
        Api.config(ApiConfig.adCourseCar+data.getId(),null).getRequest(mContext, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                showToastSync("加入购物车成功");
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.coursedetialreturnbtn:finish();break;
            case R.id.detailcourse_dz_btn:run(1,data.getId());break;
            case R.id.detailcourse_sc_btn:run(2, data.getId());break;
            case R.id.detailcourse_share_btn:run(3, data.getId());break;
            case R.id.detialcourseaddcar:{
                addcar();
                break;
            }
            case R.id.detialcourselpay:{

                break;
            }
        }
    }
}