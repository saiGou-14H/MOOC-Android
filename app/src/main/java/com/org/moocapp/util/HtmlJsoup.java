package com.org.moocapp.util;


import android.content.Context;
import android.net.Uri;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlJsoup {
    /**
     * 检测图片路径是否为真实有效的路径方法
     *
     * @param src
     * @return
     */
    public static boolean checkImage(String src) {
        //使用正则表达式，排除img标签src属性值已经是base64编码的情况
        Pattern pattern = Pattern.compile("^data:image/(png|gif|jpg|jpeg|bmp|tif|psd|ICO);base64,.*");
        Matcher matcher = pattern.matcher(src);
        if (matcher.matches())
            return false;
        //排除src路径并非图片格式的情况
        pattern = Pattern.compile("^.*[.](png|gif|jpg|jpeg|bmp|tif|psd|ICO)$");
        matcher = pattern.matcher(src);
        if (!matcher.matches())
            return false;
        return true;
    }

    /**
     * @param newsBody
     * @return
     */

    public static String documentBody(String newsBody) {
        Element doc = Jsoup.parseBodyFragment(newsBody).body();
        Elements pngs = doc.select("img[src]");
        String httpHost = "http://192.168.0.100";
        for (Element element : pngs) {
            String imgUrl = element.attr("src");
            element.attr("src", "imgUrl");
//            if (imgUrl.trim().startsWith("/")) { // 会去匹配咱们富文本的图片的 src 的相对路径的首个字符，请注意一下
//                imgUrl =httpHost + imgUrl;
//                element.attr("src", imgUrl);
//            }
        }
        return newsBody = doc.toString();
    }

}
