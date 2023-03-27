package com.org.moocapp.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

import static android.content.Context.MODE_PRIVATE;

import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.activaty.LoginActivity;
import com.org.moocapp.entity.CourseEntity;
import com.org.moocapp.entity_mysql.MCourse;
import com.org.moocapp.entity_mysql.MCourseChapter;
import com.org.moocapp.entity_mysql.MCourseType;
import com.org.moocapp.entity_mysql.MStudentCourse;
import com.org.moocapp.util.StringUtils;

public class Api {
    private static OkHttpClient client;
    private static String requestUrl;
    private static HashMap<String, Object> mParams;
    public static Api api = new Api();

    public Api() {

    }

    public static Api config(String url, HashMap<String, Object> params) {
        client = new OkHttpClient.Builder()
                .build();
        requestUrl = ApiConfig.BASE_URL+url;
        mParams = params;
        return api;
    }

    public void postRequest(Context context, final TtitCallback callback) {
        SharedPreferences sp = context.getSharedPreferences("sp_ttit", MODE_PRIVATE);
        String token = sp.getString("token", "");
        JSONObject jsonObject = new JSONObject(mParams);
        String jsonStr = jsonObject.toString();
        RequestBody requestBodyJson =
                RequestBody.create(MediaType.parse("application/json;charset=utf-8")
                        , jsonStr);
        //第三步创建Rquest
        Request request = new Request.Builder()
                .url(requestUrl)
                .addHeader("contentType", "application/json;charset=UTF-8")
                .addHeader("Authorization",token)
                .post(requestBodyJson)
                .build();
        //第四步创建call回调对象
        final Call call = client.newCall(request);
        //第五步发起请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", e.getMessage());
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                callback.onSuccess(result);
            }
        });
    }

    public void getRequest(Context context, final TtitCallback callback) {
        SharedPreferences sp = context.getSharedPreferences("sp_ttit", MODE_PRIVATE);
        String token = sp.getString("token", "");
//        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        String url = getAppendUrl(requestUrl, mParams);
//        Map<String,Object> map = new HashMap<>();
//        map.put("like","w");
//        JSONObject jsonObject = new JSONObject(map);
//        String json = jsonObject.toString();
//        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization",token)
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", e.getMessage());
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String code = jsonObject.getString("code");
                    if (code.equals("401")) {
                        Intent in = new Intent(context, LoginActivity.class);
                        context.startActivity(in);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(result);
            }
        });
    }

    private String getAppendUrl(String url, Map<String, Object> map) {
        if (map != null && !map.isEmpty()) {
            StringBuffer buffer = new StringBuffer();
            Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, Object> entry = iterator.next();
                if (StringUtils.isEmpty(buffer.toString())) {
                    buffer.append("?");
                } else {
                    buffer.append("&");
                }
                buffer.append(entry.getKey()).append("=").append(entry.getValue());
            }
            url += buffer.toString();
        }
        return url;
    }










    public static ArrayList<MCourse> getImageInfos(){
        ArrayList<MCourse> imageInfos = new ArrayList<>();
        imageInfos.add(new MCourse(R.mipmap.lunbo1,"课程名称",1,"课程描述"));
        imageInfos.add(new MCourse(R.mipmap.lunbo2,"课程名称",1,"课程描述"));
        imageInfos.add(new MCourse(R.mipmap.lunbo3,"课程名称",1,"课程描述"));
        imageInfos.add(new MCourse(R.mipmap.lunbo4,"课程名称",1,"课程描述"));
        return imageInfos;
    }

    public static ArrayList<MCourseType> getCourseTypeInfos(){
        ArrayList<MCourseType> mCourseTypes = new ArrayList<>();
        MCourseType type1 = new MCourseType();
        type1.setType("康复护理");
        mCourseTypes.add(type1);
        MCourseType type2 = new MCourseType();
        type2.setType("法律法规");
        mCourseTypes.add(type2);
        MCourseType type3 = new MCourseType();
        type3.setType("生活常识");
        mCourseTypes.add(type3);
        MCourseType type4 = new MCourseType();
        type4.setType("更多");
        mCourseTypes.add(type4);
        return mCourseTypes;
    }

    public static ArrayList<MCourseChapter> getCourseChapterInfos(){
        ArrayList<MCourseChapter> mcoursechapter = new ArrayList<>();
        mcoursechapter.add(new MCourseChapter(1,"枚举"));

        mcoursechapter.add(new MCourseChapter(2,"递归"));

        mcoursechapter.add(new MCourseChapter(3,"动态规划"));

        mcoursechapter.add(new MCourseChapter(4,"八数码问题"));

        mcoursechapter.add(new MCourseChapter(5,"迷宫问题"));

        mcoursechapter.add(new MCourseChapter(6,"八贪心算法"));

        return mcoursechapter;
    }

    public static  ArrayList<MStudentCourse> getStudentCourseInfos(){
        ArrayList<MStudentCourse> StudentCourse = new ArrayList<>();
        StudentCourse.add(new MStudentCourse("好评好评，听了一半课了感觉很有收获！"));
        StudentCourse.add(new MStudentCourse("好评好评，听了一半课了感觉很有收获！"));
        StudentCourse.add(new MStudentCourse("好评好评，听了一半课了感觉很有收获！"));
        StudentCourse.add(new MStudentCourse("好评好评，听了一半课了感觉很有收获！"));
        return StudentCourse;
    }
}

