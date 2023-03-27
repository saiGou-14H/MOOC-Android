package com.org.moocapp.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.org.moocapp.R;
import com.org.moocapp.fragment.MyVideoPlayerFragment;

import xyz.doikki.videoplayer.player.VideoView;

public class MyVideoView extends VideoView {
    public MyVideoView(@NonNull Context context) {
        super(context);
    }

    public MyVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
//        mPlayerContainer = new FrameLayout(getContext());
//        mPlayerContainer.setBackgroundColor( Color.BLACK);
//        Button button = new Button(getContext());
//        // 使用代码设置drawableTop
//        Drawable drawable = getResources().getDrawable(R.drawable.video_fanhui);
//        // 这一步必须要做,否则不会显示.
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());
//        button.setCompoundDrawables(null, drawable, null, null);
//        mPlayerContainer.addView(button);

        mPlayerContainer = (FrameLayout) inflate(getContext(),R.layout.fragment_my_video_player,null);
        mPlayerContainer.setBackgroundColor( Color.BLACK);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(mPlayerContainer, params);
    }
}
