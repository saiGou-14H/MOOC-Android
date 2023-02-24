package com.org.moocapp.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.org.moocapp.R;
import com.org.moocapp.adapter.CatalogueListAdapter;
import com.org.moocapp.api1.api;

public class CourseCatalogueFragment extends BaseFragment {
    private RecyclerView recyclerView;

    @Override
    protected int initLayout() {
        return R.layout.fragment_course_catalogue;
    }

    @Override
    protected void initView() {
        recyclerView = v.findViewById(R.id.catalogueRecyclerView);
    }

    @Override
    protected void initData() {
        CatalogueListAdapter adapter = new CatalogueListAdapter(context, api.getCourseChapterInfos());
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(adapter);
    }
}