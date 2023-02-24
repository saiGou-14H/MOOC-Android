package com.org.moocapp.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.org.moocapp.MainActivity;
import com.org.moocapp.R;
import com.org.moocapp.adapter.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class StudyFragment extends BaseFragment {
    private String[] titles = {"自选课程","班级课程"};
    private ViewPager2 viewpager;
    private TabLayout tab;
    private List<Fragment> fragments = new ArrayList<>();
    @Override
    protected int initLayout() {
        return R.layout.fragment_study;
    }

    @Override
    protected void initView() {
        viewpager = v.findViewById(R.id.studyviewpager);
        tab = v.findViewById(R.id.studytab);
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
    }
}