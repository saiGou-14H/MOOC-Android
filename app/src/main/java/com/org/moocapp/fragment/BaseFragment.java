package com.org.moocapp.fragment;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.org.moocapp.activaty.BaseActivity;

public abstract class BaseFragment extends Fragment {
    public View v;
    public Context context;

    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initData();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        v = inflater.inflate(initLayout(), container, false);
        context = getContext();
        initView();
        initData();
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToastSync(String msg) {
        Looper.prepare();
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    public void navigateTo(Class cls,Bundle bundle) {
        Intent in = new Intent(getActivity(), cls);
        if(bundle!=null){
            in.putExtras(bundle);
        }
        startActivity(in);
    }

    public void navigateToWithFlag(Class cls, int flags) {
        Intent in = new Intent(getActivity(), cls);
        in.setFlags(flags);
        startActivity(in);
    }

    protected void insertVal(String key, String val) {
        SharedPreferences sp = getActivity().getSharedPreferences("sp_ttit", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.commit();
    }

    protected String findByKey(String key) {
        SharedPreferences sp = getActivity().getSharedPreferences("sp_ttit", MODE_PRIVATE);
        return sp.getString(key, "");
    }


    // 页面启动
    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "Fragment onStart");
    }

    // 页面恢复
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Fragment onResume");
    }

    // 页面暂停
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Fragment onPause");
    }

    // 页面停止
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "Fragment onStop");
    }

    // 销毁碎片视图
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "Fragment onDestroyView");
    }

    // 页面销毁
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Fragment onDestroy");
    }

    // 把碎片从页面撕下来
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "Fragment onDetach");
    }
}
