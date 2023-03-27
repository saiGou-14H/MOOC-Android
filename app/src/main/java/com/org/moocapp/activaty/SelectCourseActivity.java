package com.org.moocapp.activaty;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.adapter.CourseListAdapter;
import com.org.moocapp.adapter.TypeAdapter;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.CourseEntity;
import com.org.moocapp.entity.Response.CourseResponse;
import com.org.moocapp.entity.CourseTypeEntity;
import com.org.moocapp.entity.Response.CourseTypeResponse;
import com.org.moocapp.entity_mysql.MCourseType;
import com.org.moocapp.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectCourseActivity extends BaseActivity implements View.OnClickListener{

    private TextInputEditText typetitle;
    private Spinner coursetype;
    private RecyclerView selectCourseView;
    private Spinner coursetype2;
    private Spinner timetype;
    private Button returnbtn;
    private SmartRefreshLayout select_refresh;
    private List<CourseEntity> datas= new ArrayList<>();
    private List<CourseTypeEntity> courseTypeEntities = new ArrayList<>();
    private List<CourseTypeEntity> courseTypeEntities2 = new ArrayList<>();
    private List<CourseTypeEntity> courseTypeEntities3 = new ArrayList<>();
    private CourseTypeEntity def = new CourseTypeEntity();
    private CourseTypeEntity type1 = new CourseTypeEntity();
    private CourseTypeEntity type2 = new CourseTypeEntity();
    private CourseTypeEntity type3 = new CourseTypeEntity();
    private CourseListAdapter courseListAdapter;
    private int page = 1;
    private String base_url = "";
    private String SearchName="";
    private String ParentType = "";
    private String paixv = "default";
    TypeAdapter adapter;
    TypeAdapter adapter2;
    TypeAdapter adapter3;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    courseListAdapter.setDatas(datas);
                    courseListAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private Button coursefindbtn;

    @SuppressLint("ResourceAsColor")
    @Override
    protected int initLayout() {
        return R.layout.activity_course;
    }

    @Override
    protected void initView() {
        typetitle = findViewById(R.id.typetitle);
        coursefindbtn = findViewById(R.id.coursefindbtn);
        coursetype = findViewById(R.id.coursetype);
        coursetype2 = findViewById(R.id.coursetype2);
        selectCourseView = findViewById(R.id.select_course_view);
        timetype = findViewById(R.id.coursetime);
        returnbtn = findViewById(R.id.coursereturnbtn);
        select_refresh = findViewById(R.id.select_refresh);
    }

    @Override
    protected void initData() {

        if(getIntent().getStringExtra("search")!=null){
            SearchName =getIntent().getStringExtra("search");
            typetitle.setText(SearchName);
            base_url = ApiConfig.shCourseLike;
            Refresh(true,SearchName);
        }else {
            base_url = ApiConfig.shCourse;
            Refresh(true,"");
        }

        TypeRefresh(0,null);
        coursetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type1 = (CourseTypeEntity) adapterView.getSelectedItem();
                if(type1.getId()!=0){
                    page = 1;
                    ParentType=type1.getType();
                    base_url = ApiConfig.shCourseByParentType+ParentType+"/";
                    TypeRefresh(1,String.valueOf(type1.id));
                    Refresh(true,SearchName);
                }else{
                    page =1;
                    base_url = ApiConfig.shCourse;
                    ParentType="";
                    TypeRefresh(1,String.valueOf(type1.id));
                    Refresh(true,SearchName);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        coursetype2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type2 = (CourseTypeEntity) adapterView.getSelectedItem();
                if(type2.getId()!=0){
                    page = 1;
                    base_url = ApiConfig.shCourseByType+type2.getType()+"/";
//                    TypeRefresh(1,String.valueOf(type2.id));
                    Refresh(true,SearchName);
                }else{
                    if(ParentType!=""){
                        page =1;
                        base_url = ApiConfig.shCourseByParentType+ParentType+"/";
                        Refresh(true,SearchName);
                    }else{
                        page =1;
                        base_url = ApiConfig.shCourse;
                        Refresh(true,SearchName);
                    }

                }
                TypeRefresh(2,String.valueOf(type1.id));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        timetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type3 = (CourseTypeEntity) adapterView.getSelectedItem();
                if(type2.getId()!=0){
                    page = 1;
//                    TypeRefresh(1,String.valueOf(mCourseTypeList.id));
                    if(type3.getType().equals("点赞最多")){
                        paixv = "reco_num";
                    }else if(type3.getType().equals("收藏最多")){
                        paixv = "coll_num";
                    }else if(type3.getType().equals("分享最多")){
                        paixv = "share";
                    }else paixv = "default";
                    Refresh(true,SearchName);
                }else{
                    if(ParentType!=""){
                        page =1;
                        if(type3.getType().equals("点赞最多")){
                            paixv = "reco_num";
                        }else if(type3.getType().equals("收藏最多")){
                            paixv = "coll_num";
                        }else if(type3.getType().equals("分享最多")){
                            paixv = "share";
                        }else paixv = "default";
                        Refresh(true,SearchName);
                    }else{
                        page =1;
                        base_url = ApiConfig.shCourse;
                        if(type3.getType().equals("点赞最多")){
                            paixv = "reco_num";
                        }else if(type3.getType().equals("收藏最多")){
                            paixv = "coll_num";
                        }else if(type3.getType().equals("分享最多")){
                            paixv = "share";
                        }else paixv = "default";
                        Refresh(true,SearchName);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        courseListAdapter = new CourseListAdapter(mContext, datas, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", datas.get(position));
                navigateTo(CourseDetailActivity.class,bundle);
            }
        },R.layout.item_sellect_course);
        selectCourseView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        selectCourseView.setAdapter(courseListAdapter);

        select_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page =1;
                if(SearchName.equals("")){
                    Refresh(true,SearchName);
                }else{
                    Refresh(true,SearchName);
                }
            }
        });
        select_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page ++;
                if(SearchName.equals("")){
                    Refresh(false,SearchName);
                }else{
                    Refresh(false,SearchName);
                }

            }
        });


        returnbtn.setOnClickListener(this);
        coursefindbtn.setOnClickListener(this);


        typetitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i==KeyEvent.ACTION_DOWN){
                    page = 1;
                    SearchName = String.valueOf(typetitle.getText());
                    Refresh(true,SearchName);
                }
                return false;
            }
        });



    }

    public void TypeRefresh(int index,String parent_id){
        String url="";
        if(parent_id==null){
             url = ApiConfig.shMaxType;
        }else{
            url = ApiConfig.shChirlType+Integer.valueOf(parent_id);
        }
        Api.config(url,null).getRequest(this.mContext, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                CourseTypeResponse courseResponse = new Gson().fromJson(res,CourseTypeResponse.class);
                System.out.println(res);
                if(courseResponse!=null&&courseResponse.getData()!=null&&courseResponse.code==200){
                    List<CourseTypeEntity> courseTypeEntityList = courseResponse.getData();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            def.type="综合排序";
                            switch (index){
                                case 0:
                                    courseTypeEntities.clear();
                                    courseTypeEntities.add(def);
                                    courseTypeEntities.addAll(courseTypeEntityList);
                                    adapter = new TypeAdapter(mContext,R.layout.item_type);
                                    adapter.setDataType(courseTypeEntities);
                                    coursetype.setAdapter(adapter);
                                    //设置默认类型
                                    setOneItem(adapter,coursetype);break;
                                case 1:
                                    courseTypeEntities2.clear();
                                    courseTypeEntities2.add(def);
                                    courseTypeEntities2.addAll(courseTypeEntityList);
                                    adapter2 = new TypeAdapter(mContext,R.layout.item_type);
                                    adapter2.setDataType(courseTypeEntities2);
                                    coursetype2.setAdapter(adapter2);
                                    //设置默认类型
                                    setOneItem(adapter2,coursetype2);break;
                                case 2:
                                courseTypeEntities3.clear();
                                courseTypeEntities3.add(def);
                                CourseTypeEntity courseType1 = new CourseTypeEntity();
                                CourseTypeEntity courseType2 = new CourseTypeEntity();
                                CourseTypeEntity courseType3 = new CourseTypeEntity();
                                courseType1.setType("点赞最多");
                                courseType2.setType("收藏最多");
                                courseType3.setType("分享最多");
                                courseTypeEntities3.add(courseType1);
                                courseTypeEntities3.add(courseType2);
                                courseTypeEntities3.add(courseType3);
                                adapter3 = new TypeAdapter(mContext,R.layout.item_type);
                                adapter3.setDataType(courseTypeEntities3);
                                timetype.setAdapter(adapter3);
                                //设置默认类型
                                setOneItem(adapter3,timetype);

                            }
                        }
                    });
                }
            }
            @Override
            public void onFailure(Exception e) {
            }
        });
    }



    public void Refresh(final boolean isRefresh,String search){
        String url="";
        HashMap<String,Object> map = new HashMap<>();
        map.put("like",search);
        map.put("paixv",paixv);
        url = base_url+page+"/"+6;
        Api.config(url,map).postRequest(this.mContext, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isRefresh) {
                            select_refresh.finishRefresh(true);
                        } else {
                            select_refresh.finishLoadMore(true);
                        }
                        System.out.println(res);
                        CourseResponse courseResponse = new Gson().fromJson(res,CourseResponse.class);
                        if (courseResponse.getCode()==200&& courseResponse.getData()!=null){
                            List<CourseEntity> mcourselist = courseResponse.getData();
                            runOnUiThread(new Runnable() {
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
                });
            }

            @Override
            public void onFailure(Exception e) {
                if (isRefresh) {
                    select_refresh.finishRefresh(true);
                } else {
                    select_refresh.finishLoadMore(true);
                }
            }
        });
    }

    public void RefreshTypeOne(final boolean isRefresh,boolean isSearch,String search){
        String url="";
        HashMap<String,Object> map = new HashMap<>();
        if(isSearch){
            url = base_url+page+"/"+6;
            map.put("like",search);
        }else{
            url = base_url+page+"/"+6;
        }
        Api.config(url,map).postRequest(this.mContext, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                if (isRefresh) {
                    select_refresh.finishRefresh(true);
                } else {
                    select_refresh.finishLoadMore(true);
                }
                CourseResponse courseResponse = new Gson().fromJson(res,CourseResponse.class);
                List<CourseEntity> mcourselist = courseResponse.getData();
                if (courseResponse.getCode()==200&&mcourselist!=null){
                    runOnUiThread(new Runnable() {
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
                    select_refresh.finishRefresh(true);
                } else {
                    select_refresh.finishLoadMore(true);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.coursefindbtn:{
                SearchName = String.valueOf(typetitle.getText());
                Refresh(true,SearchName);
                break;
            }
            case R.id.coursereturnbtn:
                finish();
                break;
            case R.id.select_course_btn:
                break;
        }
    }




    public void setOneItem(TypeAdapter adapter,Spinner spinner){
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String typename = bundle.getString("type");
            for (int i = adapter.getCount()-1 ; i>=0 ;i-- ){
                CourseTypeEntity mCourseTypeList = (CourseTypeEntity)adapter.getItem(i);
                if (mCourseTypeList.type.equals(typename)){
                    TextInputEditText title = findViewById(R.id.typetitle);
                    title.setHint(typename);
                    spinner.setSelection(i,Boolean.TRUE);
                }
            }
        }
        else{
            TextInputEditText title = findViewById(R.id.typetitle);
            spinner.setSelection(0,Boolean.TRUE);
        }
    }

}