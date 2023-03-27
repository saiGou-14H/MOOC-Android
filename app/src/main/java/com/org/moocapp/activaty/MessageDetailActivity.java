package com.org.moocapp.activaty;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.adapter.MessageCommentAdapter;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.find.MessageCommentEntity;
import com.org.moocapp.entity.find.MessageCommentListResponse;
import com.org.moocapp.entity.find.MessageEntity;
import com.org.moocapp.util.CircleTransform;
import com.org.moocapp.view.NoScrollListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MessageDetailActivity extends BaseActivity {
    private WebView webView;//加载显示网页

    private Button messageDetail_back;//退出页面
    private TextView tvMessage_title;
    private ImageView imImg_header;
    private TextView tvMessage_author;
    private TextView tvPublish_comment;
    private LinearLayout Like_MMessage;//点赞按钮
    private ImageView message_img_like;//点赞按钮图片
    private TextView message_like_num;//点赞数量
    private TextView message_detail_comment_num;//点赞数量
    private EditText commentEdit;        //评论输入框
    private String comment = "";        //记录对话框中的内容
    private TextInputEditText textInputEditText;

    private List<MessageCommentEntity> datas = new ArrayList<>();

    private MessageCommentAdapter messageCommentAdapter;
    private NoScrollListView NoScrollListView_messageCommentList;//评论列表


    private MessageEntity messageEntity;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    messageCommentAdapter.setDatas(datas);
                    //通知recyclerView刷新页面
                    messageCommentAdapter.notifyDataSetChanged();

//                    messageCommentRecyclerViewAdapter.setDatas(datas);
//                    messageCommentRecyclerViewAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_message_detail;
    }

    @Override
    protected void initView() {
        //获得控件
        webView = (WebView) findViewById(R.id.message_content_webView);

        messageDetail_back = findViewById(R.id.messageDetail_back);
        tvMessage_title = findViewById(R.id.message_title);
        imImg_header = findViewById(R.id.img_header);
        tvMessage_author = findViewById(R.id.message_author);
//        tvMessage_content = findViewById(R.id.message_content);
        tvPublish_comment = findViewById(R.id.publish_omment);
        commentEdit = findViewById(R.id.comment_Edit);
        Like_MMessage = findViewById(R.id.Like_MMessage);
        message_img_like = findViewById(R.id.message_img_like);
        message_like_num = findViewById(R.id.message_like_num);
        message_detail_comment_num = findViewById(R.id.message_detail_comment_num);

        MyClickListener myClickListener = new MyClickListener();
        messageDetail_back.setOnClickListener(myClickListener);
        tvPublish_comment.setOnClickListener(myClickListener);
        Like_MMessage.setOnClickListener(myClickListener);

        textInputEditText = findViewById(R.id.comment_Edit);
        textInputEditText.setOnEditorActionListener(new MyOnEditor());
        /**
         * messageCommentAdapter
         */
        NoScrollListView_messageCommentList = findViewById(R.id.NoScrollListView_messageCommentList);
        messageCommentAdapter = new MessageCommentAdapter(this);
        NoScrollListView_messageCommentList.setAdapter(messageCommentAdapter);


    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            messageEntity = (MessageEntity) bundle.getSerializable("messageEntity");
            tvMessage_title.setText(messageEntity.getTitle());
            tvMessage_author.setText(messageEntity.getAuthor());
            Picasso.with(mContext)
                    .load(messageEntity.getHeadPic())
                    .transform(new CircleTransform())
                    .into(imImg_header);
//            tvMessage_content.setText(messageEntity.getContent());
            webView.loadDataWithBaseURL(null, getHtmlData(messageEntity.getContent()), "text/html", "utf-8", null);
//            webView.loadDataWithBaseURL(null, getHtmlData(IMAGE3), "text/html", "utf-8", null);
            message_like_num.setText(String.valueOf(messageEntity.getMessageLike()));
            message_detail_comment_num.setText("评论(" + messageEntity.getCommentNum() + ")");
            getMessageCommentList(messageEntity.getId());
        }
        initWebView();

    }

    /**
     * 关闭软键盘
     *
     * @param view 当前页面上任意一个可用的view
     */
    public void closeSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * 获取html
     */
    private String getHtmlData(String bodyHTML) {
        String head = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>img{max-width: 100%; width:100%; height:auto;}*{margin:0px;}</style>"
                + "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    /**
     * 初始化WebView
     */
    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);// 设置WebView支持JavaScript
        //支持自动适配
        settings.setUseWideViewPort(true);//将图片调整到适合webView的大小
        settings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        settings.setSupportZoom(true);  //支持放大缩小
        settings.setBuiltInZoomControls(true); //显示缩放按钮
        settings.setBlockNetworkImage(true);// 把图片加载放在最后来加载渲染
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setSaveFormData(true);
        settings.setGeolocationEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);/// 支持通过JS打开新窗口
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //系统默认会通过手机浏览器打开网页,设置不让其跳转浏览器
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        // 添加客户端支持
        webView.setWebChromeClient(new WebChromeClient());
        // mWebView.loadUrl(TEXTURL);

        //不加这个图片显示不出来
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.getSettings().setBlockNetworkImage(false);

        //允许cookie 不然有的网站无法登陆
        CookieManager mCookieManager = CookieManager.getInstance();
        mCookieManager.setAcceptCookie(true);
        mCookieManager.setAcceptThirdPartyCookies(webView, true);

        String content = "&lt;p&gt;&lt;span style=&quot;color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;; font-size: 15px; background-color: rgb(255, 255, 255);&quot;&gt;富文本内容&lt;/span&gt;&lt;/p&gt;";
        if (content.contains("&")) {
            content = content.replace("&#039;", "'");
            content = content.replace("&quot;", "\"");
            content = content.replace("&lt;", "<");
            content = content.replace("&gt;", ">");
            content = content.replace("&amp;", "&");
        }
    }

    /**
     * 获取资讯详情消息
     */
    private void getMessageDetail() {

    }

    /**
     * 获取资讯是否点赞
     */
    private void getIsMessageLike() {
        boolean likeStatus = true;
        if (likeStatus) {
            message_img_like.setVisibility(View.VISIBLE);
        } else {
            message_img_like.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * 用户点赞或者取消点赞资讯
     */
    private void LikeMMessageByUserId() {
        getIsMessageLike();
        boolean likeStatus = true;
        if (likeStatus) {
            message_img_like.setVisibility(View.VISIBLE);
        } else {
            message_img_like.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * 获取评论列表数据
     */
    private void getMessageCommentList(Long messageId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("messageId", messageId);
        Api.config(ApiConfig.MessageCommentBy_LIST_ALL, params).getRequest(this, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                MessageCommentListResponse response = new Gson().fromJson(res, MessageCommentListResponse.class);
                if (response != null && response.getCode() == 200) {
                    List<MessageCommentEntity> list = response.getData();
                    if (list != null && list.size() > 0) {
                        datas = list;
                        mHandler.sendEmptyMessage(0);
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
     * 判断对话框中是否输入内容
     */
    private boolean isEditEmply() {
        comment = commentEdit.getText().toString().trim();
        if (comment.equals("")) {
            showToast("评论不能为空");
            return false;
        }
        commentEdit.setText("");
        return true;
    }

    /**
     * 发表评论
     */
    private void publishComment(Long messageId, View view) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("messageId", messageId);
        params.put("content", comment);
        params.put("isLike", 0);
        Api.config(ApiConfig.MessageComment_ADD, params).postRequest(this, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                Log.e("response", res);
                getMessageCommentList(messageEntity.getId());
                closeSoftKeyboard(view);
                showToastSync("发表成功");
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    /**
     * 事件监听
     */
    class MyOnEditor implements TextInputEditText.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == KeyEvent.ACTION_DOWN) {
                Log.e("TAG", "点击了enter键");
                closeSoftKeyboard(new View(mContext));
            }
            return false;
        }
    }

    class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.Like_MMessage:    //点赞按钮
                    Integer num = Integer.valueOf((String) message_like_num.getText());
                    message_like_num.setText(String.valueOf(num + 1));
                    if (message_img_like.getVisibility() == View.INVISIBLE) {
                        message_img_like.setVisibility(View.VISIBLE);
                    } else {
                        message_img_like.setVisibility(View.INVISIBLE);
                    }
                    break;
                case R.id.publish_omment:    //发表评论按钮
                    //判断用户是否输入内容
                    if (isEditEmply()) {
                        publishComment(messageEntity.getId(), view);
                    }
                    break;
                case R.id.messageDetail_back:        //退出页面按钮
                    finish();
                    break;
            }

        }
    }
}