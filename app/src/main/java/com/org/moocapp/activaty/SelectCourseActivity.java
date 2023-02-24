package com.org.moocapp.activaty;


import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.org.moocapp.R;
import com.org.moocapp.adapter.CourseListAdapter;
import com.org.moocapp.adapter.TypeAdapter;
import com.org.moocapp.api1.api;
import com.org.moocapp.entity_mysql.MCourseType;
import com.org.moocapp.listener.OnItemClickListener;
import com.org.moocapp.view.MyRecyclerView;

public class SelectCourseActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    private Spinner coursetype;
    private MyRecyclerView selectCourseView;
    private Spinner gradetype;
    private Spinner timetype;
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
        return R.layout.activity_course;
    }

    @Override
    protected void initView() {
        coursetype = findViewById(R.id.coursetype);
        selectCourseView = findViewById(R.id.select_course_view);
        timetype = findViewById(R.id.coursetime);
        gradetype = findViewById(R.id.coursegrade);
        returnbtn = findViewById(R.id.coursereturnbtn);
    }

    @Override
    protected void initData() {
        TypeAdapter adapter = new TypeAdapter(api.getCourseTypeInfos(), mContext);
        coursetype.setAdapter(adapter);
        TypeAdapter adapter1 = new TypeAdapter(api.getCourseTypeInfos(), mContext);
        gradetype.setAdapter(adapter);
        TypeAdapter adapter2 = new TypeAdapter(api.getCourseTypeInfos(), mContext);
        timetype.setAdapter(adapter);
        coursetype.setOnItemSelectedListener(this);
        CourseListAdapter courseListAdapter = new CourseListAdapter(mContext, api.getCourseList(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                navigateTo(CourseDetailActivity.class,null);
            }
        },R.layout.item_sellect_course);
        selectCourseView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        selectCourseView.setAdapter(courseListAdapter);
        //设置默认类型
        setOneItem(adapter);

        returnbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        MCourseType mCourseTypeList = (MCourseType) adapterView.getSelectedItem();
        showToast(mCourseTypeList.getType());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }


    public void setOneItem(TypeAdapter adapter){
        String typename = getIntent().getExtras().getString("type");
        for (int i = adapter.getCount()-1 ; i>=0 ;i-- ){
            MCourseType mCourseTypeList = (MCourseType)adapter.getItem(i);
            if (mCourseTypeList.getType().equals(typename)){
                TextInputEditText title = findViewById(R.id.typetitle);
                title.setHint(typename);
                coursetype.setSelection(i,Boolean.TRUE);
            }
        }
    }

}