package com.org.moocapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Base64ImageUtils {
    /**
     * bmp 转 base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转bmp
     *
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Img) {
        // 若包含"data:image/jpeg;base64,"前缀，只取后边的部分
        if (TextUtils.isEmpty(base64Img)) {
            return null;
        }
        //
        if (base64Img.contains(",")) {
            String[] base64ImgArray = base64Img.split(",");
            if (base64ImgArray.length == 2) {
                base64Img = base64ImgArray[1];
            }
        }
        // 解码
        try {
            byte[] decodedString = Base64.decode(base64Img, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return decodedByte;
        } catch (Exception e) {
            e.printStackTrace();
            // 回调生成图片失败
        }
        return null;
    }
}
