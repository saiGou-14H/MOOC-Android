package com.org.moocapp.activaty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.org.moocapp.R;

public class PaySuccessActivity extends BaseActivity {
    private Button pay_success_back;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pay_success);
//    }

    @Override
    protected int initLayout() {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void initView() {
        pay_success_back = findViewById(R.id.pay_success_back);
        PaySuccessClickListener myCartClickListener = new PaySuccessClickListener();
        pay_success_back.setOnClickListener(myCartClickListener);
    }

    @Override
    protected void initData() {

    }
    class PaySuccessClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
//                case R.id.paymentBtn:        //退出页面按钮
//                    navigateTo(PaySuccessActivity.class, null);
//                    break;
                case R.id.pay_success_back:        //退出页面按钮
                    finish();
                    break;
            }
        }
    }
}