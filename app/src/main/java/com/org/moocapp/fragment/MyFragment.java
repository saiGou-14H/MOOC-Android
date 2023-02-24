package com.org.moocapp.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.activaty.MyCartActivity;
import com.org.moocapp.activaty.MyMessageActivity;
import com.org.moocapp.activaty.MyOrderActivity;
import com.org.moocapp.activaty.UserInfoActivity;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.find.UserEntity;
import com.org.moocapp.entity.find.UserListResponse;
import com.org.moocapp.util.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class MyFragment extends BaseFragment {
    private LinearLayout my_cart_layout;
    private LinearLayout my_order_layout;
    RelativeLayout my_message_layout;
    private LinearLayout userinfo;

    private ImageView user_img_header;
    private TextView username;
    private TextView role;
    private TextView my_integral;
    private ImageView my_integralDetails;


    private UserEntity user = new UserEntity();

    public MyFragment() {
        // Required empty public constructor
    }

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int initLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView() {
        my_cart_layout = v.findViewById(R.id.my_cart);
        my_order_layout = v.findViewById(R.id.my_order);
        my_message_layout = v.findViewById(R.id.my_message);
        userinfo = v.findViewById(R.id.userinfo);
        MyFragmentClickListener myFragmentClickListener = new MyFragmentClickListener();
        my_cart_layout.setOnClickListener(myFragmentClickListener);
        my_order_layout.setOnClickListener(myFragmentClickListener);
        my_message_layout.setOnClickListener(myFragmentClickListener);
        userinfo.setOnClickListener(myFragmentClickListener);


        user_img_header = v.findViewById(R.id.user_img_header);
        username = v.findViewById(R.id.my_username);
        role = v.findViewById(R.id.role);
        my_integral = v.findViewById(R.id.my_integral);
        my_integralDetails = v.findViewById(R.id.my_integralDetails);
        my_integralDetails.setOnClickListener(myFragmentClickListener);
        getUserinfo();
    }

    @Override
    protected void initData() {
        getUserinfo();


    }

    @Override
    public void onResume() {
        super.onResume();
        getUserinfo();
    }

    /**
     * 获取个人信息
     */
    private void getUserinfo() {
        HashMap<String, Object> params = new HashMap<>();
        Api.config(ApiConfig.GET_USERINFO, params).getRequest(getContext(), new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                System.out.println(res);
                UserListResponse response = new Gson().fromJson(res, UserListResponse.class);
                if (response != null && response.getCode() == 200) {
                    user = response.getData();
                    if (user != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                username.setText(String.valueOf(user.getUsername()));
                                my_integral.setText(String.valueOf(user.getIntegral()));
                                Picasso.with(getActivity())
                                        .load(user.getHeadPic())
                                        .transform(new CircleTransform())
                                        .into(user_img_header);
                                if (user.getRole() == 1) {
                                    role.setText("学生");
                                } else {
                                    role.setText("老师");
                                }
                            }
                        });
                    } else {
                    }
                }

            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    class MyFragmentClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.userinfo:
                    navigateTo(UserInfoActivity.class, null);
                    break;
                case R.id.my_integralDetails://查看积分流水
//                    navigateTo(.class, null);
                    break;
                case R.id.my_cart:
                    navigateTo(MyCartActivity.class, null);
                    break;
                case R.id.my_order:
                    navigateTo(MyOrderActivity.class, null);
                    break;
                case R.id.my_message:
                    navigateTo(MyMessageActivity.class, null);
                    break;
            }
        }
    }
}