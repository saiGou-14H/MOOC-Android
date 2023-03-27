package com.org.moocapp.activaty;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.icu.text.SimpleDateFormat;
import android.net.ParseException;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.appcompat.widget.AppCompatEditText;

import com.bigkoo.pickerview.TimePickerView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.adapter.TypeAdapter;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.CourseEntity;
import com.org.moocapp.entity.CoursePracticeEntity;
import com.org.moocapp.entity.Requests.StudentParcticeEntity;
import com.org.moocapp.entity.Response.CoursePracticeResponse;
import com.org.moocapp.entity.Response.ResponseHeader;
import com.org.moocapp.entity.Response.StudentPracticeResponse;
import com.org.moocapp.entity.StudentPracticeEntity;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.CollationElementIterator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class PracticeActivity extends BaseActivity implements View.OnClickListener {
    private CollationElementIterator tvbirthday;
    private TimePickerView pvTime;
    private ImageView practice_image;
    private Button time_btn;
    private Button practice_position;
    private Button practice_send;
    private Spinner prictice_spinner;
    private Button practice_time;
    private TextInputEditText practice_phone;
    private TextInputEditText practice_username;
    private AppCompatEditText practice_remake;
    private CourseEntity data;
    private List<CoursePracticeEntity> coursePracticeEntities;
    private TypeAdapter adapter;
    private CoursePracticeEntity selectcoursePracticeEntity;
//    private StudentParcticeEntity studentParcticeEntity = new StudentParcticeEntity();

    private DatePickerDialog dataPickerDialog;

    private String strDate;

    private TimePickerDialog timePickerDialog;
    @Override
    protected int initLayout() {
        return R.layout.activity_practice;
    }
    @Override
    protected void initView() {
        //初始化日期选择器
        showTimePicker();
        selectDataAndTime();
        time_btn = findViewById(R.id.practice_time);
        prictice_spinner = findViewById(R.id.prictice_spinner);
        practice_send = findViewById(R.id.practice_send);
        practice_time = findViewById(R.id.practice_time);
        practice_position = findViewById(R.id.practice_position);
        practice_phone = findViewById(R.id.practice_phone);
        practice_remake = findViewById(R.id.practice_remake);
        practice_username = findViewById(R.id.practice_username);
        practice_image = findViewById(R.id.practice_image);
        data = (CourseEntity) getIntent().getSerializableExtra("data");
    }

    @Override
    protected void initData() {
        Picasso.with(mContext).load(data.getPicture()).into(practice_image);
        practice_image.setScaleType(ImageView.ScaleType.FIT_XY);

        time_btn.setOnClickListener(this);
        if (data!=null){
            initSpinner(String.valueOf(data.getId()));
        }
        prictice_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              selectcoursePracticeEntity = (CoursePracticeEntity) adapterView.getSelectedItem();
                practice_position.setText(selectcoursePracticeEntity.getSite());
                practice_time.setText(selectcoursePracticeEntity.getDate());
                if(selectcoursePracticeEntity.getId()!=0){
                    shStudentParctice(selectcoursePracticeEntity.getId());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        practice_send.setOnClickListener(this);
    }

    public void shStudentParctice(int parcticeid){
        Api.config(ApiConfig.shPracticeByStudentId+parcticeid,null).getRequest(mContext, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                runOnUiThread(new Runnable() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void run() {
                        StudentPracticeResponse studentPracticeResponse = new Gson().fromJson(res,StudentPracticeResponse.class);
                        StudentPracticeEntity studentPracticeEntity = studentPracticeResponse.getData();
                        if(studentPracticeResponse.getCode()==200){
                            if(studentPracticeEntity!=null){
                                practice_time.setText(studentPracticeEntity.getPraDate());
                                practice_position.setText(studentPracticeEntity.getPosition());
                                practice_phone.setText(studentPracticeEntity.getPhone());
                                practice_remake.setText(studentPracticeEntity.getRemake());
                                practice_username.setText(studentPracticeEntity.getStuName());
                                practice_send.getBackground().setColorFilter(Color.rgb(252,85,49), PorterDuff.Mode.DARKEN);
                                practice_send.setText("已报名该实践");
                            }else{
                                showToast("未报名");
                                practice_phone.setText("");
                                practice_remake.setText("");
                                practice_username.setText("");
                                practice_send.getBackground().setColorFilter(Color.rgb(94,200,245), PorterDuff.Mode.MULTIPLY);
                                practice_send.setText("报名");
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

    public void initSpinner(String courseid){
        Api.config(ApiConfig.shPracticeByCouserId+courseid,null).getRequest(mContext, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CoursePracticeResponse coursePracticeResponse = new Gson().fromJson(res,CoursePracticeResponse.class);
                        coursePracticeEntities = coursePracticeResponse.getData();
                        if(coursePracticeResponse.getCode()==200&&coursePracticeEntities!=null){
                            adapter = new TypeAdapter(mContext,R.layout.item_select);
                            adapter.setDataPractice(coursePracticeEntities);
                            prictice_spinner.setAdapter(adapter);
                        }else if(coursePracticeEntities.size()==0){
                            CoursePracticeEntity coursePracticeEntity = new CoursePracticeEntity();
                            coursePracticeEntity.setName("该课程未发起实训");
                            coursePracticeEntities.add(coursePracticeEntity);
                            adapter = new TypeAdapter(mContext,R.layout.item_select);
                            adapter.setDataPractice(coursePracticeEntities);
                            prictice_spinner.setAdapter(adapter);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

    }


    /**
     * 选择日期和时间1
     */
    public void showTimePicker(){
         pvTime= new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String birthday = format.format(date);
                tvbirthday.setText(birthday);

            }
        }).setSubmitText("确定")
                .setCancelText("取消")
                .setCancelColor(Color.BLACK)
                .setSubmitColor(Color.BLACK)
                .setSubCalSize(16)
                //.isDialog(true) //是否对话框样式显示（显示在页面中间）
                //.isCyclic(true) //是否循环滚动
                .setType(new boolean[]{true, true, true, false, false, false}) //显示“年月日时分秒”的哪几项
                .isCenterLabel(false) //是否只显示选中的label文字，false则每项item全部都带有 label
                .build();
        //设置显示的日期
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-20"));
        } catch (ParseException | java.text.ParseException e) {
            //e.printStackTrace();
        }
        //这里需要注意的是月份是从0开始的，要显示10月份这里的参数应该是9
        //calendar.set(1997,9,10);
        pvTime.setDate(calendar);
    }
    /**
     * 选择日期和时间2
     */
    private void selectDataAndTime() {
        // 获取当前时间
        final Calendar calendar = Calendar.getInstance();
        /*
         * toast("当前时间是：" + calendar.get(Calendar.YEAR) + "," +
         * calendar.get(Calendar.MONTH) + "," +
         * calendar.get(Calendar.DAY_OF_MONTH));
         */
        // 日期选择对话框
        dataPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                // 判断用户选择的日期是否合法
                if (calendar.get(Calendar.YEAR) > year) {
                    showToast("时间有误，请从新选择");
                    return;
                } else if (calendar.get(Calendar.YEAR) == year) {
                    if (calendar.get(Calendar.MONTH) > month) {
                        showToast("时间有误，请从新选择");
                        return;
                    } else if (calendar.get(Calendar.MONTH) == month) {
                        if (calendar.get(Calendar.DAY_OF_MONTH) > day) {
                            showToast("时间有误，请从新选择");
                            return;
                        } else {
                            strDate = year + "-" + (month + 1) + "-" + day;
                            if (timePickerDialog != null) {
                                timePickerDialog.show();
                            }
                        }
                    } else {
                        strDate = year + "-" + (month + 1) + "-" + day;
                        if (timePickerDialog != null) {
                            timePickerDialog.show();
                        }
                    }
                } else {
                    strDate = year + "-" + (month + 1) + "-" + day;
                    if (timePickerDialog != null) {
                        timePickerDialog.show();
                    }
                }
                String strTime = strDate ;
                time_btn.setText(strTime);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH));
        // 时间选择对话框
//        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//
//            @Override
//            public void onTimeSet(TimePicker view, int hour, int minute) {
//                String strTime = strDate + " " + hour + ":" + minute;
//                time_btn.setText(strTime);
//            }
//        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.practice_time:{
                //pvTime.show();
                dataPickerDialog.show();
                break;
            }
            case R.id.practice_send:
                if(selectcoursePracticeEntity!=null)
                sendParctice();
                else
                    showToast("该课程未包含实训内容");
                break;

        }
    }



    public void sendParctice(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("stuId",null);
        hashMap.put("praId",selectcoursePracticeEntity.getId());
        hashMap.put("praDate",practice_time.getText());
        hashMap.put("stuName",String.valueOf(practice_username.getText()));
        hashMap.put("remake",String.valueOf(practice_remake.getText()));
        hashMap.put("position",String.valueOf(practice_position.getText()));
        hashMap.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) );
        hashMap.put("phone",String.valueOf(practice_phone.getText()));
        Api.config(ApiConfig.adPractice,hashMap).postRequest(mContext, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                ResponseHeader responseHeader = new Gson().fromJson(res,ResponseHeader.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(responseHeader.getCode()==200){
                            showToast("报名成功！");
                            practice_send.getBackground().setColorFilter(Color.rgb(252,85,49), PorterDuff.Mode.DARKEN);
                            practice_send.setText("已报名该实践");
                        }else if(responseHeader.getCode()==300){
                            showToast("报名失败，该实践已报名！");
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