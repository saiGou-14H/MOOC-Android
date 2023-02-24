package com.org.moocapp.activaty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.flyco.tablayout.SlidingTabLayout;
import com.org.moocapp.R;
import com.org.moocapp.adapter.FindAdapter;
import com.org.moocapp.fragment.MessageFragment;
import com.org.moocapp.fragment.MyAnswerFragment;
import com.org.moocapp.fragment.MyQuestionFragment;
import com.org.moocapp.fragment.ProblemFragment;

import java.util.ArrayList;

public class MyMessageActivity extends BaseActivity {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"我的回答", "我的提问"};
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private Button my_message_back;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_message);
//    }

    @Override
    protected int initLayout() {
        return R.layout.activity_my_message;
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.fixedViewPager_my_message);
        slidingTabLayout = findViewById(R.id.slidingTabLayout_my_message);
        my_message_back = findViewById(R.id.my_message_back);
        MyMessageClickListener myMessageClickListener = new MyMessageClickListener();
        my_message_back.setOnClickListener(myMessageClickListener);

    }

    @Override
    protected void initData() {
        mFragments.add(MyAnswerFragment.newInstance(mTitles[0]));
        mFragments.add(MyQuestionFragment.newInstance(mTitles[1]));
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.setAdapter(new FindAdapter(getSupportFragmentManager(), mTitles, mFragments));
        slidingTabLayout.setViewPager(viewPager, mTitles);

    }

    class MyMessageClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.my_message_back:        //退出页面按钮
                    finish();
                    break;
            }
        }
    }
}