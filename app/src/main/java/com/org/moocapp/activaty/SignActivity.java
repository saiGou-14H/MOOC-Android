package com.org.moocapp.activaty;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.Response.ResponseHeader;
import com.org.moocapp.entity.Response.UserEntity;
import com.org.moocapp.entity.Response.UserResponse;

public class SignActivity extends BaseActivity {
    private TextView singhistory;
    private TextView signmoney;
    private Button[] btn = new Button[8];
    private Button sign_btn;


    @Override
    protected int initLayout() {
        return R.layout.activity_sign;
    }

    @Override
    protected void initView() {
        singhistory = findViewById(R.id.singhistory);
        signmoney = findViewById(R.id.signmoney);
        btn[0] = findViewById(R.id.sign_btn1);
        btn[1] = findViewById(R.id.sign_btn2);
        btn[2] = findViewById(R.id.sign_btn3);
        btn[3] = findViewById(R.id.sign_btn4);
        btn[4] = findViewById(R.id.sign_btn5);
        btn[5] = findViewById(R.id.sign_btn6);
        btn[6] = findViewById(R.id.sign_btn7);

        sign_btn = findViewById(R.id.sign_btn);
        sign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sign();
                getUser();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getUser();
    }

    public void getUser(){
        Api.config(ApiConfig.shUser,null).getRequest(mContext, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                UserResponse userResponse = new Gson().fromJson(res,UserResponse.class);
                UserEntity userEntity = userResponse.getData();
                runOnUiThread(new Runnable() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void run() {
                        if (userResponse.getCode()==200&&userEntity!=null){
                            int history = Integer.valueOf(userEntity.getSign());
                            int cun= 0,a=1000000;
                            while (history!=0){
                                history-=a;
                                a/=10;
                                cun++;
                            }
                            if (userEntity.issign){
                                sign_btn.setText("已签到");
                                singhistory.setText("已连续签到"+cun+"天");
                                signmoney.setText("明日签到可得"+btn[(cun)%7].getText()+"积分");
                            }else {
                                sign_btn.setText("签到");
                                singhistory.setText("已连续签到"+cun+"天");
                                signmoney.setText("今日签到可得"+btn[(cun)%7].getText()+"积分");
                            }
                            while (cun!=0){
                                btn[cun-1].getBackground().setColorFilter(R.color.green, PorterDuff.Mode.DARKEN);
                                cun--;
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

    public void Sign(){
        Api.config(ApiConfig.Sign,null).getRequest(mContext, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                ResponseHeader responseHeader = new Gson().fromJson(res,ResponseHeader.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseHeader.getCode()==200){
                            showToast("签到成功");
                            sign_btn.setText("已签到");
                        }else if(responseHeader.getCode()==300){
                            showToast("今天已经签到过了哦");
                            sign_btn.setText("已签到");
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