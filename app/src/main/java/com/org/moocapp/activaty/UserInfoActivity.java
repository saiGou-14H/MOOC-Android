package com.org.moocapp.activaty;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.find.UserEntity;
import com.org.moocapp.entity.find.UserListResponse;
import com.org.moocapp.util.CircleTransform;
import com.org.moocapp.util.PermissionUtils;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class UserInfoActivity extends BaseActivity {

    //SD存储权限组
    private final String[] PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //从相册中选择照片Activity的请求码
    public static final int CHOOSE_PHOTO = 2;
    //回调选中图片到真实路径
    private String logoPath = "";
    // 多个权限请求Code
    private final int REQUEST_CODE_PERMISSIONS = 3;


    private Button userinfo_back;
    private ImageView userinfo_avatar;
    private EditText userinfo_username;
    private EditText userinfo_sex;
    private EditText userinfo_age;
    private EditText userinfo_phone;
    private EditText userinfo_email;
    private EditText userinfo_introduction;

    private RelativeLayout save_userinfo_btn;
    private UserEntity user = new UserEntity();

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_info);
//    }

    @Override
    protected int initLayout() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView() {
        userinfo_back = findViewById(R.id.userinfo_back);
        UserInfoClickListener myCartClickListener = new UserInfoClickListener();
        userinfo_back.setOnClickListener(myCartClickListener);


        userinfo_avatar = findViewById(R.id.userinfo_avatar);
        userinfo_username = findViewById(R.id.userinfo_username);
        userinfo_age = findViewById(R.id.userinfo_age);
        userinfo_phone = findViewById(R.id.userinfo_phone);
        userinfo_email = findViewById(R.id.userinfo_email);
        userinfo_introduction = findViewById(R.id.userinfo_introduction);
        save_userinfo_btn = findViewById(R.id.save_userinfo_btn);
        save_userinfo_btn.setOnClickListener(myCartClickListener);
        userinfo_avatar.setOnClickListener(myCartClickListener);
    }

    @Override
    protected void initData() {
        getUserinfo();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserinfo();
    }

    /**
     * 保存个人信息
     */
    private void updateUserinfo() {
        HashMap<String, Object> params = new HashMap<>();
        String username = String.valueOf(userinfo_username.getText());
        String age = String.valueOf(userinfo_age.getText());
        String email = String.valueOf(userinfo_email.getText());
        String phone = String.valueOf(userinfo_phone.getText());
        String introduction = String.valueOf(userinfo_introduction.getText());
        params.put("username", username);
        params.put("age", age);
        params.put("email", email);
        params.put("phone", phone);
        params.put("introduction", introduction);
        Api.config(ApiConfig.UPDATE_USERINFO, params).postRequest(this, new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                System.out.println(res);
                getUserinfo();
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    /**
     * 获取个人信息
     */
    private void getUserinfo() {
        HashMap<String, Object> params = new HashMap<>();
        Api.config(ApiConfig.GET_USERINFO, params).getRequest(this, new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
                System.out.println(res);
                UserListResponse response = new Gson().fromJson(res, UserListResponse.class);
                if (response != null && response.getCode() == 200) {
                    user = response.getData();
                    if (user != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (user.getUsername() != null) {
                                    userinfo_username.setText(String.valueOf(user.getUsername()));
                                }
                                if (user.getAge() != null) {
                                    userinfo_age.setText(String.valueOf(user.getAge()));
                                }
                                if (user.getPhone() != null) {
                                    userinfo_phone.setText(String.valueOf(user.getPhone()));
                                }
                                if (user.getEmail() != null) {
                                    userinfo_email.setText(String.valueOf(user.getEmail()));
                                }
                                if (user.getIntroduction() != null) {
                                    userinfo_introduction.setText(String.valueOf(user.getIntroduction()));
                                }
                                if (user.getHeadPic() != null) {
                                    Picasso.with(mContext)
                                            .load(user.getHeadPic())
                                            .transform(new CircleTransform())
                                            .into(userinfo_avatar);
                                }


                            }
                        });
                    } else {
                    }
                }

            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    /**
     * 修改头像信息
     */
    private void uploadHead(String base46Str) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("base46Str", base46Str);
        Api.config(ApiConfig.UPDATE_HEAD, params).postRequest(this, new TtitCallback() {
            @Override
            public void onSuccess(final String res) {
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }


    class UserInfoClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.userinfo_avatar:        //保存更换头像按钮
                    requestMorePermissions();
                    break;
                case R.id.save_userinfo_btn:        //保存个人信息按钮
                    updateUserinfo();
                    showToast("保存成功");

                    break;


                case R.id.userinfo_back:        //退出页面按钮
                    finish();
                    break;

            }
        }
    }


    /**
     * 自定义申请多个权限
     */
    private void requestMorePermissions() {
        PermissionUtils.checkMorePermissions(mContext, PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
            @Override
            public void onHasPermission() {
                // 已授予权限，打开相册获取图片真实路径
                Log.i(TAG, "已授予权限");
                choosePermissionLogo();
            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                // 上一次申请权限被拒绝，可用于向用户说明权限原因，然后调用权限申请方法
                Log.i(TAG, "上一次申请权限被拒绝");
                showExplainDialog((dialog, which) -> PermissionUtils.requestMorePermissions(mContext, PERMISSIONS, REQUEST_CODE_PERMISSIONS));
            }

            @Override
            public void onUserHasAlreadyTurnedDownAndDonAsk(String... permission) {
                // 第一次申请权限或被禁止申请权限，建议直接调用申请权限方法。
                Log.i(TAG, "第一次申请权限或被禁止申请权限");
                PermissionUtils.requestMorePermissions(mContext, PERMISSIONS, REQUEST_CODE_PERMISSIONS);
            }
        });
    }

    /**
     * 解释权限的dialog
     */
    private void showExplainDialog(DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(mContext)
                .setTitle("申请内存读写权限")
                .setMessage("用于打开相册，读取图片路径")
                .setPositiveButton("确定", onClickListener)
                .show();
    }

    /**
     * 打开相册选择权限图标
     */
    private void choosePermissionLogo() {
        //如果重复选择的话，先清空，等新图片路径来重新赋值，如果需要多张路的话，需要使用List<String>来存储多张图片到真实路径
        logoPath = "";
        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, CHOOSE_PHOTO);
    }

    /**
     * 回调申请的权限组
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            PermissionUtils.onRequestMorePermissionsResult(mContext, PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
                @Override
                public void onHasPermission() {
                    // 权限已被授予
                    choosePermissionLogo();
                }

                @Override
                public void onUserHasAlreadyTurnedDown(String... permission) {
                    // 拒绝权限
                    Toast.makeText(mContext, "我们需要" + Arrays.toString(permission) + "权限", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onUserHasAlreadyTurnedDownAndDonAsk(String... permission) {
                    //已禁止再次询问权限
//                    Toast.makeText(mContext, "我们需要" + Arrays.toString(permission) + "权限", Toast.LENGTH_SHORT).show();
                    showToast("我们需要" + Arrays.toString(permission) + "权限");
//                    showToAppSettingDialog();
                }
            });
        }
    }

    /**
     * 获取图片路径
     *
     * @param uri
     * @param selection
     * @return
     */

    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        Log.e("TAG", "getImagePath: " + path);
        return path;
    }

    /**
     * Android 4.4以上打开相册获取图片真实路径
     */
    @TargetApi(19)
    private String handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.i(TAG, "获取的uri=" + uri);
        if (DocumentsContract.isDocumentUri(mContext, uri)) {
            //如果是document类型的Uri ,则通过document_id来处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数据格式的ID
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                Log.i(TAG, "类型=media.documents");
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(docId));
                imagePath = getImagePath(contentUri, null);
                Log.i(TAG, "类型=downloads.documents");
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是普通类型的Uri,则使用普通的方式来处理
            imagePath = getImagePath(uri, null);
            //imagePath = getRealPathFromURI(uri);
            Log.i(TAG, "类型=content");
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri，直接获取图片路径就可以了
            imagePath = uri.getPath();
            Log.i(TAG, "类型=file");
        }
        Log.i(TAG, "图片真实路径=" + imagePath);
        return imagePath;
    }

    /**
     * Android4.4以下打开相册获取图片真实路径
     */
    private String handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        Log.i(TAG, "图片真实路径=" + imagePath);
        return imagePath;
    }

    /**
     * 处理相册选择回调图片Uri
     */
    @SuppressLint({"ObsoleteSdkInt", "SetTextI18n"})
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO: 从相册获取
        if (requestCode == CHOOSE_PHOTO) {
            if (resultCode == RESULT_OK) {
                if (Build.VERSION.SDK_INT >= 19) {
                    logoPath = handleImageOnKitKat(data);
                } else {
                    logoPath = handleImageBeforeKitKat(data);
                }
            }
        }
        //必须先进行上面两个handleImage方法去解析Uri，将类型转为真实路径的String路径返回给logoPath，然后进行下面的判断操作业务
        if (TextUtils.isEmpty(logoPath) && logoPath.equals("")) {
//            tvPermissionShow.setText("请您选择一张权限图标");
//            tvPermissionShow.setTextColor(getResources().getColor(R.color.red)); //字体变红色
        } else {
            //不为空，将图片路径转为Bitmap后，使用图片控件显示出来
            userinfo_avatar.setImageBitmap(BitmapFactory.decodeFile(logoPath));
            //修改头像
//            uploadHead(bitmapToBase64(BitmapFactory.decodeFile(logoPath)));
//            userinfo_avatar.setImageURI(Uri.fromFile(new File(logoPath)));
//            tvPermissionShow.setText("当前图片名：" + logoPath.substring(logoPath.lastIndexOf("/") + 1));
//            tvPermissionShow.setTextColor(getResources().getColor(R.color.blue));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * bitmap转为base64
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


}