package com.org.moocapp.activaty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.org.moocapp.R;
import com.org.moocapp.adapter.SectionsPagerAdapter;
import com.org.moocapp.fragment.CourseCatalogueFragment;
import com.org.moocapp.fragment.CourseEvaluateFragment;
import com.org.moocapp.fragment.CourseIntroductionFragment;

import java.util.ArrayList;
import java.util.List;

public class CourseDetailActivity extends BaseActivity implements View.OnClickListener {

    private String[] titles = {"简介","目录","评价"};
    private TabLayout tab;
    List<Fragment> fragments = new ArrayList<>();
    private ViewPager2 viewpager;
    private Button returnbtn;

    @SuppressLint("ResourceAsColor")
    @Override
    protected int initLayout() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(R.color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        return R.layout.activity_course_detail;
    }

    @Override
    protected void initView() {
        tab = findViewById(R.id.coursedetailtab);
        viewpager = findViewById(R.id.coursedetialviewpager);
        returnbtn = findViewById(R.id.coursedetialreturnbtn);
    }

    @Override
    protected void initData() {
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



    }

    @Override
    public void onClick(View view) {
        finish();
    }
}