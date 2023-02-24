package com.org.moocapp.fragment;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.org.moocapp.R;
import com.org.moocapp.activaty.SearchActivity;
import com.org.moocapp.activaty.SelectCourseActivity;
import com.org.moocapp.activaty.CourseDetailActivity;
import com.org.moocapp.adapter.CourseGridAdapter;
import com.org.moocapp.adapter.ImageViewAdapter;
import com.org.moocapp.api1.api;
import com.org.moocapp.entity_mysql.MCourse;

import java.util.ArrayList;


public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private int currentPosition;
    private GridView gridview;
    private ViewPager viewPager;
    private Runnable runable;
    private ImageViewAdapter imageViewAdapter;
    //模拟网络获取数据
    private ArrayList<MCourse> mCourseInfos = api.getImageInfos();
    private Button more;
    private Button search;

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        viewPager = v.findViewById(R.id.lunbo);
        more = v.findViewById(R.id.homemore);
        gridview = v.findViewById(R.id.selectcoursegridview);
        search = v.findViewById(R.id.selectcoursefindbtn);
    }

    @Override
    protected void initData() {
        imageViewAdapter = new ImageViewAdapter(context, mCourseInfos);
        viewPager.setAdapter(imageViewAdapter);

        runable = new Runnable() {

            int i = 1;

            @Override
            public void run() {
                //下标自动++
                currentPosition += i;
                //当下标到最后时，就重新来过
                if (currentPosition >= mCourseInfos.size()) {
                    currentPosition = mCourseInfos.size() - 1;
                    i = -1;
                }
                if (currentPosition <= 0) {
                    currentPosition = 0;
                    i = 1;
                }
                //设置图片
                viewPager.setCurrentItem(currentPosition);
                //重复调用
                viewPager.postDelayed(this, 2000);
            }
        };
        viewPager.postDelayed(runable, 2000);
        viewPager.setOnClickListener(this::onClick);
        //模拟网络获取数据api.getCourseList()
        CourseGridAdapter courseGridAdapter = new CourseGridAdapter(context, api.getCourseList());
        gridview.setAdapter(courseGridAdapter);

        more.setOnClickListener(this);
        v.findViewById(R.id.hometype1).setOnClickListener(this);
        v.findViewById(R.id.hometype2).setOnClickListener(this);
        v.findViewById(R.id.hometype3).setOnClickListener(this);
        v.findViewById(R.id.hometype4).setOnClickListener(this);
        search.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gridview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        navigateTo(CourseDetailActivity.class, null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hometype1: {
                Bundle bundle = new Bundle();
                Button hometype1 = v.findViewById(view.getId());
                bundle.putString("type", String.valueOf(hometype1.getText()));
                navigateTo(SelectCourseActivity.class, bundle);
                break;
            }
            case R.id.hometype2: {
                Bundle bundle = new Bundle();
                Button hometype1 = v.findViewById(view.getId());
                bundle.putString("type", String.valueOf(hometype1.getText()));
                navigateTo(SelectCourseActivity.class, bundle);
                break;
            }
            case R.id.hometype3: {
                Bundle bundle = new Bundle();
                Button hometype1 = v.findViewById(view.getId());
                bundle.putString("type", String.valueOf(hometype1.getText()));
                navigateTo(SelectCourseActivity.class, bundle);
                break;
            }
            case R.id.hometype4: {
                Bundle bundle = new Bundle();
                Button hometype1 = v.findViewById(view.getId());
                bundle.putString("type", String.valueOf(hometype1.getText()));
                navigateTo(SelectCourseActivity.class, bundle);
                break;
            }
            case R.id.homemore: {
                Bundle bundle = new Bundle();
                Button hometype1 = v.findViewById(view.getId());
                bundle.putString("type", String.valueOf(hometype1.getText()));
                navigateTo(SelectCourseActivity.class, bundle);
                break;
            }
            case R.id.lunbo: {
                navigateTo(CourseDetailActivity.class, null);
                break;
            }
            case R.id.selectcoursefindbtn: {
                navigateTo(SearchActivity.class, null);
                break;
            }
        }

    }
}