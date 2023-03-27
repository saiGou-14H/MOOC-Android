package com.org.moocapp.activaty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.org.moocapp.entity.LearnProgressEntity;
import com.org.moocapp.entity.Response.LearnProgressResponse;
import com.org.moocapp.entity.Response.LearnProgresssResponse;
import com.org.moocapp.fragment.CourseCatalogueFragment;
import com.org.moocapp.fragment.MyCourseOneFragment;
import com.org.moocapp.fragment.MyCourseThreeFragment;
import com.org.moocapp.fragment.MyCourseTwoFragment;
import com.org.moocapp.util.utils;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyCourseDetailActivity extends BaseActivity implements View.OnClickListener {
    private String[] titles = {"目录","评论"};
    private TabLayout tab ;
    private List<Fragment> fragments = new ArrayList<>();
    private ViewPager2 viewpager;
    private Button practice_btn;
    private CourseEntity data;
    private List<LearnProgressEntity> learnProgressEntities;
    private Bundle bundle = new Bundle();

    private ImageView image;
    private TextView name;
    private TextView jd;
    private ProgressBar progressbar;
    private TextView have_progressbar_text;
    @Override
    protected int initLayout() {
        return R.layout.activity_my_course_detail;
    }

    @Override
    protected void initView() {
        tab = findViewById(R.id.have_coursedetailtab);
        viewpager = findViewById(R.id.have_coursedetialviewpager);
        practice_btn = findViewById(R.id.go_course_practice);


        image = findViewById(R.id.hava_course_image);
        name = findViewById(R.id.have_detailcoursename);
        jd = findViewById(R.id.have_course_jd);
        progressbar = findViewById(R.id.have_progressbar);
        have_progressbar_text = findViewById(R.id.have_progressbar_text);
    }

    @Override
    protected void initData() {
        data = (CourseEntity)getIntent().getSerializableExtra("data");

        fragments.add(new MyCourseTwoFragment());
        fragments.add(new MyCourseOneFragment());
        tab.setTabMode(TabLayout.MODE_FIXED);
        SectionsPagerAdapter sectionsPagerAdap = new SectionsPagerAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        viewpager.setAdapter(sectionsPagerAdap);
        new TabLayoutMediator(tab, viewpager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles[position]);
            }
        }).attach();
        practice_btn.setOnClickListener(this);
        initCourseData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCourseData();
    }

    public void initCourseData(){
        if(data!=null){
            Picasso.with(mContext).load(data.getPicture()).into(image);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            name.setText(String.valueOf(data.getName()));
            getLearnProgress(data.getId());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.go_course_practice:{
                bundle.putSerializable("data",data);
                navigateTo(PracticeActivity.class,bundle);
            }
        }
    }

    public void getLearnProgress(int courseid){
        Api.config(ApiConfig.shLearnProgress+courseid,null).getRequest(mContext, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                LearnProgresssResponse learnProgressResponse = new Gson().fromJson(res,LearnProgresssResponse.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(learnProgressResponse.getCode()==200&&learnProgressResponse.getData()!=null){
                            learnProgressEntities = learnProgressResponse.getData();
                            int progress_ = 0;
                            int catanum = 0;
                            for (int i = 0; i < learnProgressEntities.size(); i++) {
                                progress_+=learnProgressEntities.get(i).getReadTime();
                                if(learnProgressEntities.get(i).progress>=1)catanum++;
                            }
                            jd.setText(String.valueOf(data.getCataNum())+"章"+" / "+catanum+"章");
                            String str = utils.getRatio(new Double(progress_/1000),new Double(data.getTime()));
                            Double x = Double.valueOf(str.replace("%",""));
                            progressbar.setProgress(Integer.valueOf((int) (x*100/100)));
                            have_progressbar_text.setText(str);
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