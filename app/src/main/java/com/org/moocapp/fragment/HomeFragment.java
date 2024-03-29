package com.org.moocapp.fragment;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.activaty.SearchActivity;
import com.org.moocapp.activaty.SelectCourseActivity;
import com.org.moocapp.activaty.CourseDetailActivity;
import com.org.moocapp.adapter.CourseGridAdapter;
import com.org.moocapp.adapter.ImageViewAdapter;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.CourseEntity;
import com.org.moocapp.entity.Response.CourseResponse;
import com.org.moocapp.entity_mysql.MCourse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener, TextView.OnEditorActionListener {
    private int currentPosition;
    private GridView gridview;
    private ViewPager viewPager;
    private Runnable runable;
    private ImageViewAdapter imageViewAdapter;
    //模拟网络获取数据
    private ArrayList<MCourse> mCourseInfos = Api.getImageInfos();
    private Button more;
    private Button search;
    private SmartRefreshLayout homerefresh;
    private TextInputEditText searchbtn;

    CourseGridAdapter courseGridAdapter;
    List<CourseEntity> datas = new ArrayList<>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    courseGridAdapter.setDatas(datas);
                    courseGridAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    private static int page = 1;

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        viewPager = v.findViewById(R.id.lunbo);
        more  =v.findViewById(R.id.homemore);
        gridview= v.findViewById(R.id.selectcoursegridview);
        search = v.findViewById(R.id.selectcoursefindbtn);
        homerefresh = v.findViewById(R.id.homerefresh);
        searchbtn = v.findViewById(R.id.home_search);
    }

    @Override
    protected void initData() {
        homerefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getCourseList(true);
            }
        });

        homerefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getCourseList(false);
            }
        });
        imageViewAdapter = new ImageViewAdapter(context,mCourseInfos);
        viewPager.setAdapter(imageViewAdapter);
        searchbtn.setOnEditorActionListener(this);

        runable= new Runnable() {
            int i = 1;
            @Override
            public void run() {
                //下标自动++
                currentPosition+=i;
                //当下标到最后时，就重新来过
                if(currentPosition >= mCourseInfos.size()){
                    currentPosition = mCourseInfos.size()-1;
                    i=-1;
                }
                if(currentPosition <= 0){
                    currentPosition = 0;
                    i=1;
                }
                //设置图片
                viewPager.setCurrentItem(currentPosition);
                //重复调用
                viewPager.postDelayed(this,2000);
            }
        };
        viewPager.postDelayed(runable,2000);
        viewPager.setOnClickListener(this::onClick);



        more.setOnClickListener(this);
        v.findViewById(R.id.hometype1).setOnClickListener(this);
        v.findViewById(R.id.hometype2).setOnClickListener(this);
        v.findViewById(R.id.hometype3).setOnClickListener(this);
        v.findViewById(R.id.hometype4).setOnClickListener(this);
        search.setOnClickListener(this);

        courseGridAdapter = new CourseGridAdapter(context,datas);
        gridview.setAdapter(courseGridAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        getCourseList(true);
    }

    public void getCourseList(final boolean isRefresh){
        HashMap<String,Object> map = new HashMap<>();
        map.put("paixv","coll_num");
        Api.config(ApiConfig.shCourse+"/"+page+"/"+ApiConfig.PAGE_SIZE,map).postRequest(context, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                if (isRefresh) {
                    homerefresh.finishRefresh(true);
                } else {
                    homerefresh.finishLoadMore(true);
                }
                CourseResponse courseResponse = new Gson().fromJson(res,CourseResponse.class);
                if (courseResponse.getCode()==200&& courseResponse.getData()!=null){
                    List<CourseEntity> mcourselist = courseResponse.getData();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(isRefresh){
                                datas = mcourselist;;
                            }else {
                                datas.addAll(mcourselist);
                            }
                            mHandler.sendEmptyMessage(0);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {
                if (isRefresh) {
                    homerefresh.finishRefresh(true);
                } else {
                    homerefresh.finishLoadMore(true);
                }
            }
        });
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gridview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", datas.get(i));
        navigateTo(CourseDetailActivity.class,bundle);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hometype1:{
                Bundle bundle = new Bundle();
                Button hometype1 = v.findViewById(view.getId());
                bundle.putString("type",String.valueOf(hometype1.getText()));
                navigateTo(SelectCourseActivity.class,bundle);break;
            }
            case R.id.hometype2:{
                Bundle bundle = new Bundle();
                Button hometype1 = v.findViewById(view.getId());
                bundle.putString("type",String.valueOf(hometype1.getText()));
                navigateTo(SelectCourseActivity.class,bundle);break;
            }
            case R.id.hometype3:{
                Bundle bundle = new Bundle();
                Button hometype1 = v.findViewById(view.getId());
                bundle.putString("type",String.valueOf(hometype1.getText()));
                navigateTo(SelectCourseActivity.class,bundle);break;
            }
            case R.id.hometype4:{
                Bundle bundle = new Bundle();
                Button hometype1 = v.findViewById(view.getId());
                bundle.putString("type",String.valueOf(hometype1.getText()));
                navigateTo(SelectCourseActivity.class,bundle);break;
            }
            case R.id.homemore:{
                Bundle bundle = new Bundle();
                Button hometype1 = v.findViewById(view.getId());
                bundle.putString("type",String.valueOf(hometype1.getText()));
                navigateTo(SelectCourseActivity.class,bundle);break;
            }
            case R.id.lunbo:{
                navigateTo(CourseDetailActivity.class,null);
                break;
            }
            case R.id.selectcoursefindbtn:{
                navigateTo(SearchActivity.class,null);
                break;
            }
            case R.id.home_search:{
                break;
            }
        }

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i==KeyEvent.ACTION_DOWN){
            Bundle bundle = new Bundle();
            bundle.putString("search", String.valueOf(searchbtn.getText()));
            navigateTo(SelectCourseActivity.class,bundle);
        }
        return false;
    }
}