package com.org.moocapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.org.moocapp.activaty.BaseActivity;
import com.org.moocapp.adapter.SectionsPagerAdapter;
import com.org.moocapp.fragment.FindFragment;
import com.org.moocapp.fragment.MyFragment;
import com.org.moocapp.fragment.HomeFragment;
import com.org.moocapp.fragment.StudyFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private TabLayout tab;
    private ViewPager2 viewpager;

    List<String> titles = new ArrayList<>();
    List<Integer> icon = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            //清除状态栏
            //window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            //| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(R.color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        tab= findViewById(R.id.tab);
        viewpager = findViewById(R.id.viewpager);


    }

    @Override
    protected void initData() {
        titles.add("选课");
        titles.add("学习");
        titles.add("发现");
        titles.add("我的");
        icon.add(R.mipmap.icon_tab1);
        icon.add(R.mipmap.icon_tab2);
        icon.add(R.mipmap.icon_tab3);
        icon.add(R.mipmap.icon_tab4);

        fragments.add(new HomeFragment());
        fragments.add(new StudyFragment());
        fragments.add(new FindFragment());
        fragments.add(new MyFragment());

        viewpager.setOffscreenPageLimit(fragments.size());

        tab.setTabMode(TabLayout.MODE_FIXED);
        SectionsPagerAdapter sectionsPagerAdap = new SectionsPagerAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        viewpager.setAdapter(sectionsPagerAdap);
        new TabLayoutMediator(tab, viewpager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles.get(position));
                tab.setIcon(icon.get(position));
            }
        }).attach();
    }



}