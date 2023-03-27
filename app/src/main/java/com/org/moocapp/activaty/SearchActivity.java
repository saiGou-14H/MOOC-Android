package com.org.moocapp.activaty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.org.moocapp.R;

public class SearchActivity extends BaseActivity implements TextView.OnEditorActionListener {
    private TextInputEditText search_Edit;
    private Button search_return;
    private TextView search_test;
    @Override
    protected int initLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        search_Edit = findViewById(R.id.search_Edit);
        search_return = findViewById(R.id.search_return);
        search_test = findViewById(R.id.search_test);
    }

    @Override
    protected void initData() {
        search_Edit.setOnEditorActionListener(this);

        search_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        search_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search(String.valueOf(search_test.getText()));
            }
        });
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i==KeyEvent.ACTION_DOWN){
            search(String.valueOf(search_Edit.getText()));
        }
        return false;
    }

    public void search(String searchname){
        Bundle bundle = new Bundle();
        bundle.putString("search", searchname);
        navigateTo(SelectCourseActivity.class,bundle);
    }
}