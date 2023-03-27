package com.org.moocapp.util;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class utils {
    public static Target setLayoutBackground(Resources getResources,ViewGroup layout){
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                layout.setBackground(new BitmapDrawable(getResources,bitmap));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }
    public static String getRatio(Double d1, Double d2) {
        if (d1 == null || d2 == null || d2 <= 0) {
            return "0%";
        }

        System.out.println(d1);
        System.out.println(d2);
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(2);
        percent.setMinimumFractionDigits(2);

        return percent.format(d1/d2);
    }

    public static Double div(Double v1,Double v2,int scale){

        if(scale<0){

            throw new IllegalArgumentException(

                    "The scale must be a positive integer or zero");

        }

        BigDecimal b1 = new BigDecimal(v1.toString());

        BigDecimal b2 = new BigDecimal(v2.toString());

        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();

    }



}
