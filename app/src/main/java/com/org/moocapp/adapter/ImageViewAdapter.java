package com.org.moocapp.adapter;

import static android.widget.ImageView.ScaleType.FIT_XY;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.org.moocapp.entity_mysql.MCourse;
import com.org.moocapp.util.RoundTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageViewAdapter extends PagerAdapter {
    private final Context mContext;
    private final ArrayList<MCourse> mCourseInfos;
    // 声明一个图像视图列表
    private List<ImageView> mViewList = new ArrayList<>();

    public ImageViewAdapter(Context Context,ArrayList<MCourse> CourseInfos) {
        this.mContext = Context;
        this.mCourseInfos=CourseInfos;
        for (MCourse info : mCourseInfos) {
            ImageView view = new ImageView(mContext);
            view.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            view.setScaleType(FIT_XY);
            Picasso.with(mContext).load(info.getImage()).transform(new RoundTransform(mContext,40)).into(view);


            mViewList.add(view);
        }
    }


    @Override
    public int getCount() {
        return mCourseInfos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView item = mViewList.get(position);
        container.addView(item);
        return item;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mViewList.get(position));
    }

}
