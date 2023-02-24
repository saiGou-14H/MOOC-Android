package com.org.moocapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NoScrollListView extends ListView {
    private float xDistance, yDistance, xLast, yLast;

    public NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 100, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }

//    private List list = new ArrayList();
//    private int scrollPaddingTop; // scrollview的顶部内边距
//    private int scrollPaddingLeft;// scrollview的左侧内边距
//    private int[] scrollLoaction = new int[2]; // scrollview在窗口中的位置
//    private final static int UPGLIDE = 0;
//    private final static int DOWNGLIDE = 1;
//    private int glideState;
//
//    private int downY = 0;
//    private int moveY = 0;
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                downY = (int) ev.getY();
//                System.out.println("actiondown" + ev.getY());
//                break;
//            case MotionEvent.ACTION_MOVE:
//                moveY = (int) ev.getY();
//                System.out.println("move" + moveY + "down" + downY);
//                if ((moveY - downY) >= 0) {
//                    System.out.println("'''''''''DOWNGLIDE'''''''''''");
//                    glideState = DOWNGLIDE;
//                } else {
//                    System.out.println("'''''''''UPGLIDE'''''''''''");
//                    glideState = UPGLIDE;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//            default:
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }

}
