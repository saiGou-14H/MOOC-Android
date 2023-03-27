package com.org.moocapp.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import com.org.moocapp.R;

import com.org.moocapp.activaty.PublishProblemActivity;
import com.org.moocapp.adapter.FindAdapter;

import java.util.ArrayList;


public class FindFragment extends BaseFragment {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"资讯", "问题"};
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private Button add_problem_btn;

    public FindFragment() {
        // Required empty public constructor
    }

    public static FindFragment newInstance() {
        FindFragment fragment = new FindFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initView() {
        viewPager = v.findViewById(R.id.fixedViewPager_message);
        slidingTabLayout = v.findViewById(R.id.slidingTabLayout_message);
        add_problem_btn = v.findViewById(R.id.add_problem_btn);
    }

    @Override
    protected void initData() {



    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //以下是添加tab和对应页面Fragments
        mFragments.add(MessageFragment.newInstance(mTitles[0]));
        mFragments.add(ProblemFragment.newInstance());
        viewPager.setOffscreenPageLimit(mFragments.size());
        //数据
        viewPager.setAdapter(new FindAdapter(getFragmentManager(), mTitles, mFragments));
//        不可用
//        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setViewPager(viewPager, mTitles);
        slidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 1) {
                    add_problem_btn.setVisibility(View.VISIBLE);
                } else {
                    add_problem_btn.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });


        add_problem_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.add_problem_btn:    //添加问题点击事件
                        navigateTo(PublishProblemActivity.class, null);

                        break;
                }
            }
        });
    }

}