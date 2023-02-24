package com.org.moocapp.util;

import android.content.Context;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RichTextUtil {

    /**
     * 处理图片转base64
     *
     * @param text 富文本内容
     * @return 返回处理图片后的数据
     */
    public static String richText(Context context, String text) {
        String s = new String(text);
        String result = null;//返回结果
        List<String> imgStr = getImgStr(s); //每组base64编码图片
        if (imgStr.isEmpty()) {
            return text;
        }
        for (String s1 : imgStr) { //每个base64转换并上传
//            String s2 = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg"; //新的文件链接
//            //上传
//            MultipartFile multipartFile = BASE64DecodedMultipartFile.base64ToMultipart(s1);
//            assert multipartFile != null;
//            minioUtils.putObject1(multipartFile,minioConfig.getBucketName(),s2);
//            String foreverUrl=minioConfig.getEndpoint()+":"+minioConfig.getPort()+"/"+minioConfig.getBucketName()+"/"+s2;//永久链接
            Uri uri = Uri.parse(s1);
            String imgPath = RealPathFromUriUtils.getRealPathFromUri(context, uri);
            String base64 = "data:image/png;base64," + Base64Util.imageToBase64(imgPath);
            if (Objects.isNull(result)) {
                result = s.replace(s1, base64); //第一次替换"null"换s
            }
            result = result.replace(s1, base64); //前边替换过，结果继续替换
        }
        return result;
    }

    /**
     * @return java.util.List<java.lang.String>
     * @Author shipj
     * @Description 提取富文本图片（img标签src属性）
     * @Date 20:49 2021-04-12
     * @Param [htmlStr]
     **/
    public static List<String> getImgStr(String htmlStr) {
        List<String> list = new ArrayList<>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        // String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片连接地址
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 获得<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                list.add(m.group(1));
            }
        }
        return list;
    }
}
