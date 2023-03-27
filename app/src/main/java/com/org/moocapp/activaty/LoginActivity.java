package com.org.moocapp.activaty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.org.moocapp.MainActivity;
import com.org.moocapp.R;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.Response.LoginResponse;
import com.org.moocapp.entity.Response.ResponseHeader;
import com.org.moocapp.util.RoundTransform;
import com.org.moocapp.util.utils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class LoginActivity extends BaseActivity {
    private Button login_btn;
    private TextInputEditText account;
    private TextInputEditText password;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        login_btn = findViewById(R.id.login_btn);
        account =  findViewById(R.id.account);
        password = findViewById(R.id.password);

    }

    @Override
    protected void initData() {
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
    }

    public void Login(){
        HashMap<String ,Object> map = new HashMap<>();
        map.put("id",String.valueOf(account.getText()));
        map.put("password",String.valueOf(password.getText()));
        Api.config(ApiConfig.LOGIN,map).postRequest(mContext, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                LoginResponse loginResponse = new Gson().fromJson(res,LoginResponse.class);

                System.out.println("11111111111"+loginResponse.getMessage());
                if(loginResponse.getCode()==200){
                    insertVal("token",loginResponse.getData().getToken());
                    navigateTo(MainActivity.class,null);
                }else {
                    showToastSync("登录失败");
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}